package com.jxdinfo.salary.move.service.impl;

import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jxdinfo.salary.department.model.Department;
import com.jxdinfo.salary.departure.model.DepartureLog;
import com.jxdinfo.salary.entry.model.EntryLog;
import com.jxdinfo.salary.move.model.MoveLog;
import com.jxdinfo.salary.move.dao.MoveLogMapper;
import com.jxdinfo.salary.move.service.IMoveLogService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jxdinfo.salary.position.model.Position;
import com.jxdinfo.salary.staff.model.Staff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * <p>
 * 调动记录表 服务实现类
 * </p>
 *
 * @author zxn
 * @since 2020-06-17
 */
@Service
public class MoveLogServiceImpl extends ServiceImpl<MoveLogMapper, MoveLog> implements IMoveLogService {

    @Autowired
    MoveLogMapper moveLogMapper;

    @Override
    public List<Integer> selectOperationId(){return moveLogMapper.selectOperationId();}

    @Override
    public List<Integer> selectOperatorId(){return moveLogMapper.selectOperatorId();}

    @Override
    public List<String> selectOperatorName(){return moveLogMapper.selectOperatorName();}

    @Override
    public List<Integer> selectMoveId(){return moveLogMapper.selectMoveId();}

    @Override
    public List<String> selectMoveName(){return moveLogMapper.selectMoveName();}

    @Override
    public List<String> selectOldDepartmentName(){return moveLogMapper.selectOldDepartmentName();}

    @Override
    public List<String> selectOldPositionName(){return moveLogMapper.selectOldPositionName();}

    @Override
    public List<String> selectNewDepartmentName(){return moveLogMapper.selectNewDepartmentName();}

    @Override
    public List<String> selectNewPositionName(){return moveLogMapper.selectNewPositionName();}

    @Override
    public List<String> selectOperationTime(){return moveLogMapper.selectOperationTime();}


    //模糊查询
    @Override
    public List<MoveLog> likeSelect(Page<MoveLog> page,String condition1, String condition2, String condition3) {
        return moveLogMapper.likeSelect(page,condition1, condition2, condition3);
    }

    //带筛选的模糊查询
    @Override
    public List<MoveLog> likeSelectByCondition(Page<MoveLog> page, Wrapper<MoveLog> wrapper, String condition1, String condition2, String condition3){
        return moveLogMapper.likeSelectByCondition(page,wrapper,condition1,condition2,condition3);
    }

    //特定部门模糊查询
    @Override
    public List<MoveLog> likeSelectD(Page<MoveLog> page,String condition1, String condition2, String condition3, Wrapper<MoveLog> wrapper){
        wrapper = (Wrapper<MoveLog>) SqlHelper.fillWrapper(page, wrapper);
        return moveLogMapper.likeSelectD(page, condition1, condition2, condition3,wrapper);

    }

    //添加调动记录
    @Override
    public boolean addMoveLog(Staff operator, Staff move, Department oldD, Department newD,
                              Position oldP, Position newP, Timestamp moveTime){
        MoveLog moveLogWithMaxId = moveLogMapper.selectMaxOperationId(
                new SimpleDateFormat("yyyyMMdd").format(moveTime)); //传入日期匹配出最大Id的记录
        MoveLog moveLog; // 新记录
        if(moveLogWithMaxId!=null){ // 如果有最大记录
            long operationId = Long.parseLong(moveLogWithMaxId.getOperationId());
            String newOperationId = String.valueOf(operationId+1); //最大Id+1
            moveLog = new MoveLog(newOperationId,operator,move,oldD,newD,oldP,newP,moveTime);
        }else{ // 如果没有最大记录
            moveLog = new MoveLog(operator,move,oldD,newD,oldP,newP,moveTime); //生成当前时间的第一个Id
        }

        return insert(moveLog); //调用内置的插入
    }

    @Override
    public Page<MoveLog> selectByDidPage(Page<MoveLog> page, Wrapper<MoveLog> wrapper) {
        wrapper = (Wrapper<MoveLog>) SqlHelper.fillWrapper(page, wrapper);
        page.setRecords(moveLogMapper.selectByDidPage(page, wrapper));
        return page;
    }
}
