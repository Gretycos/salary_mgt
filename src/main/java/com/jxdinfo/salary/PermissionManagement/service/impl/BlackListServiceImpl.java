package com.jxdinfo.salary.PermissionManagement.service.impl;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.jxdinfo.salary.PermissionManagement.model.BlackList;
import com.jxdinfo.salary.PermissionManagement.dao.BlackListMapper;
import com.jxdinfo.salary.PermissionManagement.service.IBlackListService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 黑名单表 服务实现类
 * </p>
 *
 * @author 王明凯
 * @since 2020-06-16
 */
@Service
public class BlackListServiceImpl extends ServiceImpl<BlackListMapper, BlackList> implements IBlackListService {

    @Autowired
    private BlackListMapper blackListMapper;


    @Override
    public List<Integer> selectStaffId() {
        return blackListMapper.selectStaffId();
    }

    @Override
    public List<String> selectStaffName() {
        return blackListMapper.selectStaffName();
    }

    @Override
    public List<String> selectDepartmentName(@Param("ew") Wrapper<BlackList> var1) {
        return blackListMapper.selectDepartmentName(var1);
    }

    @Override
    public List<String> selectPermissionName(@Param("ew") Wrapper<BlackList> var1) {
        return blackListMapper.selectPermissionName(var1);
    }

    @Override
    public Boolean deleteBatchByIds(List<BlackList> list) {
        return blackListMapper.deleteBatchByIds(list);
    }
}
