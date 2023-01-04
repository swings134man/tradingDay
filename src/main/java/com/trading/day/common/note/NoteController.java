package com.trading.day.common.note;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


    /**
     * @info    : 쪽지 발송 (save)
     * @name    : sendNote
     * @date    : 2023/01/04 6:55 PM
     * @author  : SeokJun Kang(swings134@gmail.com)
     * @version : 1.0.0
     * @Description :
     */
    @ApiOperation(value = "note 쪽지 저장(전송)", notes = "쪽지 전송")
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("sendnote")
    public NoteDTO sendNote(@RequestBody NoteDTO inDTO) {
        log.debug("전송 값 : " + inDTO);

        return service.sendNote(inDTO);
    }

    /**
     * @info    : 쪽지 리스트 페이징 - receiveId 검색 (마이페이지)
     * @name    : findByReceivePage
     * @date    : 2023/01/04 7:29 PM
     * @author  : SeokJun Kang(swings134@gmail.com)
     * @version : 1.0.0
     * @param   : String receiveId
     * @return  :
     * @Description :
     */
    @ApiOperation(value = "나에게 온 쪽지 목록 확인 API", notes = "receiveID로 검색")
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("findByReceivePage")
    public Page<NoteDTO> findByReceivePage(@RequestParam(required = true) String receiveMemberId,
                                  @PageableDefault(size = 10, sort = "note_no", direction = Sort.Direction.DESC) Pageable pageable ) {

        Page<NoteDTO> result = service.findByReceivePage(receiveMemberId, pageable);
        return result;
    }

    /**
     * @info    : 쪽지 상세 정보 - 읽음표시 기능 추가
     * @name    : noteDetail
     * @date    : 2023/01/04 7:30 PM
     * @author  : SeokJun Kang(swings134@gmail.com)
     * @version : 1.0.0
     * @param   :
     * @return  :
     * @Description : 읽음 표시 기능 추가
     */
    // 쪽지 상세 정보 - detail 볼때 status/ Y 변경 해야함.
    @ApiOperation(value = "쪽지 상세 정보 보기", notes = "쪽지 상세정보 및, 읽음표시")
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("noteDetail")
    public NoteDTO noteDetail(@RequestParam Long noteNo) {
        NoteDTO noteDTO = service.noteDetail(noteNo);
        return noteDTO;
    }

    // 쪽지 삭제 다,단건
    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("delete")
    public boolean delete(@RequestParam(name = "data") List<Long> list) {
        boolean res = service.delete(list);
        return res;
    }

    // 쪽지 삭제 다건


}
