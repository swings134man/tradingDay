package com.trading.day.batch.member_active;


import com.trading.day.batch.member_active.listener.InActiveChunkListener;
import com.trading.day.batch.member_active.listener.InActiveJobListener;
import com.trading.day.batch.member_active.listener.inActiveStepListener;
import com.trading.day.member.domain.Member;
import com.trading.day.member.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;

/************
 * @info : InActive Member - Batch Job
 * @name : InActiveMemberJobConfig
 * @date : 2023/01/27 4:50 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description : 1년 이상 비로그인 계정 비활성화 배치 잡
 ************/
@Configuration
@RequiredArgsConstructor
@Slf4j
public class InActiveMemberJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final MemberJpaRepository repository;

    @Bean
    public Job inActiveJob() {
        return jobBuilderFactory.get("inActive_member_job")
                .preventRestart()
                .incrementer(new RunIdIncrementer())
                .listener(new InActiveJobListener())
                .start(inActiveMemberStep())
                .build();
    }

    @Bean
    public Step inActiveMemberStep() {
        return stepBuilderFactory.get("inActiveMemberStep")
                .<Member, Member>chunk(10)
//                .listener(new inActiveStepListener()) // stepListener
                .reader(inActiveReader())
                .processor(inActiveProcessor())
                .writer(inActiveWriter())
                .listener(new InActiveChunkListener())
                .build();
    }

    //Reader
    @Bean
    @StepScope
    public ListItemReader<Member> inActiveReader() {
        LocalDateTime currentDateTime = LocalDateTime.now(); // 2023-01-27T16:34:30.123

        List<Member> result =
                repository.findByLastLoginTimeBeforeAndActivatedEquals(currentDateTime.minusYears(1), true);

        return new ListItemReader<>(result);
    }

    //Processor
    private ItemProcessor<Member, Member> inActiveProcessor() {
        return new ItemProcessor<Member, Member>() {
            @Override
            public Member process(Member item) throws Exception {
                return item.setActivatedFalse();
            }
        };
    }

    //Writer
    private ItemWriter<Member> inActiveWriter() {
        return ((List<? extends Member> members) -> repository.saveAll(members));
    }
}
