package com.hrmtool.controller;

import com.hrmtool.config.Constant;
import com.hrmtool.globalHandler.response.ApiResponse;
import com.hrmtool.persistance.dto.JobDto;
import com.hrmtool.service.JobService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = Constant.Path.Job.JOB_CONTROLLER,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class JobController {

    private JobService jobService;

    private static final Logger logger = LoggerFactory.getLogger(JobController.class);

    @PostMapping(value = Constant.Path.Job.ADD_JOB)
    public ResponseEntity<ApiResponse<JobDto>> createJob(@RequestBody JobDto jobDto) {
        logger.info("Job creation request: {}", jobDto);
        return jobService.createJob(jobDto);
    }

    @GetMapping(value = Constant.Path.Job.FETCH_BY_JOB_ID)
    public ResponseEntity<ApiResponse<JobDto>> getJob(@PathVariable Integer jobId) {
        return jobService.getJobById(jobId);
    }

    @GetMapping(value = Constant.Path.Job.FETCH_ALL_JOB)
    public ResponseEntity<ApiResponse<List<JobDto>>> getAllJobs() {
        return jobService.getAllJobs();
    }

    @PutMapping(value = Constant.Path.Job.UPDATE_JOB)
    public ResponseEntity<ApiResponse<JobDto>> updateJob(@PathVariable Integer jobId, @RequestBody JobDto jobDto) {
        return jobService.updateJob(jobId,jobDto);
    }

    @DeleteMapping(value = Constant.Path.Job.DELETE_JOB)
    public ResponseEntity<ApiResponse<JobDto>> deleteJob(@PathVariable Integer jobId) {
        return jobService.deleteJob(jobId);
    }
}
