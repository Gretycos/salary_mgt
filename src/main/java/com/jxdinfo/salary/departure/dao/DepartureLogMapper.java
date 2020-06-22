package com.jxdinfo.salary.departure.dao;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
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
    List<DepartureLog> likeSelect(RowBounds var1,
                                  @Param("condition1")String condition1,
                                  @Param("condition2")String condition2,
                                  @Param("condition3")String condition3);

    //带筛选的模糊查询
    List<DepartureLog> likeSelectByCondition(RowBounds var1,
                                         @Param("ew") Wrapper<DepartureLog> wrapper,
                                         @Param("condition1")String condition1,
                                         @Param("condition2")String condition2,
                                         @Param("condition3")String condition3);


    //内置方法，不需要显示地写出来
    //Integer insert(T var1);

    DepartureLog selectMaxOperationId(String time);
    List<DepartureLog> selectPage(RowBounds rowBounds, @Param("ew") Wrapper<DepartureLog> wrapper);
    List<DepartureLog> selectByDidPage(RowBounds rowBounds, @Param("ew") Wrapper<DepartureLog> wrapper);

}
