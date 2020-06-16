/*
 * 金现代轻骑兵V8开发平台 
 * CacheDemo.java 
 * 版权所有：金现代信息产业股份有限公司  Copyright (c) 2018-2023 .
 * 金现代信息产业股份有限公司保留所有权利,未经允许不得以任何形式使用.
 */
package com.jxdinfo.hussar.hussardemo.controller;

import com.jxdinfo.hussar.core.base.controller.BaseController;
import com.jxdinfo.hussar.core.base.tips.Tip;
import com.jxdinfo.hussar.core.cache.HussarCacheManager;
import com.jxdinfo.hussar.core.cache.HussarEhcacheManager;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;

/**
 * 类的用途：缓存管理Demo 控制器
 * 创建日期：2018/5/30 15:32 ;
 * 修改历史：
 * 修改日期：2018/5/30 15:32 ;
 * 修改作者：LiangDong ;
 * 修改内容：
 * @author LiangDong ;
 * @version 1.0
 */
@Controller
@RequestMapping("/cacheDemo")
public class CacheDemo extends BaseController {

    /**
     *  hussar缓存 接口
     */
    @Autowired
    private HussarCacheManager hussarCacheManager;

    /**
     *  是否是单机环境
     */
    @Value("${hussar.stand-alone}")
    private boolean stand_alone;

    /**
     * 返回缓存管理Demo
     * @author      LiangDong
     * @return      java.lang.String
     * @date        2018/5/30 15:35
     */
    @RequestMapping("/view")
    @RequiresPermissions("cacheDemo:view")
    public String index() {
        if ( stand_alone ) {
            //如果是单机环境
            return "/hussardemo/ehcacheDemo.html";
        } else {
            //如果是集群环境
            return "/hussardemo/redisCacheDemo.html";
        }
    }


    /*************************单机模式：Ehcache  START**********************/
    /**
     * 返回ehcache所有的缓存名称
     * @author      LiangDong
     * @return      java.lang.Object
     * @date        2018/5/31 8:52
     */
    @RequestMapping("/cacheNames")
    @ResponseBody
    public Object getCacheNames() {
        //缓存名称集
        Collection<String> cacheNamesList = ((HussarEhcacheManager)hussarCacheManager).getCacheNames();
        return cacheNamesList;
    }

    /**
     * 缓存数据
     * @author      LiangDong
     * @return      com.jxdinfo.hussar.core.base.tips.Tip
     * @date        2018/5/31 9:04
     */
    @RequestMapping("/saveCache")
    @ResponseBody
    public Tip saveCache() {
        String cacheName = super.getPara("cacheName").trim();
        String key = super.getPara("key").trim();
        String value = super.getPara("value").trim();
        hussarCacheManager.setObject(cacheName, key, value);
        return SUCCESS_TIP;
    }

    /**
     * 根据确定的缓存名称和key删除一个缓存数据
     * @author      LiangDong
     * @return      com.jxdinfo.hussar.core.base.tips.Tip
     * @date        2018/5/31 9:11
     */
    @RequestMapping("/deleteOneCache")
    @ResponseBody
    public Tip deleteOneCache() {
        String cacheName = super.getPara("cacheName").trim();
        String key = super.getPara("key").trim();
        hussarCacheManager.delete(cacheName, key);
        return SUCCESS_TIP;
    }

    /**
     * 根据确定的缓存名称和模糊key查询删除多个缓存数据
     * @author      LiangDong
     * @return      com.jxdinfo.hussar.core.base.tips.Tip
     * @date        2018/5/31 9:16
     */
    @RequestMapping("/deleteMoreCache")
    @ResponseBody
    public Tip deleteMoreCache() {
        String cacheName = super.getPara("cacheName").trim();
        String key = super.getPara("key").trim();
        hussarCacheManager.deleteMore(cacheName, key);
        return SUCCESS_TIP;
    }
    /*************************单机模式：Ehcache  END**********************/

    /*************************集群模式：Redis  START**********************/
    /**
     * 缓存数据
     * @author      LiangDong
     * @return      com.jxdinfo.hussar.core.base.tips.Tip
     * @date        2018/5/31 9:04
     */
    @RequestMapping("/saveRedisCache")
    @ResponseBody
    public Tip saveRedisCache() {
        String key = super.getPara("key").trim();
        String value = super.getPara("value").trim();
        hussarCacheManager.setObject(null, key, value);
        return SUCCESS_TIP;
    }

    /**
     * 根据确定的缓存名称和key删除一个缓存数据
     * @author      LiangDong
     * @return      com.jxdinfo.hussar.core.base.tips.Tip
     * @date        2018/5/31 9:11
     */
    @RequestMapping("/deleteOneRedisCache")
    @ResponseBody
    public Tip deleteOneRedisCache() {
        String key = super.getPara("key").trim();
        hussarCacheManager.delete(null, key);
        return SUCCESS_TIP;
    }

    /**
     * 根据确定的缓存名称和模糊key查询删除多个缓存数据
     * @author      LiangDong
     * @return      com.jxdinfo.hussar.core.base.tips.Tip
     * @date        2018/5/31 9:16
     */
    @RequestMapping("/deleteMoreRedisCache")
    @ResponseBody
    public Tip deleteMoreRedisCache() {
        String key = super.getPara("key").trim();
        hussarCacheManager.deleteMore(null, key);
        return SUCCESS_TIP;
    }
    /*************************集群模式：Redis  END**********************/

}
