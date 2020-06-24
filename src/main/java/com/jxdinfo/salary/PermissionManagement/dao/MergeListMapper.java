package com.jxdinfo.salary.PermissionManagement.dao;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.jxdinfo.salary.PermissionManagement.model.MergeList;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Date: 2020/6/24 10:49
 * @Author: wmk
 * @Description:
 */
@Component
public interface MergeListMapper {
    List<MergeList> selectPageWhite(RowBounds var1, @Param("ew") Wrapper<MergeList> var2);

    List<MergeList> selectPageBlack(RowBounds var1, @Param("ew") Wrapper<MergeList> var2);

}
