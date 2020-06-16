/*
 * 金现代轻骑兵V8开发平台
 * QueryTableDemo.java
 * 版权所有：金现代信息产业股份有限公司  Copyright (c) 2018-2023 .
 * 金现代信息产业股份有限公司保留所有权利,未经允许不得以任何形式使用.
 */
package com.jxdinfo.hussar.hussardemo.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jxdinfo.hussar.core.base.controller.BaseController;
import com.jxdinfo.hussar.ureport.dao.UreportFileMapper;
import com.jxdinfo.hussar.ureport.model.UreportFileEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类的用途：报表控制器<p>
 * 创建日期：2019年10月10日 <br>
 *
 * @author qiuyuanlong
 * @version 1.0
 */
@Controller
@RequestMapping("/ureportDemo")
public class UReportDemo extends BaseController {

    @Autowired
    private UreportFileMapper ureportFileMapper;

    /**
     * 跳转到报表首页
     */
    @RequestMapping("/view")
    public String index() {
        return "/hussardemo/ureportDemo.html";
    }

    ;

    /**
     * 获取报表列表
     */
    @RequestMapping("/list")
    @ResponseBody
    public Object list(String condition,
                       @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                       @RequestParam(value = "pageSize", defaultValue = "20") int pageSize) {
        Page<UreportFileEntity> page = new Page<>(pageNumber, pageSize);
        Wrapper<UreportFileEntity> ew = new EntityWrapper<>();
        Map<String, Object> result = new HashMap<>(5);
        page.setRecords(ureportFileMapper.getList(page));
        List<UreportFileEntity> list = page.getRecords();
        result.put("total", page.getTotal());
        result.put("rows", list);
        return result;
    }

}

