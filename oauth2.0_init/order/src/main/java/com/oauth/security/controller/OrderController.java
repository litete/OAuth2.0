package com.oauth.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName OrderController
 * @Description
 * @Author 戴书博
 * @Date 2020/5/10 14:49
 * @Version 1.0
 **/
@RestController
public class OrderController {
    @GetMapping(value = "/r1")
    @PreAuthorize("hasAnyAuthority('p2')")
    public String r1() {
        return "访问资源1";
    }
}
