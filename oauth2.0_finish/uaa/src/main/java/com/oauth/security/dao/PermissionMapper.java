package com.oauth.security.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oauth.security.entity.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName PermissionMapper
 * @Description 查询对应用户的权限信息。
 * @Author Zesysterm
 * @Date 2020/5/4 14:22
 * @Version 1.0
 **/
public interface PermissionMapper  extends BaseMapper<Permission> {

    List<String> selectPermissionByUser(@Param("userId") Long id);
}
