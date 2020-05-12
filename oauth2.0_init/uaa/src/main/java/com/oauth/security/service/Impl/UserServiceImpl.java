package com.oauth.security.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oauth.security.dao.UserMapper;
import com.oauth.security.entity.UserAdmin;
import com.oauth.security.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName UserServiceImpl
 * @Description
 * @Author 戴书博
 * @Date 2020/5/4 10:38
 * @Version 1.0
 **/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserAdmin> implements IUserService {

    @Override
    public UserAdmin selectUserByUsername(String username){
        QueryWrapper<UserAdmin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        List<UserAdmin> list = this.baseMapper.selectList(queryWrapper);
        if(list != null && list.size() > 0){
            return list.get(0);
        }else{
            return null;
        }
    }
}
