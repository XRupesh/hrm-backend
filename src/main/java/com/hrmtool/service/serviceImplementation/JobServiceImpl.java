package com.hrmtool.service.serviceImplementation;

import com.hrmtool.globalHandler.response.ApiResponse;
import com.hrmtool.persistance.dto.JobDto;
import com.hrmtool.persistance.entity.Department;
import com.hrmtool.persistance.entity.Job;
import com.hrmtool.persistance.entity.Organization;
import com.hrmtool.persistance.entity.Role;
import com.hrmtool.persistance.repository.JobRepo;
import com.hrmtool.persistance.repository.OrganizationRepo;
import com.hrmtool.persistance.repository.RoleRepo;
import com.hrmtool.service.JobService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class JobServiceImpl implements JobService {

    private JobRepo jobRepository;
    private OrganizationRepo organizationRepository;
    private RoleRepo roleRepository;

    @Override
    public ResponseEntity<ApiResponse<List<JobDto>>> getAllJobs() {
        List<JobDto> jobList = jobRepository.findAll().stream()
                .map(JobDto::new)
                .collect(Collectors.toList());

        ApiResponse<List<JobDto>> response = new ApiResponse<>(HttpStatus.OK.value(), "Job List Fetched successfully", jobList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse<JobDto>> getJobById(Integer jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found with id: " + jobId));
        ApiResponse<JobDto> response = new ApiResponse<>(HttpStatus.OK.value(), "Job Fetched successfully", new JobDto(job));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse<JobDto>> createJob(JobDto jobDto) {
        Organization organization = organizationRepository.findById(jobDto.getOrganizationId())
                .orElseThrow(() -> new RuntimeException("Organization not found with id: " + jobDto.getOrganizationId()));
        Role role = roleRepository.findById(jobDto.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + jobDto.getRoleId()));
        Job job = jobDto.toJob(role,organization);
        Job savedJob = jobRepository.save(job);
        ApiResponse<JobDto> response = new ApiResponse<>(HttpStatus.OK.value(), "Job created successfully", new JobDto(savedJob));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse<JobDto>> updateJob(Integer jobId, JobDto jobDto) {
        Job currentJob = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found with id: " + jobId));
        Role role = roleRepository.findById(jobDto.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + jobDto.getRoleId()));
        Organization organization = organizationRepository.findById(jobDto.getOrganizationId())
                .orElseThrow(() -> new RuntimeException("Organization not found with id: " + jobDto.getOrganizationId()));

        currentJob.setName(jobDto.getName());
        currentJob.setRole(role);
        currentJob.setOrganization(organization);

        Job updatedJob = jobRepository.save(currentJob);
        ApiResponse<JobDto> response = new ApiResponse<>(HttpStatus.OK.value(), "Job updated successfully", new JobDto(updatedJob));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse<JobDto>> deleteJob(Integer jobId) {
        jobRepository.deleteById(jobId);
        ApiResponse<JobDto> response = new ApiResponse<>(HttpStatus.OK.value(), "Job updated successfully", null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
