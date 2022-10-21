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

    @ApiOperation(value = "게시물 단건 다건 삭제 api", notes = "프론트에서 넘어오는 체크박스의 갯수에 따라 단건 다건 삭제함")
    @DeleteMapping("/deleteQna")
    public int deleteQna(QnaDTO qnaInDTO) { // @RequestParam List<String> values
        System.out.println("qnaInDTO controller : "  + qnaInDTO.getQnaId());
        return qnaService.deleteQna(qnaInDTO);
    }

    @ApiOperation(value = "writer로 해당 고객이 남긴 문의글 조회 api", notes = "해당 고객이 남긴 문의글만 조회함")
    @GetMapping(value = "/qnaByWriter")
    public List<Qna> findByWriter(@RequestParam String writer) {
        return qnaService.findByWriter(writer);
    }

    @ApiOperation(value = "qna문의 수정 api", notes = "해당 고객이 남긴 문의글을 수정함")
    @PutMapping("/updateQna")
    public QnaDTO updateQna(QnaDTO qnaInDTO) {
        return qnaService.updateQna(qnaInDTO);
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
