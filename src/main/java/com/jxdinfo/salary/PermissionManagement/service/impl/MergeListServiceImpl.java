package com.jxdinfo.salary.PermissionManagement.service.impl;

import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jxdinfo.salary.PermissionManagement.dao.MergeListMapper;
import com.jxdinfo.salary.PermissionManagement.model.MergeList;
import com.jxdinfo.salary.PermissionManagement.service.IMergeListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Date: 2020/6/24 12:07
 * @Author: wmk
 * @Description:
 */
@Service
public class MergeListServiceImpl implements IMergeListService {

    @Autowired
    private MergeListMapper mergeListMapper;
    @Override
    public Page<MergeList> selectPageWhite(Page<MergeList> page, Wrapper<MergeList> wrapper) {
        wrapper = (Wrapper<MergeList>)SqlHelper.fillWrapper(page, wrapper);
        return page.setRecords(mergeListMapper.selectPageWhite(page,wrapper));
    }

    @Override
    public Page<MergeList> selectPageBlack(Page<MergeList> page, Wrapper<MergeList> wrapper) {
        wrapper = (Wrapper<MergeList>)SqlHelper.fillWrapper(page, wrapper);
        return page.setRecords(mergeListMapper.selectPageBlack(page,wrapper));
    }
}
