package com.trading.day.qna.answer.controller;

import com.trading.day.qna.answer.domain.AnswerDTO;
import com.trading.day.qna.answer.service.AnswerService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * packageName : com.trading.day.qna.answer.controller
 * fileName : AnswerController
 * author : taeil
 * date : 2022/11/18
 * description :
 * =======================================================
 * DATE          AUTHOR                      NOTE
 * -------------------------------------------------------
 * 2022/11/18        taeil                   최초생성
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("*")
@RequestMapping("/answer/v1")
public class AnswerController {

    private final AnswerService answerService;

    @ApiOperation(value = "문의에 대한 답변 저장 api", notes = "문의에 대한 답변을 저장함")
    @PostMapping("/save")
    public AnswerDTO answerSave(@RequestBody AnswerDTO inDTO) {
        return answerService.answerSave(inDTO);
    }

    @ApiOperation(value = "문의에 대한 답변 수정 api", notes = "문의에 대한 답변을 수정함")
    @PutMapping("/update")
    public AnswerDTO answerUpdate(@RequestBody AnswerDTO inDTO) {
        return answerService.answerUpdate(inDTO);
    }

    @ApiOperation(value = "문의 답변 삭제 API", notes = "문의 답변을 pk로 삭제함")
    @DeleteMapping("/delete")
    public int answerDelete(@RequestParam Long id) {
        return answerService.answerDelete(id);
    }




}