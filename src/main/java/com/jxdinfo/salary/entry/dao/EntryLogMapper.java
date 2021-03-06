package com.jxdinfo.salary.entry.dao;

import com.jxdinfo.salary.departure.model.DepartureLog;
import com.jxdinfo.salary.entry.model.EntryLog;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.jxdinfo.salary.move.model.MoveLog;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.security.Timestamp;
import java.util.List;

/**
 * <p>
 * 入职记录表 Mapper 接口
 * </p>
 *
 * @author zxn
 * @since 2020-06-19
 */
public interface EntryLogMapper extends BaseMapper<EntryLog> {
    //模糊查询
    List<EntryLog> likeSelect(RowBounds var1,
                              @Param("condition1")String condition1,
                              @Param("condition2")String condition2,
                              @Param("condition3")String condition3);

    //带筛选的模糊查询
    List<EntryLog> likeSelectByCondition(RowBounds var1,
                              @Param("ew") Wrapper<EntryLog> wrapper,
                              @Param("condition1")String condition1,
                              @Param("condition2")String condition2,
                              @Param("condition3")String condition3);

    EntryLog selectMaxOperationId(String time);
    List<EntryLog> selectPage(RowBounds rowBounds, @Param("ew") Wrapper<EntryLog> wrapper);
    List<EntryLog> selectByDidPage(RowBounds rowBounds, @Param("ew") Wrapper<EntryLog> wrapper);

}
