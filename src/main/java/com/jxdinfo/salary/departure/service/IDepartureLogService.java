package com.jxdinfo.salary.departure.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jxdinfo.salary.departure.model.DepartureLog;
import com.baomidou.mybatisplus.service.IService;
import com.jxdinfo.salary.entry.model.EntryLog;
import com.jxdinfo.salary.staff.model.Staff;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

/**
 * <p>
 * 离职记录表 服务类
 * </p>
 *
 * @author zxn
 * @since 2020-06-19
 */
public interface IDepartureLogService extends IService<DepartureLog> {

    //模糊查询
    List<DepartureLog> likeSelect(String condition1,String condition2,String condition3);

    //添加离职记录 对外接口
    boolean addDepartureLog(Staff operator, Staff departure, Timestamp departureTime);

    Page<DepartureLog> selectByDidPage(Page<DepartureLog> page, Wrapper<DepartureLog> wrapper);

}
