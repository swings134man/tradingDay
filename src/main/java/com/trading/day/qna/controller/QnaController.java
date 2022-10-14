package com.trading.day.qna.controller;


import com.trading.day.qna.domain.Qna;
import com.trading.day.qna.domain.QnaDTO;
import com.trading.day.qna.service.QnaService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/qna/v1")
public class QnaController {

    private final QnaService qnaService;


    @ApiOperation(value = "문의글 리스트 전체 조회 api", notes = "조건에 상관없이 모든 문의를 조회함")
    @GetMapping(value = "/qna")
    public List<Qna> findAll() {
        return qnaService.findAll();
    }

    @ApiOperation(value ="게시글 저장 api", notes = "게시글 작성시 저장함")
    @PostMapping("/qna")
    public QnaDTO saveQna( @RequestParam String title ,
                           @RequestParam String writer) {

        QnaDTO inQnaDTO = QnaDTO.builder()
                                .Writer(writer)
                                .title(title)
                                .build();
        return qnaService.saveQna(inQnaDTO);
    }





}
