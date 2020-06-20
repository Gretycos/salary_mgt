package com.jxdinfo.salary.departure.dao;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.jxdinfo.salary.departure.model.DepartureLog;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jxdinfo.salary.entry.model.EntryLog;
import com.jxdinfo.salary.staff.model.Staff;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * <p>
 * 离职记录表 Mapper 接口
 * </p>
 *
 * @author zxn
 * @since 2020-06-19
 */
public interface DepartureLogMapper extends BaseMapper<DepartureLog> {

    //模糊查询
    List<DepartureLog> likeSelect(@Param("condition1")String condition1,@Param("condition2")String condition2,@Param("condition3")String condition3);

    //添加离职记录
    void addDepartureLogRecord(@Param("operationId")String operationId,
                               @Param("operatorId")String operatorId,
                               @Param("departureId")String departureId,
                               @Param("departureTime")String departureTime);

    List<DepartureLog> selectPage(RowBounds rowBounds, @Param("ew") Wrapper<DepartureLog> wrapper);
    List<DepartureLog> selectByDidPage(RowBounds rowBounds, @Param("ew") Wrapper<DepartureLog> wrapper);

}
