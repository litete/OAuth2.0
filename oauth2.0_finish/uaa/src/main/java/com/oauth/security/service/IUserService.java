package com.oauth.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oauth.security.entity.UserAdmin;

/**
 * @ClassName IUserService
 * @Description
 * @Author Zesysterm
 * @Date 2020/5/4 10:37
 * @Version 1.0
 **/
public interface IUserService extends IService<UserAdmin> {

    UserAdmin selectUserByUsername(String username);
}
