package com.trading.day.batch.token_manage.listener;


import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

/************
 * @info : Token manage - Batch Step Listener
 * @name : ToKenManageStepListener
 * @date : 2023/01/30 5:51 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description :
 ************/
@Slf4j
public class ToKenManageStepListener implements StepExecutionListener {
    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.info("#### {} Batch Step STARTED", stepExecution.getStepName());
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        String stepName = stepExecution.getStepName();
        ExitStatus exitStatus = stepExecution.getExitStatus();
        log.info("#### "+ stepName +  " Step has " + exitStatus);
        return exitStatus;
    }
}
