package com.oauth.security.service.Impl;

import com.alibaba.fastjson.JSON;
import com.oauth.security.dao.PermissionMapper;
import com.oauth.security.entity.UserAdmin;
import com.oauth.security.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName SpringDataUserDetailService
 * @Description UserDetailsService的实现类重写loadUserByUsername方法查询用户信息，然后封装成UserDetails。
 * @Author Zesysterm
 * @Date 2020/5/4 8:59
 * @Version 1.0
 **/
@Slf4j
@Service
public class SpringDataUserDetailService implements UserDetailsService {

    @Autowired
    private IUserService userService;
    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //查询用户信息
        UserAdmin userAmin = userService.selectUserByUsername(s);
        log.info("user => {}",userAmin);
        if(userAmin == null){
            return null;
        }
        //查询权限
        List<String> list =  permissionMapper.selectPermissionByUser(userAmin.getId());
        String[] arr = new String[list.size()];
        list.toArray(arr);
        String prinple = JSON.toJSONString(userAmin);
        //封装UserDetails
        UserDetails user = User.withUsername(prinple).password(userAmin.getPassword()).authorities(arr).build();
        return user;
    }
}
