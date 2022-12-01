package com.trading.day.item.apply.domain;


import lombok.*;
import org.springframework.data.domain.Page;

/************
 * @info : 프로젝트 지원서 DTO
 * @name : applyDTO
 * @date : 2022/11/30 2:18 AM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description :
 ************/
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplyDTO {

    private Long applyId;            /* 지원서ID */
    private String title;            /* 지원서 제목 */
    private String content;          /* 내용 */
    private String type;             /* 지원분야 */
    private String level;            /* 스킬 레벨 */
    private String writer;           /* 지원자 Member_ID */
    private String writerEmail;      /* 지원자 Email */

    // Board
    private Long itemBoard;          /* 게시물 ID */

    // Member
    private Long member;             /* 회원 ID */


        // Paging
        public Page<ApplyDTO> toPageDTO(Page<Apply> entity) {
            Page<ApplyDTO> applyListPage = entity.map(m ->
                    ApplyDTO.builder()
                            .applyId(m.getApplyId())
                            .title(m.getTitle())
                            .content(m.getContent())
                            .type(m.getType())
                            .level(m.getLevel())
                            .writer(m.getWriter())
                            .writerEmail(m.getWriterEmail())
                            .itemBoard(m.getItemBoard().getId())
                            .member(m.getMember().getMemberNo())
                            .build()
                    );
            return applyListPage;
        } //page



    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ApplyRequest {
        private Long applyId;            /* 지원서ID */
        private String title;            /* 지원서 제목 */
        private String content;          /* 내용 */
        private String type;             /* 지원분야 */
        private String level;            /* 스킬 레벨 */
        private String writer;           /* 지원자 Member_ID */
        private String writerEmail;      /* 지원자 Email */

        private Long itemBoard;          /* 게시물 ID */
        private Long member;             /* 회원 ID */
        private String memberId;         /* 회원 ID */ // -> Long Type 변경후 다시 set 필요. -> 화면에서 받을때만 사용함.
    }

}
