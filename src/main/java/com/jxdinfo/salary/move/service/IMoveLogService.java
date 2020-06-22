package com.jxdinfo.salary.move.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jxdinfo.hussar.bsp.permit.model.SysUsers;
import com.jxdinfo.salary.PermissionManagement.model.WhiteList;
import com.jxdinfo.salary.entry.model.EntryLog;
import com.jxdinfo.salary.move.model.MoveLog;
import com.baomidou.mybatisplus.service.IService;
import com.jxdinfo.salary.staff.model.Staff;
import com.jxdinfo.salary.department.model.Department;
import com.jxdinfo.salary.position.model.Position;

import java.sql.Timestamp;
import java.util.List;

/**
 * <p>
 * 调动记录表 服务类
 * </p>
 *
 * @author zxn
 * @since 2020-06-17
 */
public interface IMoveLogService extends IService<MoveLog> {

    //获取操作编码
    List<Integer> selectOperationId();

    //获取操作员工号
    List<Integer> selectOperatorId();

    // 获取操作员姓名
    List<String> selectOperatorName();

    //获取调动员工工号
    List<Integer> selectMoveId();

    // 获取调动员工姓名
    List<String> selectMoveName();

    // 获取原部门名称
    List<String> selectOldDepartmentName();

    // 获取原职位名称
    List<String> selectOldPositionName();

    // 获取新部门名称
    List<String> selectNewDepartmentName();

    // 获取新职位名称
    List<String> selectNewPositionName();

    //获取操作时间
    List<String> selectOperationTime();


    //模糊查询
    List<MoveLog> likeSelect(Page<MoveLog> page,String condition1, String condition2, String condition3);

    //带筛选的模糊查询
    List<MoveLog> likeSelectByCondition(Page<MoveLog> page, Wrapper<MoveLog> wrapper, String condition1, String condition2, String condition3);

    //特定部门模糊查询
    List<MoveLog> likeSelectD(Page<MoveLog> page,String condition1, String condition2, String condition3, Wrapper<MoveLog> wrapper);

    //添加调动记录 对外接口
    boolean addMoveLog(Staff operator, Staff move, Department oldD, Department newD, Position oldP, Position newP, Timestamp moveTime);

    Page<MoveLog> selectByDidPage(Page<MoveLog> page, Wrapper<MoveLog> wrapper);
}
