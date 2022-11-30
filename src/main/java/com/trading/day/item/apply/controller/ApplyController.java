package com.trading.day.item.apply.controller;


import com.trading.day.item.apply.domain.ApplyDTO;
import com.trading.day.item.apply.service.ApplyService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/************
 * @info : 프로젝트 지원서 컨트롤러
 * @name : applyController
 * @date : 2022/11/30 2:19 AM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description :
 ************/
@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("*")
@RequestMapping("/apply/v1/")
public class ApplyController {

    private final ApplyService service;

    /**
     * @info    : 지원서 작성(저장)
     * @name    : saveApply
     * @date    : 2022/11/30 4:48 PM
     * @author  : SeokJun Kang(swings134@gmail.com)
     * @version : 1.0.0
     * @param   :
     * @return  :
     * @Description :
     */
    @ApiOperation(value = "지원서 저장 API", notes = "지원서 작성시 저장.")
    @PostMapping("save")
    public int saveApply(@RequestBody ApplyDTO.ApplyRequest inDTO) {
        int result = service.savePost(inDTO);
        return result;
    }

   /**
    * @info    : 지원서 단건 조회
    * @name    : findByApplyId
    * @date    : 2022/11/30 4:48 PM
    * @author  : SeokJun Kang(swings134@gmail.com)
    * @version : 1.0.0
    * @param   :
    * @return  :
    * @Description :
    */
    public ApplyDTO findByApplyId(Long applyId) {
        ApplyDTO outDTO = service.findByApplyId(applyId);
        return outDTO;
    }


}//class
