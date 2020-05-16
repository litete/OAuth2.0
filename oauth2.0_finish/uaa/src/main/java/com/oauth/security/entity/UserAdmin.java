package com.oauth.security.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @ClassName User
 * @Description 用户实体类
 * @Author Zesysterm
 * @Date 2020/5/4 10:35
 * @Version 1.0
 **/
@Data
@TableName("t_user")
public class UserAdmin {
    private Long id;
    private String username;
    private String password;
    private String fullname;
    private String mobile;
}
