package com.jxdinfo.salary.staff.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jxdinfo.hussar.core.base.controller.BaseController;
import com.jxdinfo.salary.department.model.Department;
import com.jxdinfo.salary.department.service.IDepartmentService;
import com.jxdinfo.salary.position.model.Position;
import com.jxdinfo.salary.position.service.IPositionService;
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
import com.jxdinfo.salary.staff.model.Staff;
import com.jxdinfo.salary.staff.service.IStaffService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 人员管理控制器
 *
 * @author hyc
 * @Date 2020-06-16 15:42:27
 */
@Controller
@RequestMapping("/staff")
public class StaffController extends BaseController {

    private String PREFIX = "/salary/staff/";

    @Autowired
    private IStaffService staffService;
    @Autowired
    private IDepartmentService departmentService;
    @Autowired
    private IPositionService positionService;

    /**
     * 跳转到人员管理首页
     */
    @RequestMapping("/view")
    @BussinessLog(key = "/staff/view", type = BussinessLogType.QUERY, value = "人员管理页面")
    @RequiresPermissions("staff:view")
    public String index() {
        return PREFIX + "staff.html";
    }

    /**
     * 跳转到添加人员管理
     */
    @RequestMapping("/staff_add")
    @BussinessLog(key = "/staff/staff_add", type = BussinessLogType.INSERT, value = "跳转到添加人员管理")
    @RequiresPermissions("staff:staff_add")
    public String staffAdd() {
        return PREFIX + "staff_add.html";
    }

    /**
     * 跳转到修改人员管理
     */
    @RequestMapping("/staff_update/{staffId}")
    @BussinessLog(key = "/staff/staff_update", type = BussinessLogType.MODIFY, value = "跳转到修改人员管理")
    @RequiresPermissions("staff:staff_update")
    public String staffUpdate(@PathVariable String staffId, Model model) {
        Staff staff = staffService.selectById(staffId);
        model.addAttribute("item",staff);
        return PREFIX + "staff_edit.html";
    }

    /**
     * 获取人员管理列表
     */
    @RequestMapping(value = "/list")
    @BussinessLog(key = "/staff/list", type = BussinessLogType.QUERY, value = "获取人员管理列表")
    @RequiresPermissions("staff:list")
    @ResponseBody
    public Object list(String condition,
                       @RequestParam(value="pageNumber", defaultValue="1")int pageNumber,
                       @RequestParam(value="pageSize", defaultValue="20") int pageSize) {
        Page<Staff> page = new Page<>(pageNumber, pageSize);
        Wrapper<Staff> ew = new EntityWrapper<>();
        Map<String, Object> result = new HashMap<>(5);
        List<Staff> list = staffService.selectPage(page, ew).getRecords();
        result.put("total", page.getTotal());
        result.put("rows", list);
//        return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);
        return result;
    }

    /**
     * 新增人员管理
     */
    @RequestMapping(value = "/add")
    @BussinessLog(key = "/staff/add", type = BussinessLogType.INSERT, value = "新增人员管理")
    @RequiresPermissions("staff:add")
    @ResponseBody
    public Object add(@RequestParam Map<String, String> staffInfo) {
        System.out.println(staffInfo);

        String staffName = staffInfo.get("staffName"); // 员工名称
        String gender = staffInfo.get("gender"); // 性别
        int departmentId = Integer.parseInt(staffInfo.get("departmentId")); // 部门ID
        int positionId = Integer.parseInt(staffInfo.get("positionId")); // 职位ID
        Long jstime = Long.parseLong(staffInfo.get("jstime")); // 入职时间戳

        // 新ID
        int staffId = staffService.selectMaxStaffIdByDid(departmentId)+1;
        // 部门
        Wrapper<Department> wrapper1 = new EntityWrapper<>();
        wrapper1
                .eq("DEPARTMENT_ID",departmentId);
        Department department = departmentService.selectOne(wrapper1);
        //职位
        Wrapper<Position> wrapper2 = new EntityWrapper<>();
        wrapper2
                .eq("POSITION_ID",positionId);
        Position position = positionService.selectOne(wrapper2);
        //入职时间
        Date date = new Date(jstime);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp entryTime = Timestamp.valueOf(sf.format(date));

        Staff staff = new Staff(staffId,staffName,gender,department,position,entryTime); // 新员工

        staffService.insert(staff); //新增员工
        return SUCCESS_TIP;
    }

    /**
     * 删除人员管理
     */
    @RequestMapping(value = "/delete")
    @BussinessLog(key = "/staff/delete", type = BussinessLogType.DELETE, value = "删除人员管理")
    @RequiresPermissions("staff:delete")
    @ResponseBody
    public Object delete(@RequestParam String staffId) {
        staffService.deleteById(staffId);
        return SUCCESS_TIP;
    }

    /**
     * 修改人员管理
     */
    @RequestMapping(value = "/update")
    @BussinessLog(key = "/staff/update", type = BussinessLogType.MODIFY, value = "修改人员管理")
    @RequiresPermissions("staff:update")
    @ResponseBody
    public Object update(Staff staff) {
        Staff staff1 = staffService.selectById(staff);
        LogObjectHolder.me().set(staff1);
        staffService.updateById(staff);
        return SUCCESS_TIP;
    }

    /**
     * 人员管理详情
     */
    @RequestMapping(value = "/detail/{staffId}")
    @BussinessLog(key = "/staff/detail", type = BussinessLogType.QUERY, value = "人员管理详情")
    @RequiresPermissions("staff:detail")
    @ResponseBody
    public Object detail(@PathVariable("staffId") String staffId) {
        return staffService.selectById(staffId);
    }
}
