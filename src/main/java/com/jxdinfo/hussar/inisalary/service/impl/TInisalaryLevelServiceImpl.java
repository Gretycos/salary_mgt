package com.jxdinfo.hussar.inisalary.service.impl;

import com.jxdinfo.hussar.inisalary.model.TInisalaryLevel;
import com.jxdinfo.hussar.inisalary.dao.TInisalaryLevelMapper;
import com.jxdinfo.hussar.inisalary.service.ITInisalaryLevelService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yxx
 * @since 2020-06-16
 */
@Service
public class TInisalaryLevelServiceImpl extends ServiceImpl<TInisalaryLevelMapper, TInisalaryLevel> implements ITInisalaryLevelService {


    @Override
    public TInisalaryLevel selectByTwo(String dep, String lev) {
        Map<String, Object> columnMap = new HashMap<String, Object>();
        columnMap.put("DEPARTMENT_ID",dep);
        columnMap.put("LEVEL",lev);
        List<TInisalaryLevel> TInisalaryLevelList=selectByMap(columnMap);
        TInisalaryLevel tInisalaryLevel = null;
        for (TInisalaryLevel t : TInisalaryLevelList) {
            tInisalaryLevel =t;
        }
        return tInisalaryLevel;
    }

    @Override
    public int getInisalary(String dep, String lev) {
        TInisalaryLevel t=selectByTwo(dep,lev);
        return t.getInitialSalary();
    }
}
