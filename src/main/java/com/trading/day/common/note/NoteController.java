package com.trading.day.common.note;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/************
 * @info : 쪽지 컨트롤러 클래스
 * @name : NoteController
 * @date : 2023/01/04 2:04 AM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description :
 ************/
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
@RequestMapping("/note/v1/auth/")
public class NoteController {

    private final NoteService service;


    @ApiOperation(value = "note 쪽지 저장(전송)", notes = "쪽지 전송")
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("sendnote")
    public NoteDTO sendNote(@RequestBody NoteDTO inDTO) {
        log.debug("전송 값 : " + inDTO);

        return service.sendNote(inDTO);
    }



}
