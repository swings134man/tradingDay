package com.trading.day.item.service;

import com.trading.day.item.domain.ItemBoard;
import com.trading.day.item.domain.ItemBoardDTO;
import com.trading.day.item.repository.ItemBoardJpaRepository;
import com.trading.day.member.domain.Member;
import com.trading.day.member.repository.MemberJpaRepository;
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
import java.util.Optional;

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
    private final MemberJpaRepository memberRepository;
    private final ModelMapper modelMapper; // modelMapper

    /************
    * @info : 게시판 작성
    * @name : ItemBoardService
    * @date : 2022/10/14 1:47 AM
    * @author : SeokJun Kang(swings134@gmail.com)
    * @version : 1.0.0
    ************/
    public ItemBoardDTO savePost(ItemBoardDTO inDTO) {
        // Member 조회 -> 차후 세션 값으로 조회 가능 예정
        Member resultMember = memberRepository.findByMemberId(inDTO.getWriter()); //ID로 조회해서 PK 가져옴.


        // to Entity
        ItemBoard item = modelMapper.map(inDTO, ItemBoard.class);
        item.setMember(resultMember);
        // 게시판 save
        ItemBoard entityResult = repository.save(item);


        // Member List add
        resultMember.addItemBoards(item); // member Entity

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
    @Transactional(readOnly = true)
    public Page<ItemBoardDTO> findAllPage(Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() -1);
        pageable = PageRequest.of(page, 10, Sort.by("id").descending());
        Page<ItemBoard> findEntity = repository.findAll(pageable);

        // toDTO

//        Page<ItemBoardDTO> outDTO = (Page<ItemBoardDTO>) modelMapper.map(findEntity, ItemBoardDTO.class);
//        Page<ItemBoardDTO> outDTO = findEntity.map(m ->
//                ItemBoardDTO.builder()
//                        .id(m.getId())
//                        .title(m.getTitle())
//                        .content(m.getContent())
//                        .writer(m.getWriter())
//                        .type(m.getType())
//                        .view(m.getView())
//                        .createdDate(m.getCreatedDate())
//                        .modifiedDate(m.getModifiedDate())
//                        .build());
        // Refactoring ver.
        Page<ItemBoardDTO> outDTO = new ItemBoardDTO().toPageDTO(findEntity);
        return outDTO;
    }// Paging all


    /**
    * @info    : 게시물 조건검색 페이징(제목, 작성자 중1)
    * @name    : findTitleOrWriter
    * @date    : 2022/10/14 7:07 PM
    * @author  : SeokJun Kang(swings134@gmail.com)
    * @version : 1.0.0
    * @param   : String keyType, String keyWord, Pageable
    * @return  : Page<ItemBoardDTO>
    */
    //String keyType, String keyWord,
    @Transactional(readOnly = true)
    public Page<ItemBoardDTO> findTitleOrWriter(ItemBoardDTO inDTO ,Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() -1);
        pageable = PageRequest.of(page, 10, Sort.by("id").descending());

        ItemBoard entity = new ItemBoard();
        if(inDTO.getKeyType().equals("title")) {
            // title
            Page<ItemBoard> titleResult = repository.findByTitleContaining(inDTO.getKeyWord(), pageable);
            Page<ItemBoardDTO> outDTO = new ItemBoardDTO().toPageDTO(titleResult);

            return outDTO;
        }else {
            // writer
            Page<ItemBoard> writerResult = repository.findByWriterContaining(inDTO.getKeyWord(), pageable);
            Page<ItemBoardDTO> outDTO = new ItemBoardDTO().toPageDTO(writerResult);

            return outDTO;
        }
    }//find title or Wirter


    /**
    * @info    : 게시물 한개 삭제
    * @name    : deletePost
    * @date    : 2022/10/14 4:52 PM
    * @author  : SeokJun Kang(swings134@gmail.com)
    * @version : 1.0.0
    * @param   : MemberDTO
    * @return  : int
    */
    public int deletePostOne(ItemBoardDTO inDTO) {
        Optional<ItemBoard> findResult = repository.findById(inDTO.getId());
        if(findResult.isPresent()){
            repository.delete(findResult.get());
            return 1;
        }

        return 0;
    }//delete


    /**
    * @info    : 게시물 한개 수정
    * @name    : updatePost
    * @date    : 2022/10/14 5:08 PM
    * @author  : SeokJun Kang(swings134@gmail.com)
    * @version : 1.0.0
    * @param   : ItemBoardDTO
    * @return  : ItemBoardDTO
    */
    public ItemBoardDTO updatePost(ItemBoardDTO inDTO) {
        // 회원관리 domain 개발 완료시 -> 조회 파라미터 수정필요
        Optional<ItemBoard> entity = repository.findById(inDTO.getId());
        if(entity.isPresent()){
            ItemBoard setting = entity.get();
            setting.setId(inDTO.getId());
            setting.setTitle(inDTO.getTitle());
            setting.setContent(inDTO.getContent());
            setting.setType(inDTO.getType());
            setting.setView(setting.getView());

            // toDTO
            return modelMapper.map(setting, ItemBoardDTO.class);
        }
        return null;
    }//updatePost


}//class
