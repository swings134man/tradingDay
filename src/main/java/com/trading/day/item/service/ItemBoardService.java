package com.trading.day.item.service;

import com.trading.day.item.domain.ItemBoard;
import com.trading.day.item.domain.ItemBoardDTO;
import com.trading.day.item.repository.ItemBoardJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/************
* @info : Item 물품 게시판 서비스 클래스
* @name : ItemBoardService
* @date : 2022/10/14 1:41 AM
* @author : SeokJun Kang(swings134@gmail.com)
* @version : 1.0.0
************/
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ItemBoardService {

    private final ItemBoardJpaRepository repository;
    private final ModelMapper modelMapper; // modelMapper

    /************
    * @info : 게시판 작성
    * @name : ItemBoardService
    * @date : 2022/10/14 1:47 AM
    * @author : SeokJun Kang(swings134@gmail.com)
    * @version : 1.0.0
    ************/
    public ItemBoardDTO savePost(ItemBoardDTO inDTO) {
        // to Entity
        ItemBoard item = modelMapper.map(inDTO, ItemBoard.class);

        ItemBoard entityResult = repository.save(item);

        ItemBoardDTO outDTO = modelMapper.map(entityResult, ItemBoardDTO.class);
        return outDTO;
    }// savePost


    /************
    * @info : 게시판 전체조회 Paging
    * @name : ItemBoardService
    * @date : 2022/10/14 2:09 AM
    * @author : SeokJun Kang(swings134@gmail.com)
    * @version : 1.0.0
    ************/
    public Page<ItemBoardDTO> findAllPage(Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() -1);
        pageable = PageRequest.of(page, 10, Sort.by("id").descending());
        Page<ItemBoard> findEntity = repository.findAll(pageable);

        // toDTO

//        Page<ItemBoardDTO> outDTO = (Page<ItemBoardDTO>) modelMapper.map(findEntity, ItemBoardDTO.class);
        Page<ItemBoardDTO> outDTO = findEntity.map(m ->
                ItemBoardDTO.builder()
                        .id(m.getId())
                        .title(m.getTitle())
                        .content(m.getContent())
                        .writer(m.getWriter())
                        .type(m.getType())
                        .view(m.getView())
                        .createdDate(m.getCreatedDate())
                        .modifiedDate(m.getModifiedDate())
                        .build());



        return outDTO;
    }


}//class
