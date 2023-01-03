package com.trading.day.common.file;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


/************
* @info : 이미지 파일 컨트롤러
* @name : ImageFileController
* @date : 2022/11/01 10:21 PM
* @author : SeokJun Kang(swings134@gmail.com)
* @version : 1.0.0
************/
@RestController
@RequiredArgsConstructor
@RequestMapping("/image/v1/")
public class ImageFileController {

    private final ImageFileService service;

    // TODO : Image File 단독으로 사용 필요시 Service Method 조건에 맞춰 작성 할것.
    /**
     * @info    : 이미지 단건 출력.
     * @name    : showImageByBoardId
     * @date    : 2022/11/08 8:30 PM
     * @author  : SeokJun Kang(swings134@gmail.com)
     * @version : 1.0.0
     * @param   :
     * @return  :
     * @Description :
     */

    @ApiOperation(value = "이미지 출력", notes = "게시판 Id로 이미지 출력")
    @PreAuthorize("isAnonymous()")
    @GetMapping("showImage")
    @ResponseBody
    public Resource showImageByBoardId(@RequestParam("boardId") Long boardId) throws IOException {

        Optional<ImageFile> imageFile = service.showImageByBoardId(boardId);
        return new UrlResource("file:" + imageFile.get().getSavePath());
    }

    /**
     * @info    : image 다건 출력
     * @name    : showImage
     * @date    : 2022/11/24 4:10 PM
     * @author  : SeokJun Kang(swings134@gmail.com)
     * @version : 1.0.0
     * @param   :
     * @return  :
     * @Description :
     */
    @GetMapping("showImageList")
    @PreAuthorize("isAnonymous()")
    @ResponseBody
    public void showImageList(@RequestParam List<Long> list) throws IOException{
        List<ImageFile> resultList = service.showImageList(list);

//        List<UrlResource> resourceList = new ArrayList<>();
//        for (int i = 0; i < resourceList.size(); i++) {
//            resourceList.add(new UrlResource(resultList.get(i).getSavePath()));
//        }
//        System.out.println(resourceList.get(0));
        return ;
    }

}
