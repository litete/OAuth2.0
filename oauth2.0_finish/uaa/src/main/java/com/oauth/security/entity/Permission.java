package com.oauth.security.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @ClassName Permission
 * @Description 权限实体类
 * @Author Zesysterm
 * @Date 2020/5/4 14:23
 * @Version 1.0
 **/
@Data
@TableName("t_permission")
public class Permission {
    private Long id;
    private String code;
    private String description;
    private String url;
}
