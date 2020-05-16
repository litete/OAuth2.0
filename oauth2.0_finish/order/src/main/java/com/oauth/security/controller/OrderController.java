package com.oauth.security.controller;

import com.oauth.security.entity.UserAdmin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName OrderController
 * @Description 模拟访问资源的url
 * @Author Zesysterm
 * @Date 2020/5/10 14:49
 * @Version 1.0
 **/
@RestController
public class OrderController {

    @GetMapping(value = "/r1")
    @PreAuthorize("hasAnyAuthority('p1')")
    public String r1() {
        UserAdmin user = (UserAdmin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getFullname() + "访问资源1";
    }

    @GetMapping(value = "/r3")
    @PreAuthorize("hasAnyAuthority('p3')")
    public String r2() {
        UserAdmin user = (UserAdmin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getUsername() + "访问资源2";
    }
}
