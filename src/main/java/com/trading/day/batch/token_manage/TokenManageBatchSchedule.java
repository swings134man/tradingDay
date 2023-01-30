package com.trading.day.batch.token_manage;


import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/************
 * @info : TokenManage Batch - Schedule
 * @name : TokenManageBatchSchedule
 * @date : 2023/01/30 5:58 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description :
 ************/
@Component
@Slf4j
@EnableAsync
public class TokenManageBatchSchedule {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private TokenManageJobConfig job;

    // 초 분 시간 일 월 요일
    @Scheduled(cron = "0 0 0 * * SUN") // 운영
//    @Scheduled(cron = "0 11 18 * * MON") //test
    public void runJob(){
        JobParameters parameters = new JobParametersBuilder()
                .addDate("startDate", new Date())
                .addString("batchJobName", "TokenManage")
                .toJobParameters();

        try {
            jobLauncher.run(job.tokenManageJob(), parameters);

        } catch (JobExecutionAlreadyRunningException e) {
            e.printStackTrace();
        } catch (JobRestartException e) {
            e.printStackTrace();
        } catch (JobInstanceAlreadyCompleteException e) {
            e.printStackTrace();
        } catch (JobParametersInvalidException e) {
            e.printStackTrace();
        }
    }

}
