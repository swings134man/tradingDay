package com.trading.day.batch.member_active;


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

import java.time.LocalDateTime;
import java.util.Date;

/************
 * @info : InActiveMember Batch - Schedule
 * @name : InActiveBatchSchedule
 * @date : 2023/01/27 5:54 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description : InActiveMemberJobConfig(jobName : inActive_member_job) 스케쥴링.
 *
 *  - 매년, 매달 수-금, 00시 Batch start
 *  - Parameters : startDate=2023-01-27 20:18:00.004000, batchJobName=InActiveMember
 ************/
@Slf4j
@Component
@EnableAsync
public class InActiveBatchSchedule {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private InActiveMemberJobConfig job;

    // 초 분 시간 일 월 요일
    @Scheduled(cron = "0 0 0 * * WED-FRI") // 운영
//    @Scheduled(cron = "0 18 20 * * WED-FRI") // test
    public void runJob() {
        JobParameters parameters = new JobParametersBuilder()
                .addDate("startDate", new Date())
                .addString("batchJobName", "InActiveMember")
                .toJobParameters();

        try {
            jobLauncher.run(job.inActiveJob(), parameters);

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
