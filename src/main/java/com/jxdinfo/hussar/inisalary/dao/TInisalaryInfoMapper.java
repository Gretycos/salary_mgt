package com.jxdinfo.hussar.inisalary.dao;

import com.jxdinfo.hussar.inisalary.model.TInisalaryInfo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
/**
 * <p>
 * 初始工资信息 Mapper 接口
 * </p>
 *
 * @author yxx
 * @since 2020-06-16
 */

public interface TInisalaryInfoMapper extends BaseMapper<TInisalaryInfo> {
    void updateDeId(Integer staffId,String staffNane,Integer departmentId);
}
