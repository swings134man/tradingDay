package com.trading.day.batch.token_manage;


import com.trading.day.batch.token_manage.listener.ToKenManageStepListener;
import com.trading.day.batch.token_manage.listener.TokenManageJobListener;
import com.trading.day.jwtToken.domain.TokenManage;
import com.trading.day.jwtToken.repository.TokenManageJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/************
 * @info : Token Manage - Batch Job
 * @name : TokenManageJobConfig
 * @date : 2023/01/30 5:07 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description : 2주 이상된 RT(Refresh Token) Data 삭제 - 배치
 * - 단일 Step 구성
 ************/
@Configuration
@RequiredArgsConstructor
@Slf4j
public class TokenManageJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final TokenManageJpaRepository repository;


    @Bean
    public Job tokenManageJob() {
        return jobBuilderFactory.get("token_manage_job")
                .preventRestart()
                .incrementer(new RunIdIncrementer())
                .listener(new TokenManageJobListener())
                .start(step())
                .build();
    }

    @Bean
    public Step step() {
        return stepBuilderFactory.get("token_manage_step")
                .tasklet((contribution, chunkContext) -> {
                    // 2023-01-02 00:00:00, 14일 이전
                    String formatDate = LocalDateTime.now().minusDays(14).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    List<TokenManage> result = repository.findByModifiedDateLessWeek(formatDate);

                    // delete
                    if(result.size() > 0 && result != null) {
                        for (TokenManage token: result) {
                            repository.deleteById(token.getTokenId());
                        }
                    }

                    return RepeatStatus.FINISHED;
                })
                .listener(new ToKenManageStepListener())
                .build();
    }

}
