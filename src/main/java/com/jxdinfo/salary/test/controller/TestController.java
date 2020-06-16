package com.jxdinfo.salary.test.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jxdinfo.hussar.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.jxdinfo.hussar.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.jxdinfo.hussar.common.annotion.BussinessLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.jxdinfo.hussar.core.log.type.BussinessLogType;
import com.jxdinfo.salary.test.model.Test;
import com.jxdinfo.salary.test.service.ITestService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试控制器
 *
 * @author zwl
 * @Date 2020-06-10 16:45:41
 */
@Controller
@RequestMapping("/test")
public class TestController extends BaseController {

    private String PREFIX = "/salary/test/";

    @Autowired
    private ITestService testService;

    /**
     * 跳转到测试首页
     */
    @RequestMapping("/view")
    @BussinessLog(key = "/test/view", type = BussinessLogType.QUERY, value = "测试页面")
    @RequiresPermissions("test:view")
    public String index() {
        return PREFIX + "test.html";
    }

    /**
     * 跳转到添加测试
     */
    @RequestMapping("/test_add")
    @BussinessLog(key = "/test/test_add", type = BussinessLogType.INSERT, value = "跳转到添加测试")
    @RequiresPermissions("test:test_add")
    public String testAdd() {
        return PREFIX + "test_add.html";
    }

    /**
     * 跳转到修改测试
     */
    @RequestMapping("/test_update/{testId}")
    @BussinessLog(key = "/test/test_update", type = BussinessLogType.MODIFY, value = "跳转到修改测试")
    @RequiresPermissions("test:test_update")
    public String testUpdate(@PathVariable String testId, Model model) {
        Test test = testService.selectById(testId);
        model.addAttribute("item",test);
        return PREFIX + "test_edit.html";
    }

    /**
     * 获取测试列表
     */
    @RequestMapping(value = "/list")
    @BussinessLog(key = "/test/list", type = BussinessLogType.QUERY, value = "获取测试列表")
    @RequiresPermissions("test:list")
    @ResponseBody
    public Object list(String condition,
                       @RequestParam(value="pageNumber", defaultValue="1")int pageNumber,
                       @RequestParam(value="pageSize", defaultValue="20") int pageSize) {
        Page<Test> page = new Page<>(pageNumber, pageSize);
        Wrapper<Test> ew = new EntityWrapper<>();
        String name  = super.getPara("name");
        ew.like("NAME",name);
        Map<String, Object> result = new HashMap<>(5);
        List<Test> list = testService.selectPage(page, ew).getRecords();
        result.put("total", page.getTotal());
        result.put("rows", list);
        return result;
    }

    /**
     * 新增测试
     */
    @RequestMapping(value = "/add")
    @BussinessLog(key = "/test/add", type = BussinessLogType.INSERT, value = "新增测试")
    @RequiresPermissions("test:add")
    @ResponseBody
    public Object add(Test test) {
        testService.insert(test);
        return SUCCESS_TIP;
    }

    /**
     * 删除测试
     */
    @RequestMapping(value = "/delete")
    @BussinessLog(key = "/test/delete", type = BussinessLogType.DELETE, value = "删除测试")
    @RequiresPermissions("test:delete")
    @ResponseBody
    public Object delete(@RequestParam String testId) {
        List idList = Arrays.asList(testId.split(","));
        testService.deleteBatchIds(idList);
        return SUCCESS_TIP;
    }

    /**
     * 修改测试
     */
    @RequestMapping(value = "/update")
    @BussinessLog(key = "/test/update", type = BussinessLogType.MODIFY, value = "修改测试")
    @RequiresPermissions("test:update")
    @ResponseBody
    public Object update(Test test) {
        Test test1 = testService.selectById(test);
        LogObjectHolder.me().set(test1);
        testService.updateById(test);
        return SUCCESS_TIP;
    }

    /**
     * 测试详情
     */
    @RequestMapping(value = "/detail/{testId}")
    @BussinessLog(key = "/test/detail", type = BussinessLogType.QUERY, value = "测试详情")
    @RequiresPermissions("test:detail")
    @ResponseBody
    public Object detail(@PathVariable("testId") String testId) {
        return testService.selectById(testId);
    }
}
