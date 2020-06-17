package com.jxdinfo.salary.department.controller;

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
import com.jxdinfo.salary.department.model.Department;
import com.jxdinfo.salary.department.service.IDepartmentService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 部门控制器
 *
 * @author hyc
 * @Date 2020-06-17 10:20:34
 */
@Controller
@RequestMapping("/department")
public class DepartmentController extends BaseController {

    private String PREFIX = "/department/department/";

    @Autowired
    private IDepartmentService departmentService;

    /**
     * 跳转到部门首页
     */
    @RequestMapping("/view")
    @BussinessLog(key = "/department/view", type = BussinessLogType.QUERY, value = "部门页面")
    @RequiresPermissions("department:view")
    public String index() {
        return PREFIX + "department.html";
    }

    /**
     * 跳转到添加部门
     */
    @RequestMapping("/department_add")
    @BussinessLog(key = "/department/department_add", type = BussinessLogType.INSERT, value = "跳转到添加部门")
    @RequiresPermissions("department:department_add")
    public String departmentAdd() {
        return PREFIX + "department_add.html";
    }

    /**
     * 跳转到修改部门
     */
    @RequestMapping("/department_update/{departmentId}")
    @BussinessLog(key = "/department/department_update", type = BussinessLogType.MODIFY, value = "跳转到修改部门")
    @RequiresPermissions("department:department_update")
    public String departmentUpdate(@PathVariable String departmentId, Model model) {
        Department department = departmentService.selectById(departmentId);
        model.addAttribute("item",department);
        return PREFIX + "department_edit.html";
    }

    /**
     * 获取部门列表
     */
    @RequestMapping(value = "/list")
    @BussinessLog(key = "/department/list", type = BussinessLogType.QUERY, value = "获取部门列表")
    @RequiresPermissions("department:list")
    @ResponseBody
    public Object list(String condition,
                       @RequestParam(value="pageNumber", defaultValue="1")int pageNumber,
                       @RequestParam(value="pageSize", defaultValue="20") int pageSize) {
        Page<Department> page = new Page<>(pageNumber, pageSize);
        Wrapper<Department> ew = new EntityWrapper<>();
        Map<String, Object> result = new HashMap<>(5);
        List<Department> list = departmentService.selectPage(page, ew).getRecords();
        result.put("total", page.getTotal());
        result.put("rows", list);
        return result;
    }

    /**
     * 新增部门
     */
    @RequestMapping(value = "/add")
    @BussinessLog(key = "/department/add", type = BussinessLogType.INSERT, value = "新增部门")
    @RequiresPermissions("department:add")
    @ResponseBody
    public Object add(Department department) {
        departmentService.insert(department);
        return SUCCESS_TIP;
    }

    /**
     * 删除部门
     */
    @RequestMapping(value = "/delete")
    @BussinessLog(key = "/department/delete", type = BussinessLogType.DELETE, value = "删除部门")
    @RequiresPermissions("department:delete")
    @ResponseBody
    public Object delete(@RequestParam String departmentId) {
        departmentService.deleteById(departmentId);
        return SUCCESS_TIP;
    }

    /**
     * 修改部门
     */
    @RequestMapping(value = "/update")
    @BussinessLog(key = "/department/update", type = BussinessLogType.MODIFY, value = "修改部门")
    @RequiresPermissions("department:update")
    @ResponseBody
    public Object update(Department department) {
        Department department1 = departmentService.selectById(department);
        LogObjectHolder.me().set(department1);
        departmentService.updateById(department);
        return SUCCESS_TIP;
    }

    /**
     * 部门详情
     */
    @RequestMapping(value = "/detail/{departmentId}")
    @BussinessLog(key = "/department/detail", type = BussinessLogType.QUERY, value = "部门详情")
    @RequiresPermissions("department:detail")
    @ResponseBody
    public Object detail(@PathVariable("departmentId") String departmentId) {
        return departmentService.selectById(departmentId);
    }
}
