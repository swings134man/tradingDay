package com.trading.day.qna.controller;


import com.trading.day.item.domain.ItemBoardDTO;
import com.trading.day.qna.domain.Qna;
import com.trading.day.qna.domain.QnaDTO;
import com.trading.day.qna.service.QnaService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;
import java.util.List;

/**
 * packageName :
 * fileName : QnaController
 * author : taeil
 * date :
 * description : 1:1 문의에 대한 controller class
 * =======================================================
 * DATE          AUTHOR                      NOTE
 * -------------------------------------------------------
 *               김태일                   최초생성
 */
@RequiredArgsConstructor
@RestController
@Slf4j
@CrossOrigin("*")
@RequestMapping("/qna/v1")
public class QnaController {

    private final QnaService qnaService;

    /**
     * methodName : findAll
     * author : TAEIL KIM
     * description :
     *
     * @return list
     */
    @ApiOperation(value = "문의글 리스트 전체 조회 api", notes = "조건에 상관없이 모든 문의를 조회함")
    @GetMapping(value = "/qnaList")
    public List<Qna> findAll() {
        return qnaService.findAll();
    }

    /**
     * methodName : findByQnaId
     * author : TAEIL KIM
     * description :
     * 상세페이지를 위한 문의글 상세 조회 api
     * @param qnaId
     * @return QnaDTO
     */
    @ApiOperation(value = "문의글 번호로 문의글 조회 api", notes = "문의글 번호로 특정 문의글 상세정보를 조회")
    @GetMapping(value = "/findByQnaId")
    public QnaDTO findByQnaId(Long qnaId) {
        return qnaService.findByQnaId(qnaId);
    }


    /**
     * methodName : deleteQna
     * author : TAEIL KIM
     * description :
     *
     * @param qnaInDTO
     * @return int
     */
    @ApiOperation(value = "게시물 단건 다건 삭제 api", notes = "프론트에서 넘어오는 체크박스의 갯수에 따라 단건 다건 삭제함")
    @DeleteMapping("/deleteQna")
    public int deleteQna(QnaDTO qnaInDTO) {
        System.out.println("qnaInDTO controller : "  + qnaInDTO.getQnaId());
        return qnaService.deleteQna(qnaInDTO);
    }

    /**
     * methodName : findByWriter
     * author : TAEIL KIM
     * description :
     *
     * @param writer
     * @param pageable
     * @return page
     */
    @ApiOperation(value = "writer로 해당 고객이 남긴 문의글 조회 페이징 api", notes = "해당 고객이 남긴 문의글 페이징 처리 조회함")
    @GetMapping(value = "/qnaByWriter")
    public Page<QnaDTO> findByWriter (
            @RequestParam String writer,
            @PageableDefault(size = 10, sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<QnaDTO> result = qnaService.findByWriter(writer, pageable);
        return result;
    }

    /**
     * methodName : findByWriter
     * author : TAEIL KIM
     * description : 전체 리스트 페이징
     *
     * @param writer
     * @param pageable
     * @return page
     */
    @ApiOperation(value = "문의글 전체 조회 페이징 api", notes = "문의글 전체 페이징 처리 조회함")
    @GetMapping(value = "/qnabylist")
    public Page<QnaDTO> findAllPaging (
            @PageableDefault(size = 10, sort = "qnaId", direction = Sort.Direction.DESC) Pageable pageable) {

        System.out.println("@@" + pageable.getPageNumber());

        Page<QnaDTO> result = qnaService.findAllPaging(pageable);
        return result;
    }



    /**
     * methodName : updateQna
     * author : TAEIL KIM
     * description :
     *
     * @param qnaInDTO
     * @return qna dto
     */
    @ApiOperation(value = "qna문의 수정 api", notes = "해당 고객이 남긴 문의글을 수정함")
    @PostMapping("/updateQna")
    public QnaDTO updateQna(@RequestBody QnaDTO qnaInDTO) {
        return qnaService.updateQna(qnaInDTO);
    }

    /**
     * methodName : saveQna
     * author : TAEIL KIM
     * description :
     *
     * @param inQnaDTO
     * @return qna dto
     */
    @ApiOperation(value ="문의글 저장 api", notes = "문의글 작성시 저장함")
    @PutMapping("/qna")
    public QnaDTO saveQna(@RequestBody QnaDTO inQnaDTO) {
        return qnaService.saveQna(inQnaDTO);
    }

}
