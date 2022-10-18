package com.trading.day.qna.controller;


import com.trading.day.qna.domain.Qna;
import com.trading.day.qna.domain.QnaDTO;
import com.trading.day.qna.service.QnaService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/qna/v1")
public class QnaController {

    private final QnaService qnaService;


    @ApiOperation(value = "문의글 리스트 전체 조회 api", notes = "조건에 상관없이 모든 문의를 조회함")
    @GetMapping(value = "/qnaList")
    public List<Qna> findAll() {
        return qnaService.findAll();
    }

    @ApiOperation(value = "memberId로 해당 고객이 남긴 문의글 조회 api", notes = "해당 고객이 남긴 문의글만 조회함")
    @GetMapping(value = "/qnaById")
    public List<Qna> findById(@RequestParam String memberNo) {
        return qnaService.findById(memberNo);
    }

    @ApiOperation(value ="문의글 저장 api", notes = "문의글 작성시 저장함")
    @PostMapping("/qna")
//    public QnaDTO saveQna( @RequestParam String title ,
//                           @RequestParam String writer,
//                           @RequestParam Long memberId) {

    public QnaDTO saveQna(QnaDTO inQnaDTO) {
        return qnaService.saveQna(inQnaDTO);
    }





}
