package com.jxdinfo.salary.PermissionManagement.service.impl;

import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jxdinfo.salary.PermissionManagement.model.WhiteList;
import com.jxdinfo.salary.PermissionManagement.dao.WhiteListMapper;
import com.jxdinfo.salary.PermissionManagement.service.IWhiteListService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 白名单表 服务实现类
 * </p>
 *
 * @author 王明凯
 * @since 2020-06-16
 */
@Service
public class WhiteListServiceImpl extends ServiceImpl<WhiteListMapper, WhiteList> implements IWhiteListService {

    @Autowired
    private WhiteListMapper whiteListMapper;

    @Override
    public List<Integer> selectStaffId() {
        return whiteListMapper.selectStaffId();
    }

    @Override
    public List<String> selectStaffName() {
        return whiteListMapper.selectStaffName();
    }

    @Override
    public List<String> selectDepartmentName() {
        return whiteListMapper.selectDepartmentName();
    }

    @Override
    public List<String> selectPermissionName() {
        return whiteListMapper.selectPermissionName();
    }

    @Override
    public List<WhiteList> searchByCondition(String condition) {
        return whiteListMapper.searchByCondition(condition);
    }

//    @Override
//    public Page<WhiteList> selectPage(String condition, Page<WhiteList> page, Wrapper<WhiteList> wrapper) {
//        wrapper = SqlHelper.fillWrapper(page, wrapper);
//        page.setRecords(WhiteListMapper.selectPage(condition,page, wrapper));
//        return page;
//    }


}
