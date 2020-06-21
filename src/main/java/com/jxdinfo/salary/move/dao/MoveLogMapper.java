package com.jxdinfo.salary.move.dao;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.jxdinfo.salary.PermissionManagement.model.BlackList;
import com.jxdinfo.salary.PermissionManagement.model.WhiteList;
import com.jxdinfo.salary.departure.model.DepartureLog;
import com.jxdinfo.salary.entry.model.EntryLog;
import com.jxdinfo.salary.move.model.MoveLog;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.security.Timestamp;
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
    List<MoveLog> likeSelect(@Param("condition1")String condition1,
                              @Param("condition2")String condition2,
                              @Param("condition3")String condition3);

    MoveLog selectMaxOperationId(String time);

    List<MoveLog> selectPage(RowBounds rowBounds, @Param("ew") Wrapper<MoveLog> wrapper);
    List<MoveLog> selectByDidPage(RowBounds rowBounds, @Param("ew") Wrapper<MoveLog> wrapper);

}
