package com.hrmtool.service.serviceImplementation;

import com.hrmtool.globalHandler.BadRequestException;
import com.hrmtool.globalHandler.NotFoundException;
import com.hrmtool.globalHandler.response.ApiResponse;
import com.hrmtool.persistance.dto.OrganizationDTO;
import com.hrmtool.persistance.entity.Organization;
import com.hrmtool.persistance.entity.Users;
import com.hrmtool.persistance.repository.OrganizationRepo;
import com.hrmtool.persistance.repository.UsersRepo;
import com.hrmtool.service.OrganizationService;
import com.hrmtool.utils.PasswordEncrypterDecrypter;
import com.hrmtool.utils.RequestValidator;
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

    /**
     * Functionality related to registration of organization and admin user
     */
    @Override
    public ResponseEntity<ApiResponse<OrganizationDTO>> createOrganization(OrganizationDTO organizationDTO) {

        //Check if domain name already exist
        Optional<Organization> checkOrgDomain = organizationRepository.findByDomain(organizationDTO.getDomain());
        if (!checkOrgDomain.isEmpty()){
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

            //Setup User
            Users users = Users.builder()
                    .firstName(organizationDTO.getFirstName())
                    .lastName(organizationDTO.getLastName())
                    .username(organizationDTO.getFirstName().concat(" "+organizationDTO.getLastName()))
                    .password(PasswordEncrypterDecrypter.encrypt(organizationDTO.getPassword()))
                    .primaryEmail(organizationDTO.getPrimaryEmail())
                    .organization(organization)
                    .isGuestUser(false)
                    .isPasswordResetRequired(false)
                    .build();

            usersRepository.save(users);

            ApiResponse<OrganizationDTO> response = new ApiResponse<>(HttpStatus.OK.value(), "Organization created successfully", new OrganizationDTO(organization,users));
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (BadRequestException ex) {
            ApiResponse<OrganizationDTO> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            ApiResponse<OrganizationDTO> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ApiResponse<OrganizationDTO>> getOrganizationById(Integer organizationCode) {
        try {
            Organization organization = organizationRepository.findById(organizationCode).orElseThrow(() -> new NotFoundException("Organization not found with ID: " + organizationCode));
            OrganizationDTO organizationDTO = new OrganizationDTO(organization);

            ApiResponse<OrganizationDTO> response = new ApiResponse<>(HttpStatus.OK.value(), "Organization retrieved successfully", organizationDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            ApiResponse<OrganizationDTO> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ApiResponse<List<OrganizationDTO>>> getAllOrganizations() {
        try {
            List<Organization> organizations = organizationRepository.findAll();
            List<OrganizationDTO> organizationDTOs = organizations.stream()
                    .map(OrganizationDTO::new)
                    .collect(Collectors.toList());

            ApiResponse<List<OrganizationDTO>> response = new ApiResponse<>(HttpStatus.OK.value(), "Organizations retrieved successfully", organizationDTOs);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            ApiResponse<List<OrganizationDTO>> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ApiResponse<OrganizationDTO>> updateOrganization(OrganizationDTO organizationdto) {
        try {
            Organization existingOrganization = organizationRepository.findById(organizationdto.getOrganizationCode()).orElseThrow(() -> new NotFoundException("Organization not found with ID: " + organizationdto.getOrganizationCode()));
            existingOrganization.setName(organizationdto.getName());
            existingOrganization.setContactNum(organizationdto.getContactNum());
            existingOrganization.setSize(organizationdto.getSize());
            existingOrganization.setCountry(organizationdto.getCountry());
            organizationRepository.save(existingOrganization);
            OrganizationDTO updatedOrganizationDTO = new OrganizationDTO(existingOrganization);

            ApiResponse<OrganizationDTO> response = new ApiResponse<>(HttpStatus.OK.value(), "Organization updated successfully", updatedOrganizationDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException ex) {
            ApiResponse<OrganizationDTO> response = new ApiResponse<>(HttpStatus.NOT_FOUND.value(), ex.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

        } catch (Exception ex) {
            ApiResponse<OrganizationDTO> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ApiResponse<OrganizationDTO>> deleteOrganization(Integer organizationCode) {
        try {
            Organization organization = organizationRepository.findById(organizationCode).orElseThrow(() -> new NotFoundException("Organization not found with ID: " + organizationCode));
            List<Users> users = usersRepository.getByOrganization(organization);
            if (users.isEmpty()) {
                organizationRepository.delete(organization);
            }else {
                throw new RuntimeException("Unable to delete.There are users in this organization.");
            }
            ApiResponse<OrganizationDTO> response = new ApiResponse<>(HttpStatus.OK.value(), "Organization deleted successfully", null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (NotFoundException ex) {
            ApiResponse<OrganizationDTO> response = new ApiResponse<>(HttpStatus.NOT_FOUND.value(), ex.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        catch (RuntimeException ex) {
            ApiResponse<OrganizationDTO> response = new ApiResponse<>(HttpStatus.NOT_ACCEPTABLE.value(), ex.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
        }
        catch (Exception ex) {
            ApiResponse<OrganizationDTO> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
