package com.trading.day.common.file;

import com.trading.day.config.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/************
* @info : 이미지 파일 엔티티.
* @name : ImageFile
* @date : 2022/11/01 9:15 PM
* @author : SeokJun Kang(swings134@gmail.com)
* @version : 1.0.0
************/
@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "IMAGE_FILE_INFO")
public class ImageFile extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "file_id")
    private Long id;

    private String orgNm;   // 원본 파일명
    private String saveNm;  // 저장 파일명 - uuid
    private String savePath;// 저장 경로

    // 편의 메서드
    @Builder
    public ImageFile(Long id, String orgNm, String saveNm, String savePath) {
        this.id = id;
        this.orgNm = orgNm;
        this.saveNm = saveNm;
        this.savePath = savePath;
    }
}
