/*
 * 金现代轻骑兵V8开发平台
 * DocviewDemo.java
 * 版权所有：金现代信息产业股份有限公司  Copyright (c) 2018-2023 .
 * 金现代信息产业股份有限公司保留所有权利,未经允许不得以任何形式使用.
 */
package com.jxdinfo.hussar.hussardemo.controller;

import com.jxdinfo.hussar.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 文档预览demo
 * </p>
 *
 * @author LiangDong
 * @version 1.0
 * date：2019-03-14 15:41
 */
@Controller
@RequestMapping("/doc")
public class DocviewDemo extends BaseController {


    /**
     * <p> 跳转到文档预览demo页面
     * @since 2019-03-14 15:52
     * @author LiangDong
     * @return java.lang.String
     **/
    @RequestMapping("/view")
    public String index(){
        return "/hussardemo/docviewDemo.html";
    }


    /**
     * <p> 文档分页预览
     * @since 2019-03-14 16:00
     * @author LiangDong
     * @return java.util.Map
     **/
    @GetMapping("/img")
    @ResponseBody
    public Map showSvg(){
        //文档总页数
        int total = 10;

        //当前请求页码
        int page = Integer.valueOf(super.getPara("page"));
        //页面大小
        int size = Integer.valueOf(super.getPara("size"));

        //当页的数据
        List<String> data = new ArrayList<String>();

        int limit = page*size>total ? total : page*size;
        for(int index = (page-1)*size+1; index<=limit; index++){
            data.add("/static/svg/redis" +index+".svg");
        }


        Map<String,Object> result = new HashMap<String, Object>();
        //文档总页数
        result.put("total",total);
        result.put("data",data);

        return result;
    }


}
