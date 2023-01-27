package com.trading.day.batch.member_active.listener;


import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.stereotype.Component;

/************
 * @info : InActive Member - Batch Step Listener
 * @name : inActiveStepListener
 * @date : 2023/01/27 5:31 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description :
 ************/
@Component
@Slf4j
public class inActiveStepListener  {

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        log.info("계정 비활성화(휴면계정) Step: STARTED");
    }

    @AfterStep
    public void afterStep(StepExecution stepExecution) {
        log.info("계정 비활성화(휴면계정) Step: FINISHED");
    }

}
