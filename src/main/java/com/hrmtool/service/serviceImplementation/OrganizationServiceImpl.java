package com.hrmtool.service.serviceImplementation;

import com.hrmtool.persistance.dto.EmailDetails;
import com.hrmtool.globalHandler.BadRequestException;
import com.hrmtool.globalHandler.NotFoundException;
import com.hrmtool.globalHandler.response.ApiResponse;
import com.hrmtool.persistance.dto.OrganizationDto;
import com.hrmtool.persistance.entity.*;
import com.hrmtool.persistance.repository.*;
import com.hrmtool.service.OrganizationService;
import com.hrmtool.service.SESService;
import com.hrmtool.utils.PasswordEncrypterDecrypter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepo organizationRepository;
    private final UsersRepo usersRepository;
    private final RoleRepo roleRepository;
    private final RoleUserMappingRepo roleUserMappingRepository;
    private final RolePermissionMappingRepo rolePermissionMappingRepository;
    private final PermissionRepo permissionRepository;
    private final SESService sesService;

    /**
     * Functionality related to registration of organization and admin user
     */
    @Override
    public ResponseEntity<ApiResponse<OrganizationDto>> createOrganization(OrganizationDto organizationDTO) {

        //Check if domain name already exist
        Optional<Organization> checkOrgDomain = organizationRepository.findByDomain(organizationDTO.getDomain());
        if (!checkOrgDomain.isEmpty()) {
            throw new BadRequestException("Domain name already exists please choose another one !!");
        }

        try {

            //Setup Organization
            Organization organization = Organization.builder()
                    .organizationCode(organizationDTO.getOrganizationCode())
                    .name(organizationDTO.getName())
                    .contactNum(organizationDTO.getContactNum())
                    .size(organizationDTO.getSize())
                    .country(organizationDTO.getCountry())
                    .domain(organizationDTO.getDomain())
                    .build();

            organizationRepository.save(organization);


            //Create SuperAdmin role
            Role role = Role.builder()
                    .roleName("SUPER_ADMIN")
                    .isGuestUserAllowed(false)
                    .organization(organization)
                    .build();

            roleRepository.save(role);

            //Get All permission list
            List<Permission> permissionList = permissionRepository.findAll();

            //Add all permission to SUPER_ADMIN Role
            for (Permission permission : permissionList) {
                RolePermissionMapping rolePermissionMapping = RolePermissionMapping.builder()
                        .role(role)
                        .permission(permission)
                        .build();

                rolePermissionMappingRepository.save(rolePermissionMapping);
            }

            //Setup User
            Users users = Users.builder()
                    .firstName(organizationDTO.getFirstName())
                    .lastName(organizationDTO.getLastName())
                    .username(organizationDTO.getFirstName().concat(" " + organizationDTO.getLastName()))
                    .password(PasswordEncrypterDecrypter.encrypt(organizationDTO.getPassword()))
                    .primaryEmail(organizationDTO.getPrimaryEmail())
                    .organization(organization)
                    .isGuestUser(false)
                    .isPasswordResetRequired(false)
                    .build();

            usersRepository.save(users);


            //Role User Mapping
            RoleUserMapping roleUserMapping = RoleUserMapping.builder()
                    .role(role)
                    .user(users)
                    .build();

            roleUserMappingRepository.save(roleUserMapping);



            // Email object creation
            EmailDetails emailDetails = EmailDetails.builder()
                    .toEmail(users.getPrimaryEmail())
                    .firstName(users.getFirstName())
                    .body("Testing")
                    .build();

            //Calling Mail service
            try {
                sesService.sendEmail(emailDetails);
            } catch (Exception e) {
                throw new RuntimeException("Error while sending mail");
            }

            ApiResponse<OrganizationDto> response = new ApiResponse<>(HttpStatus.OK.value(), "Organization created successfully", new OrganizationDto(organization, users));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (BadRequestException ex) {
            ApiResponse<OrganizationDto> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            ApiResponse<OrganizationDto> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ApiResponse<OrganizationDto>> getOrganizationById(Integer organizationCode) {
        try {
            Organization organization = organizationRepository.findById(organizationCode).orElseThrow(() -> new NotFoundException("Organization not found with ID: " + organizationCode));
            OrganizationDto organizationDTO = new OrganizationDto(organization);

            ApiResponse<OrganizationDto> response = new ApiResponse<>(HttpStatus.OK.value(), "Organization retrieved successfully", organizationDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            ApiResponse<OrganizationDto> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ApiResponse<List<OrganizationDto>>> getAllOrganizations() {
        try {
            List<Organization> organizations = organizationRepository.findAll();
            List<OrganizationDto> organizationDtos = organizations.stream()
                    .map(OrganizationDto::new)
                    .collect(Collectors.toList());

            ApiResponse<List<OrganizationDto>> response = new ApiResponse<>(HttpStatus.OK.value(), "Organizations retrieved successfully", organizationDtos);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            ApiResponse<List<OrganizationDto>> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ApiResponse<OrganizationDto>> updateOrganization(OrganizationDto organizationdto) {
        try {
            Organization existingOrganization = organizationRepository.findById(organizationdto.getOrganizationCode()).orElseThrow(() -> new NotFoundException("Organization not found with ID: " + organizationdto.getOrganizationCode()));
            existingOrganization.setName(organizationdto.getName());
            existingOrganization.setContactNum(organizationdto.getContactNum());
            existingOrganization.setSize(organizationdto.getSize());
            existingOrganization.setCountry(organizationdto.getCountry());
            organizationRepository.save(existingOrganization);
            OrganizationDto updatedOrganizationDto = new OrganizationDto(existingOrganization);

            ApiResponse<OrganizationDto> response = new ApiResponse<>(HttpStatus.OK.value(), "Organization updated successfully", updatedOrganizationDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException ex) {
            ApiResponse<OrganizationDto> response = new ApiResponse<>(HttpStatus.NOT_FOUND.value(), ex.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

        } catch (Exception ex) {
            ApiResponse<OrganizationDto> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ApiResponse<OrganizationDto>> deleteOrganization(Integer organizationCode) {
        try {
            Organization organization = organizationRepository.findById(organizationCode).orElseThrow(() -> new NotFoundException("Organization not found with ID: " + organizationCode));
            List<Users> users = usersRepository.getByOrganization(organization);
            if (users.isEmpty()) {
                organization.updateStatus(false);
                organizationRepository.save(organization);
            } else {
                throw new RuntimeException("Unable to delete.There are users in this organization.");
            }
            ApiResponse<OrganizationDto> response = new ApiResponse<>(HttpStatus.OK.value(), "Organization deleted successfully", null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException ex) {
            ApiResponse<OrganizationDto> response = new ApiResponse<>(HttpStatus.NOT_FOUND.value(), ex.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (RuntimeException ex) {
            ApiResponse<OrganizationDto> response = new ApiResponse<>(HttpStatus.NOT_ACCEPTABLE.value(), ex.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception ex) {
            ApiResponse<OrganizationDto> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
