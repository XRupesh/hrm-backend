package com.hrmtool.service;

import com.hrmtool.globalHandler.response.ApiResponse;
import com.hrmtool.persistance.dto.OrganizationDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrganizationService {
    ResponseEntity<ApiResponse<OrganizationDto>> createOrganization(OrganizationDto organization);

    ResponseEntity<ApiResponse<OrganizationDto>> getOrganizationById(Integer organizationCode);

    ResponseEntity<ApiResponse<List<OrganizationDto>>> getAllOrganizations();

    ResponseEntity<ApiResponse<OrganizationDto>> updateOrganization(OrganizationDto organization);

    ResponseEntity<ApiResponse<OrganizationDto>> deleteOrganization(Integer organizationCode);
}
