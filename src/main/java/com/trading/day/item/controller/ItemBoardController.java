package com.trading.day.item.controller;

import com.trading.day.item.domain.ItemBoardDTO;
import com.trading.day.item.service.ItemBoardService;
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
    @PostMapping("/item/v1/savePost")
    // parameter @RequestBody 확인 필요
    public ItemBoardDTO savePost(ItemBoardDTO inDTO) {
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
    @GetMapping("/item/v1/findAllPage")
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
    @GetMapping("/item/v1/findTitleOrWriter")
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
    @DeleteMapping("/item/v1/deletePostOne")
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
    @PutMapping("/item/v1/updatePost")
    public ItemBoardDTO updatePost(ItemBoardDTO inDTO) {
        ItemBoardDTO result = service.updatePost(inDTO);
        return result;
    }//updatePost

}//class
