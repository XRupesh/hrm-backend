package com.hrmtool.service;

import com.hrmtool.globalHandler.response.ApiResponse;
import com.hrmtool.persistance.dto.OrganizationDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrganizationService {
    ResponseEntity<ApiResponse<OrganizationDTO>> createOrganization(OrganizationDTO organization);

    ResponseEntity<ApiResponse<OrganizationDTO>> getOrganizationById(Integer organizationCode);

    ResponseEntity<ApiResponse<List<OrganizationDTO>>> getAllOrganizations();

    ResponseEntity<ApiResponse<OrganizationDTO>> updateOrganization(OrganizationDTO organization);

    ResponseEntity<ApiResponse<OrganizationDTO>> deleteOrganization(Integer organizationCode);
}
