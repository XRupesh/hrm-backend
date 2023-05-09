package com.hrmtool.service;

import com.hrmtool.globalHandler.response.ApiResponse;
import com.hrmtool.persistance.dto.DepartmentDto;
import com.hrmtool.persistance.dto.JobDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface JobService {
    ResponseEntity<ApiResponse<List<JobDto>>> getAllJobs();
    ResponseEntity<ApiResponse<JobDto>> getJobById(Integer jobId);
    ResponseEntity<ApiResponse<JobDto>> createJob(JobDto jobDto);
    ResponseEntity<ApiResponse<JobDto>> updateJob(Integer jobId, JobDto jobDto);
    ResponseEntity<ApiResponse<JobDto>> deleteJob(Integer jobId);
}
