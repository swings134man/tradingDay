package com.trading.day.common.file;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/************
* @info : 이미지 파일 서비스
* @name : ImageFileService
* @date : 2022/11/01 9:15 PM
* @author : SeokJun Kang(swings134@gmail.com)
* @version : 1.0.0
************/
@Service
@RequiredArgsConstructor
@Slf4j
public class ImageFileService {

    @Value("${file.dir}")
    private String filePath; // file path : /Users/seokjunKang/intellij-gradle/day-file/

    private final ImageFileJpaRepository repository; //repo
    private final ModelMapper modelMapper;


    /**
    * @info    : 이미지 파일 save
    * @name    : saveImage
    * @date    : 2022/11/01 10:08 PM
    * @author  : SeokJun Kang(swings134@gmail.com)
    * @version : 1.0.0
    * @param   :
    * @return  :
    */
    public ImageFile saveImage(MultipartFile files) throws IOException {
        if(files.isEmpty()){
            return null;
        }

        // 파일 원본 이름 추출
        String origName = files.getOriginalFilename();
        // 파일이름으로 사용할 uuid
        String uuid = UUID.randomUUID().toString();
        // 확장자 추출
        String extension = origName.substring(origName.lastIndexOf("."));
        // uuid + 확장자 결합
        String saveName = uuid + extension;
        // 파일을 불러올 떄 사용할 파일 경로
        String savePath = filePath + saveName;

        //Entity
        ImageFile file = ImageFile.builder()
                .orgNm(origName)
                .saveNm(saveName)
                .savePath(savePath)
                .build();

        // Directory에 uuid를 파일명으로 저장
        files.transferTo(new File(savePath));

        // DB 파일정보 save
        ImageFile saveResult = repository.save(file);

        return file;
    }//save


    /**
     * @info    : 이미지 다건 save
     * @name    : saveImageList
     * @date    : 2022/11/04 5:24 PM
     * @author  : SeokJun Kang(swings134@gmail.com)
     * @version : 1.0.0
     * @param   :
     * @return  :
     * @Description :
     */

    public List<ImageFile> saveImageList(List<MultipartFile> files) throws IOException {
        if(files.isEmpty()){
            return null;
        }
        List<ImageFile> list = new ArrayList<>();

        for(MultipartFile file : files) {

            // 파일 원본 이름 추출
            String origName = file.getOriginalFilename();
            // 파일이름으로 사용할 uuid
            String uuid = UUID.randomUUID().toString();
            // 확장자 추출
            String extension = origName.substring(origName.lastIndexOf("."));
            // uuid + 확장자 결합
            String saveName = uuid + extension;
            // 파일을 불러올 떄 사용할 파일 경로
            String savePath = filePath + saveName;

            //Entity
            ImageFile fileEntity = ImageFile.builder()
                    .orgNm(origName)
                    .saveNm(saveName)
                    .savePath(savePath)
                    .build();

            // Directory에 uuid를 파일명으로 저장
            file.transferTo(new File(savePath));

            list.add(fileEntity);
        }

        // DB 파일정보 save
        List<ImageFile> imageFiles = repository.saveAll(list);

        return imageFiles;
    }//save

    /************
    * @info : 이미지 출력
    * @name : ImageFileService
    * @date : 2022/11/01 10:16 PM
    * @author : SeokJun Kang(swings134@gmail.com)
    * @version : 1.0.0
    ************/
    // TODO : 게시판 연동시 사용방법 구체화 필요.
    public Optional<ImageFile> showImage(Long id) {
        Optional<ImageFile> fileEntity = repository.findById(id);

        return fileEntity;
    }// showImage

    /**
     * @info    : 게시판 ID로 해당 이미지 출력
     * @name    : showImageByBoardId
     * @date    : 2022/11/08 5:12 PM
     * @author  : SeokJun Kang(swings134@gmail.com)
     * @version : 1.0.0
     * @param   :
     * @return  :
     * @Description :
     */
    public Optional<ImageFile> showImageByBoardId(Long boardId) {
//        ImageFile result = repository.findByBoardId(boardId);

        Optional<ImageFile> result = Optional.ofNullable(repository.findByBoardId(boardId).orElseThrow(
                () -> new IllegalArgumentException("해당 이미지가 존재 하지 않습니다." + boardId)));

        return result;
    }

}//class
