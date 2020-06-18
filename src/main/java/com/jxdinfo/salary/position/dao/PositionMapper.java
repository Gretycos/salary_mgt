package com.jxdinfo.salary.position.dao;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.jxdinfo.salary.position.model.Position;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;


/**
 * <p>
 * 职位表 Mapper 接口
 * </p>
 *
 * @author hyc
 * @since 2020-06-17
 */
public interface PositionMapper extends BaseMapper<Position> {
    List<Position> selectDetailsPage(RowBounds var1, @Param("ew") Wrapper<Position> var2);
}
