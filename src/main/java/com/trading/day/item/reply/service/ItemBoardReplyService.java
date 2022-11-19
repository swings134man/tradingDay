package com.trading.day.item.reply.service;

import com.trading.day.item.domain.ItemBoard;
import com.trading.day.item.reply.domain.ItemBoardReply;
import com.trading.day.item.reply.domain.ItemBoardReplyDTO;
import com.trading.day.item.reply.repository.ItemBoardReplyJpaRepository;
import com.trading.day.item.repository.ItemBoardJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/************
* @info : Item 물품 게시판 댓글 서비스
* @name : ItemBoardReplyService
* @date : 2022/10/17 1:18 AM
* @author : SeokJun Kang(swings134@gmail.com)
* @version : 1.0.0
************/
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ItemBoardReplyService {

    private final ItemBoardReplyJpaRepository repository; //댓글
    private final ItemBoardJpaRepository boardRpository; // 게시물
    private final ModelMapper modelMapper;


    /**
    * @info    : 댓글 저장
    * @name    : replySave
    * @date    : 2022/10/17 1:05 AM
    * @author  : SeokJun Kang(swings134@gmail.com)
    * @version : 1.0.0
    * @param   : ItemBoardReplyDTO
    * @return  : ItemBoardReplyDTO
    */
    public ItemBoardReplyDTO replySave(ItemBoardReplyDTO inDTO) {
        // member 정보 -> TODO : Session 값인지, Front에서 받아올건지 결정. TEST 값 : writer(임의 입력)
//        String inWriter = inDTO.getWriter();

        // 댓글 정보 to Entity
        ItemBoardReply replyEntity = modelMapper.map(inDTO, ItemBoardReply.class); // 댓글 객체

        // Test
        System.out.println("indto : " + inDTO);


        // 게시물 정보
        Optional<ItemBoard> boardResult = boardRpository.findById(inDTO.getBoardId());
        ItemBoard boardEntity = boardResult.get();

        boardEntity.addReplys(replyEntity);
        repository.save(replyEntity);

        return modelMapper.map(replyEntity, ItemBoardReplyDTO.class);
    }

    /**
    * @info    : 댓글 수정
    * @name    : replyUpdate
    * @date    : 2022/10/16 6:41 PM
    * @author  : SeokJun Kang(swings134@gmail.com)
    * @version : 1.0.0
    * @param   : ItemBoardReplyDTO
    * @return  : ItemBoardReplyDTO
    */
    public ItemBoardReplyDTO replyUpdate(ItemBoardReplyDTO inDTO) {
        Optional<ItemBoardReply> findResult = Optional.ofNullable(repository.findById(inDTO.getId()).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다." + inDTO.getId())
        ));
        ItemBoardReply entity = findResult.get();
            entity.setContent(inDTO.getContent());

        // TODO : TEST 진행 후 게시물ID (boardId 넘길것.) -> entity.getBoardId()
        return modelMapper.map(entity, ItemBoardReplyDTO.class);
    }

    /**
    * @info    : 댓글 삭제
    * @name    : replyDelete
    * @date    : 2022/10/16 6:32 PM
    * @author  : SeokJun Kang(swings134@gmail.com)
    * @version : 1.0.0
    * @param   : Long
    * @return  : int
    */
    public int replyDelete(Long id) {
        Optional<ItemBoardReply> byId = Optional.ofNullable(repository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다." + id)
        ));

        repository.delete(byId.get());
        return 1;
    }//delete}


}//class
