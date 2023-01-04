package com.trading.day.common.note;

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
 * @info : 쪽지 서비스 클래스
 * @name : NoteService
 * @date : 2023/01/04 2:04 AM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description :
 ************/
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class NoteService {

    private final NoteJpaRepository repository;
    private final ModelMapper modelMapper;

    /**
     * @info    : 쪽지 발송 (save)
     * @name    : sendNote
     * @date    : 2023/01/04 5:41 PM
     * @author  : SeokJun Kang(swings134@gmail.com)
     * @version : 1.0.0
     * @Description :
     */
    @Transactional
    public NoteDTO sendNote(NoteDTO inDTO) {
        Note noteEntity = modelMapper.map(inDTO, Note.class);
        noteEntity.setStatus(false);

        // 회원 존재 유무
        Optional<Note> byReceiveMemberId =
                Optional.ofNullable(repository.findByReceiveMemberId(noteEntity.getReceiveMemberId()))
                        .orElseThrow(
                                () -> new IllegalArgumentException("해당 회원이 존재하지 않습니다. " + noteEntity.getReceiveMemberId()));

        Note saveEntity = repository.save(noteEntity);

        return modelMapper.map(saveEntity, NoteDTO.class);
    }// send

    /**
     * @info    : 쪽지 리스트 페이징 - receiveId 검색 (마이페이지)
     * @name    : findByReceivePage
     * @date    : 2023/01/04 7:26 PM
     * @author  : SeokJun Kang(swings134@gmail.com)
     * @version : 1.0.0
     * @param   :
     * @return  :
     * @Description :
     */
    @Transactional(readOnly = true)
    public Page<NoteDTO> findByReceivePage(String receiveMemberId, Pageable pageable) {
        // Paging
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() -1);
        pageable = PageRequest.of(page, 10, Sort.by("id").descending());

        // Get
        Page<Note> result = repository.findByReceiveMemberId(receiveMemberId, pageable);

        return new NoteDTO().toPageDTO(result);
    }

    /**
     * @info    : 쪽지 상세 정보 - 읽음표시 기능 추가
     * @name    : noteDetail
     * @date    : 2023/01/04 7:27 PM
     * @author  : SeokJun Kang(swings134@gmail.com)
     * @version : 1.0.0
     * @param   :
     * @return  :
     * @Description :
     */
    @Transactional
    public NoteDTO noteDetail(Long note_no) {
        Optional<Note> result = Optional.ofNullable(repository.findById(note_no).orElseThrow(
                () -> new IllegalArgumentException("해당 쪽지가 존재하지 않습니다." + note_no)
        ));
        Note noteRes = result.get();

        // 읽음 처리
        noteRes.setStatus(true);

        // toDTO
        NoteDTO outDTO = modelMapper.map(noteRes, NoteDTO.class);
        return outDTO;
    }


}//class
