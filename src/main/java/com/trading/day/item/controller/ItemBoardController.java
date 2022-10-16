package com.trading.day.item.controller;

import com.trading.day.item.domain.ItemBoardDTO;
import com.trading.day.item.service.ItemBoardService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

/************
* @info : Item 물품 게시판 컨트롤러 클래스
* @name : ItemBoardController
* @date : 2022/10/14 2:10 AM
* @author : SeokJun Kang(swings134@gmail.com)
* @version : 1.0.0
************/
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/item/v1/")
public class ItemBoardController {

    private final ItemBoardService service;

    /**
    * @info    : 게시물 작성
    * @name    : savePost
    * @date    : 2022/10/14 2:16 AM
    * @author  : SeokJun Kang(swings134@gmail.com)
    * @version : 1.0.0
    * @param   : ItemBoardDTO
    * @return  : ItemBoardDTO
    */
    @ApiOperation(value = "게시물 저장 API", notes = "게시물 작성시 저장.")
    @PostMapping("savePost")
    // parameter @RequestBody 확인 필요
    public ItemBoardDTO savePost(ItemBoardDTO inDTO) {
        inDTO.setView(0L); // 초기 게시판 생성시 조회수는 0 default
        ItemBoardDTO result = service.savePost(inDTO);
        return result;
    }//savePost


    /**
    * @info    : 게시판 전체조회 Paging
    * @name    : findAllPage
    * @date    : 2022/10/14 4:54 PM
    * @author  : SeokJun Kang(swings134@gmail.com)
    * @version : 1.0.0
    * @param   : Pageable
    * @return  : Page<ItemBoardDTO>
    */
    @ApiOperation(value = "게시물 전체조회 Paging API", notes = "조건 상관없이 모든 게시물 조회 후 페이징처리.")
    @GetMapping("findAllPage")
    public Page<ItemBoardDTO> findAllPage(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ItemBoardDTO> result = service.findAllPage(pageable);
        return result;
    }//findAllPage


    /**
    * @info    : 게시물 조건검색 페이징(제목, 작성자 중1)
    * @name    : findTitleOrWriter
    * @date    : 2022/10/14 7:21 PM
    * @author  : SeokJun Kang(swings134@gmail.com)
    * @version : 1.0.0
    * @param   :
    * @return  :
    */
    @ApiOperation(value = "게시물 조건 검색 API", notes = "작성자, 제목 중 하나의 조건으로 조회 후 페이징 처리.")
    @GetMapping("findTitleOrWriter")
    public Page<ItemBoardDTO> findTitleOrWriter(@RequestParam(required = true) String keyWord, @RequestParam String keyType,
                                  @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
                                  ) {

        ItemBoardDTO inDTO = new ItemBoardDTO().findTitleOrWriter(keyType, keyWord);
        Page<ItemBoardDTO> result = service.findTitleOrWriter(inDTO, pageable);
        return result;
    }




    /**
    * @info    : 게시물 한개 삭제
    * @name    : deletePostOne
    * @date    : 2022/10/14 5:08 PM
    * @author  : SeokJun Kang(swings134@gmail.com)
    * @version : 1.0.0
    * @param   : Long
    * @return  : int
    */
    @ApiOperation(value = "게시물 삭제 API", notes = "선택한 게시물 한개 삭제.")
    @DeleteMapping("deletePostOne")
    public int deletePostOne(@RequestParam Long id) {
        ItemBoardDTO inDTO = new ItemBoardDTO();
        inDTO.setId(id);

        int result = service.deletePostOne(inDTO);
        // Page path
//        if(result == 1){
//            return "/items/success";
//        }else {
//            return "/items/fail";
//        }
        return result;
    }//deletePostOne


    /**
    * @info    : 게시물 한개 수정
    * @name    : updatePost
    * @date    : 2022/10/14 5:09 PM
    * @author  : SeokJun Kang(swings134@gmail.com)
    * @version : 1.0.0
    * @param   : ItemBoardDTO
    * @return  : ItemBoardDTO
    */
    @ApiOperation(value = "게시물 정보 수정 API", notes = "게시물 수정시 저장.")
    @PutMapping("updatePost")
    public ItemBoardDTO updatePost(ItemBoardDTO inDTO) {
        ItemBoardDTO result = service.updatePost(inDTO);
        return result;
    }//updatePost

}//class