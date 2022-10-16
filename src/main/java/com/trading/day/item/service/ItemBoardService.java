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
        // TODO :  Member 조회 파라미터 값에 따라 수정 -> 세션 값에서 조회 (pk, member_id) 둘중 하나.
        Member resultMember = memberRepository.findByMemberId(inDTO.getWriter()); //ID로 조회해서 PK 가져옴.


        // to Entity
        ItemBoard item = modelMapper.map(inDTO, ItemBoard.class);
        item.setMember(resultMember);
        // 게시판 save
        ItemBoard entityResult = repository.save(item);


        // Member List add
        resultMember.addItemBoards(item); // member Entity

        return modelMapper.map(entityResult, ItemBoardDTO.class);
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
        return new ItemBoardDTO().toPageDTO(findEntity);
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

            return new ItemBoardDTO().toPageDTO(writerResult);
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
        // new ResourceNotFoundException("inDTO", "id", id)) -> 대체 가능
        Optional<ItemBoard> findResult = Optional.ofNullable(repository.findById(inDTO.getId()).orElseThrow(
                () -> new IllegalArgumentException("해당 게시물이 존재 하지 않습니다.")
        ));

        // TODO : 게시물 삭제시 연관된 댓글 All Delete가 선행되어야 함. 댓글 삭제 -> 게시물 삭제.

        repository.delete(findResult.get());
        return 1;
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
        // TODO : 회원관리 domain 개발 완료시 -> 조회 파라미터 수정필요
        Optional<ItemBoard> entity = Optional.ofNullable(repository.findById(inDTO.getId()).orElseThrow(
                () -> new IllegalArgumentException("해당 게시물이 존재 하지 않습니다.")
        ));
            ItemBoard setting = entity.get();
                setting.setId(inDTO.getId());
                setting.setTitle(inDTO.getTitle());
                setting.setContent(inDTO.getContent());
                setting.setType(inDTO.getType());
                setting.setView(setting.getView());
                setting.setWriter(setting.getMember().getMemberId()); // 화면에서 받아온 게시물ID

         return modelMapper.map(setting, ItemBoardDTO.class);
    }//updatePost


    /**
    * @info    : 게시물 상세 페이지
    * @name    : detailPost
    * @date    : 2022/10/16 5:40 PM
    * @author  : SeokJun Kang(swings134@gmail.com)
    * @version : 1.0.0
    * @param   : Long
    * @return  : ItemBoardDTO
    */
    public ItemBoardDTO detailPost(Long id) {
        Optional<ItemBoard> result = Optional.ofNullable(repository.findById(id).orElseThrow(
                                                        () -> new IllegalArgumentException("해당 게시물이 존재 하지 않습니다.")));
        ItemBoard entity = result.get();
        entity.increaseView(); // 조회수 증가

        return modelMapper.map(entity, ItemBoardDTO.class);
    }

}//class
