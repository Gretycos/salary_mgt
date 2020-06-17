package com.jxdinfo.salary.PermissionManagement.dao;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.jxdinfo.salary.PermissionManagement.model.BlackList;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jxdinfo.salary.PermissionManagement.model.WhiteList;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * <p>
 * 黑名单表 Mapper 接口
 * </p>
 *
 * @author 王明凯
 * @since 2020-06-16
 */
public interface BlackListMapper extends BaseMapper<BlackList> {

    List<BlackList> selectPage(RowBounds rowBounds, @Param("ew") Wrapper<BlackList> wrapper);

}
