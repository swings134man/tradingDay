package com.trading.day.item.reply.controller;

import com.trading.day.item.reply.domain.ItemBoardReplyDTO;
import com.trading.day.item.reply.service.ItemBoardReplyService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/************
* @info : Item 물품 게시판 댓글 컨트롤러
* @name : ItemBoardReplyController
* @date : 2022/10/17 1:18 AM
* @author : SeokJun Kang(swings134@gmail.com)
* @version : 1.0.0
************/
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/item/v1/reply")
public class ItemBoardReplyController {

    private final ItemBoardReplyService service;

    /*
    input Param
       boardId     : 게시판 ID (save() 제와)
       content : 내용
       writer : 작성자 (추후 Member에 따라결정)
     */

    @ApiOperation(value = "item 게시판 댓글 저장 API", notes = "게시판 댓글 저장")
    @PostMapping("/save")
    public ItemBoardReplyDTO replySave(@RequestBody ItemBoardReplyDTO inDTO) {
        System.out.println(" Control indto : " + inDTO);
        return service.replySave(inDTO);
    }

    @ApiOperation(value = "item 게시판 댓글 수정 API", notes = "게시판 댓글 수정")
    @PutMapping("update")
    public ItemBoardReplyDTO replyUpdate(ItemBoardReplyDTO inDTO) {
        return service.replyUpdate(inDTO);
    }

    @ApiOperation(value = "item 게시판 댓글 삭제 API", notes = "게시판 댓글 삭제")
    @DeleteMapping("/delete")
    public int replyDelete(@RequestParam Long id) {
        return service.replyDelete(id);
    }


}//save
