package com.oauth.security.entity;

import lombok.Data;

/**
 * @ClassName User
 * @Description
 * @Author 戴书博
 * @Date 2020/5/4 10:35
 * @Version 1.0
 **/
@Data
public class UserAdmin {
    private Long id;
    private String username;
    private String password;
    private String fullname;
    private String mobile;
}
