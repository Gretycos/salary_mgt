package com.jxdinfo.salary.move.dao;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.jxdinfo.salary.PermissionManagement.model.BlackList;
import com.jxdinfo.salary.move.model.MoveLog;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * <p>
 * 调动记录表 Mapper 接口
 * </p>
 *
 * @author zxn
 * @since 2020-06-17
 */
public interface MoveLogMapper extends BaseMapper<MoveLog> {

    List<MoveLog> selectPage(RowBounds rowBounds, @Param("ew") Wrapper<MoveLog> wrapper);


}
