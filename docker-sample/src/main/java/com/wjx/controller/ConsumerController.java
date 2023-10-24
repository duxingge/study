package com.wjx.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author wangjiaxing
 * @Date 2022/3/3
 */
@RestController
public class ConsumerController {
     AtomicInteger c = new AtomicInteger();
    @GetMapping("/test")
    public String consume(HttpServletRequest request) {
        return request.getRemoteHost() +" UID: " + UUID.randomUUID().toString();
    }
}
