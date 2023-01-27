package com.trading.day.batch.member_active.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.scope.context.StepContext;
import org.springframework.stereotype.Component;

/************
 * @info : InActive Member - Batch Chunk Listener
 * @name : InActiveChunkListener
 * @date : 2023/01/27 5:37 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description :
 ************/
@Component
@Slf4j
public class InActiveChunkListener implements ChunkListener {
    @Override
    public void beforeChunk(ChunkContext context) {

    }

    @Override
    public void afterChunk(ChunkContext context) {
        StepContext stepContext = context.getStepContext();
        StepExecution stepExecution = stepContext.getStepExecution();

        log.info("#### afterChunk Commit Count : {}", stepExecution.getCommitCount());
    }

    @Override
    public void afterChunkError(ChunkContext context) {
        StepContext stepContext = context.getStepContext();
        StepExecution stepExecution = stepContext.getStepExecution();

        log.warn("#### afterChunkError : {}", stepExecution.getRollbackCount());
    }
}
