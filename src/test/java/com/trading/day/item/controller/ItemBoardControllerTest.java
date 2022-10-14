package com.trading.day.item.controller;

import com.trading.day.item.domain.ItemBoardDTO;
import com.trading.day.item.service.ItemBoardService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemBoardControllerTest {

    @Autowired
    ItemBoardService service;

    @Test
    void savePost() {

        ItemBoardDTO inDTO = new ItemBoardDTO();

            inDTO.setTitle("제목13" );
            inDTO.setWriter("작성자13");
            inDTO.setContent("내용13");
            inDTO.setType("중고");
            inDTO.setView(0L);

        ItemBoardDTO result = service.savePost(inDTO);
        Long findId = result.getId();
        Long wantId = 13L;

        System.out.println("DTO : " + result);

        assertThat(wantId).isEqualTo(findId);
    }

    @Test
    void findAllPage() {
    }
}