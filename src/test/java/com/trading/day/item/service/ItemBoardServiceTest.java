package com.trading.day.item.service;

import com.trading.day.item.domain.ItemBoard;
import com.trading.day.item.domain.ItemBoardDTO;
import com.trading.day.item.repository.ItemBoardJpaRepository;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ItemBoardServiceTest {

    @Autowired
    private ItemBoardJpaRepository repository;


    /**
     * 디테일 화면 조회 - 리팩토링
     */
    @Test
    void detailPostRefacor() {
        ModelMapper modelMapper = new ModelMapper();
        // given
        Long id = 4L;

        // when
        ItemBoard result = Optional.ofNullable(repository.findByBoardRefactor(id))
                .orElseThrow(() ->
                        new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));

        // then
//        Assertions.assertEquals(id, result.getId());

        ItemBoardDTO map = modelMapper.map(result, ItemBoardDTO.class);

        System.out.println("res = " + map);
        System.out.println("view = " + result.getView());
        Assertions.assertEquals(id, map.getId());
    }
}