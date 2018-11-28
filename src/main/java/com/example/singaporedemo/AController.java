package com.example.singaporedemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class AController {
    @Autowired
    AService aService;
    @PostMapping("{version}/{trCode}")
    public Object get(HttpServletRequest req, @PathVariable String version, @PathVariable String trCode) throws Exception {
        BufferedReader br = req.getReader();

        String str, wholeStr = "";
        while((str = br.readLine()) != null){
            wholeStr += str;
        }
        return "{\"data\":"+aService.invoke(trCode, version, wholeStr)+",\"public\":111}";
    }


}
