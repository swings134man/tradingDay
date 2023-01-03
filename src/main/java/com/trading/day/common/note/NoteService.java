package com.trading.day.common.note;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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




}//class
