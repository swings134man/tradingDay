package com.trading.day.item.controller;

import com.trading.day.item.domain.ItemBoardDTO;
import com.trading.day.item.service.ItemBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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


    @GetMapping("/Item/v1/findAllPage")
    public Page<ItemBoardDTO> findAllPage(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ItemBoardDTO> result = service.findAllPage(pageable);
        return result;
    }//findAllPage



}//class
