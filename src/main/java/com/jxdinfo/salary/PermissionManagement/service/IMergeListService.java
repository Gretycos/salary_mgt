package com.jxdinfo.salary.PermissionManagement.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jxdinfo.salary.PermissionManagement.model.MergeList;

import java.util.List;

/**
 * @Date: 2020/6/24 12:05
 * @Author: wmk
 * @Description:
 */
public interface IMergeListService {
    Page<MergeList> selectPageWhite(Page<MergeList> var1, Wrapper<MergeList> var2);

    Page<MergeList> selectPageBlack(Page<MergeList> var1, Wrapper<MergeList> var2);

}
