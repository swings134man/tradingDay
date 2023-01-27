package com.trading.day.batch.member_active.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobInstance;

/************
 * @info : InActive Member - Batch Job Listener
 * @name : InActiveJobListener
 * @date : 2023/01/27 5:45 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description :
 ************/
@Slf4j
public class InActiveJobListener implements JobExecutionListener {
    @Override
    public void beforeJob(JobExecution jobExecution) {
        String jobName = jobExecution.getJobInstance().getJobName();

        log.info("#### {} Batch Job STARTED", jobName);
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        BatchStatus status = jobExecution.getStatus();
        String jobName = jobExecution.getJobInstance().getJobName();

        if(status == BatchStatus.FAILED) {
            log.warn("{} : JOB WAS FAILED", jobName);
        }else {
            log.info("{} : JOB WAS SUCCEED", jobName);
        }
    }
}
