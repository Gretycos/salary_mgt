package com.jxdinfo.salary.PermissionManagement.dao;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jxdinfo.salary.PermissionManagement.model.WhiteList;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * <p>
 * 白名单表 Mapper 接口
 * </p>
 *
 * @author 王明凯
 * @since 2020-06-16
 */
public interface WhiteListMapper extends BaseMapper<WhiteList> {

    List<WhiteList> selectPage(RowBounds rowBounds, @Param("ew") Wrapper<WhiteList> wrapper);

}
