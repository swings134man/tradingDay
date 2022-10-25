package com.trading.day;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class IndexController {


    @GetMapping("/v2/test")
    public String index() {
        return "됐나요?";
    }


    @GetMapping("/api/hello")
    public String index2() {
        return "이거니??";
    }
}
