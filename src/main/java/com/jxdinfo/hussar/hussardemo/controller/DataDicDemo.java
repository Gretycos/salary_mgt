/*
 * 金现代轻骑兵V8开发平台 
 * DataDicDemo.java 
 * 版权所有：金现代信息产业股份有限公司  Copyright (c) 2018-2023 .
 * 金现代信息产业股份有限公司保留所有权利,未经允许不得以任何形式使用.
 */
package com.jxdinfo.hussar.hussardemo.controller;

import com.jxdinfo.hussar.common.dicutil.DictionaryUtil;
import com.jxdinfo.hussar.core.base.controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 类的用途：数据字典Demo 控制器
 * 创建日期：2018/5/28 9:50 ;
 * 修改历史：
 * 修改日期：2018/5/28 9:50 ;
 * 修改作者：LiangDong ;
 * 修改内容：
 * @author LiangDong ;
 * @version 1.0
 */
@Controller
@RequestMapping("/dataDicDemo")
public class DataDicDemo extends BaseController {

    /**
     * 字典工具接口
     */
    @Resource
    private DictionaryUtil dictionaryUtil;

    /**
     * 返回数据字典Demo
     * @author      LiangDong
     * @return      java.lang.String
     * @date        2018/5/28 10:13
     */
    @RequestMapping("/view")
    @RequiresPermissions("dataDicDemo:view")
    public String index() {
        return "/hussardemo/dataDicDemo.html";
    }

    /**
     * 根据字典类型获取字典数据List
     * @author      LiangDong
     * @return      java.lang.Object
     * @date        2018/5/28 14:19
     */
    @RequestMapping("/listData")
    @ResponseBody
    public Object getListData() {
        String dicType = super.getPara("dicType").trim();
        List<Map<String,Object>> list = this.dictionaryUtil.getDictListByType(dicType);
        return list;
    }

    /**
     * 根据字典类型获取字典数据Map
     * @author      LiangDong
     * @return      java.lang.Object
     * @date        2018/5/28 14:19
     */
    @RequestMapping("/mapData")
    @ResponseBody
    public Object getMapData() {
        String dicType = super.getPara("dicType").trim();
        LinkedHashMap<String, Object> map = this.dictionaryUtil.getDictMapByType(dicType);
        return map;
    }

    /**
     * 根据字典类型和选项值数组获取指定的选项
     * @author      LiangDong
     * @return      java.lang.Object
     * @date        2018/6/6 15:01
     */
    @RequestMapping("/certainData")
    @ResponseBody
    public Object getCertainData() {
        String dicType = super.getPara("dicType").trim();
        String[] optionValues = super.getPara("optionValues").trim().split("-");
        List<Map<String,Object>> list = this.dictionaryUtil.getCertainDictOptions(dicType, optionValues);
        return list;
    }

    /**
     * 根据字典类型和单个选项值获取选项标签
     * @author      LiangDong
     * @return      java.lang.Object
     * @date        2018/6/12 11:02
     */
    @RequestMapping("/getLabel")
    @ResponseBody
    public Object getLabel() {
        String dicType = super.getPara("dicType").trim();
        String value = super.getPara("value").trim();
        String label = dictionaryUtil.getDictLabel(dicType, value);
        return label;
    }

    /**
     * 根据字典类型和选项标签获取选项值
     * @author      LiangDong
     * @return      java.lang.Object
     * @date        2018/6/12 11:15
     */
    @RequestMapping("/getValue")
    @ResponseBody
    public Object getValue() {
        String dicType = super.getPara("dicType").trim();
        String label = super.getPara("label").trim();
        String value = dictionaryUtil.getDictValue(dicType, label);
        return value;
    }

}
