package com.hrmtool.controller;

import com.hrmtool.config.Constant;
import com.hrmtool.dto.EmailDetails;
import com.hrmtool.globalHandler.response.ApiResponse;
import com.hrmtool.persistance.dto.OrganizationDTO;
import com.hrmtool.service.OrganizationService;
import com.hrmtool.service.SESService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = Constant.Path.Organization.ORGANIZATION_CONTROLLER,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class OrganizationController {

    private final SESService service;
    private final OrganizationService organizationService;
    private static final Logger logger = LoggerFactory.getLogger(OrganizationController.class);


    /**
     * End-point for organization registration
     *
     * @param organizationDTO : contains organization details
     * @return ResponseEntity object
     */
    @PostMapping(value = Constant.Path.Organization.ORGANIZATION_REGISTRATION)
    public ResponseEntity<ApiResponse<OrganizationDTO>> createOrganization(@RequestBody OrganizationDTO organizationDTO) {
        logger.info("Organization Registration request: {} {}", organizationDTO);
        return organizationService.createOrganization(organizationDTO);
    }

    /**
     * End-point to fetch organization by organization code
     *
     * @param organizationCode : contains organization code
     * @return ResponseEntity object
     */
    @GetMapping(value = Constant.Path.Organization.ORGANIZATION_FETCH_BY_ORGCODE)
    public ResponseEntity<ApiResponse<OrganizationDTO>> getOrganization(@PathVariable Integer organizationCode) {
        return organizationService.getOrganizationById(organizationCode);
    }

    /**
     * End-point to fetch all organization list
     *
     * @return ResponseEntity object
     */
    @GetMapping(value = Constant.Path.Organization.ORGANIZATION_FETCH_ALL)
    public ResponseEntity<ApiResponse<List<OrganizationDTO>>> getAllOrganizations() {
        return organizationService.getAllOrganizations();
    }

    /**
     * End-point for updating organization details
     *
     * @param organizationDTO : contains organization details
     * @return ResponseEntity object
     */
    @PutMapping(value = Constant.Path.Organization.ORGANIZATION_UPDATE)
    public ResponseEntity<ApiResponse<OrganizationDTO>> updateOrganization(@RequestBody OrganizationDTO organizationDTO) {
        return organizationService.updateOrganization(organizationDTO);
    }

    /**
     * End-point to delete organization by organization code
     *
     * @param organizationCode : contains organization code
     * @return ResponseEntity object
     */
    @DeleteMapping(value = Constant.Path.Organization.ORGANIZATION_DELETE)
    public ResponseEntity<ApiResponse<OrganizationDTO>> deleteOrganization(@PathVariable Integer organizationCode) {
        return organizationService.deleteOrganization(organizationCode);
    }

    @PostMapping("/sendEmail")
    public ResponseEntity<?> sendMessage(@RequestBody EmailDetails emailDetails) {
        return ResponseEntity.ok(service.sendEmail(emailDetails));
    }
}
