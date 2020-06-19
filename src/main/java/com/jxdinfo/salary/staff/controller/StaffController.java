package com.jxdinfo.salary.staff.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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
        int staffId = staffService.getNewStaffId(departmentId);
        // 部门
        Department department = departmentService.selectOne(new EntityWrapper<Department>()
                .eq("DEPARTMENT_ID",departmentId));
        //职位
        Position position = positionService.selectOne(new EntityWrapper<Position>()
                .eq("POSITION_ID",positionId));
        //入职时间
        Timestamp entryTime = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(new Date(jstime)));

        Staff manager = staffService.selectOne(new EntityWrapper<Staff>()
                .eq("POSITION_ID",1).eq("DEPARTMENT_ID",department.getDepartmentId()));

        if (manager != null // 入职去的部门存在经理
                && position.getPositionId() == 1 // 入职后的职位是经理
                && manager.getDepartureTime() == null // 经理没有离职
        ){
            Map<String,Object> res= new HashMap<>();
            res.put("code",500);
            res.put("message","已存在部门经理");
            return res;
        }

        Staff superAdmin = staffService.selectOne(new EntityWrapper<Staff>()
                .eq("POSITION_ID",2));

        if(superAdmin != null // 存在总经理
                && superAdmin.getDepartureTime() == null //未离职
                && position.getPositionId() == 2 // 入职后是总经理
        ){
            Map<String,Object> res= new HashMap<>();
            res.put("code",500);
            res.put("message","已存在总经理");
            return res;
        }

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
    public Object delete(@RequestParam Map<String, String> info) {
        Long jstime = Long.parseLong(info.get("jstime"));
        List<Map<String,Integer>> list = JSONArray.parseObject(info.get("staff"),List.class);
        for (Map<String,Integer> m : list){
            int staffId = m.get("staffId");
            Staff staff = staffService.selectOne(new EntityWrapper<Staff>()
                    .eq("STAFF_ID",staffId));
            System.out.println(staff);
            Timestamp departureTime = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .format(new Date(jstime)));
            staff.setDepartureTime(departureTime);
            staffService.updateById(staff);
        }

        return SUCCESS_TIP;
    }

    /**
     * 修改人员管理
     */
    @RequestMapping(value = "/update")
    @BussinessLog(key = "/staff/update", type = BussinessLogType.MODIFY, value = "修改人员管理")
    @RequiresPermissions("staff:update")
    @ResponseBody
    public Object update(@RequestParam Map<String, String> staffInfo) {
        System.out.println(staffInfo);
        int staffId = Integer.parseInt(staffInfo.get("staffId")); //员工ID
        String staffName = staffInfo.get("staffName"); // 员工名称
        String gender = staffInfo.get("gender"); // 性别
        int departmentId = Integer.parseInt(staffInfo.get("departmentId")); // 部门ID
        int positionId = Integer.parseInt(staffInfo.get("positionId")); // 职位ID
        Long jstime = Long.parseLong(staffInfo.get("jstime")); // 变动时间

        Staff staff = staffService.selectOne(new EntityWrapper<Staff>()
                .eq("STAFF_ID",staffId));

        Department newDepartment = departmentService.selectOne(new EntityWrapper<Department>()
                .eq("DEPARTMENT_ID",departmentId));

        Position newPosition = positionService.selectOne(new EntityWrapper<Position>()
                .eq("POSITION_ID",positionId));

        Staff manager = staffService.selectOne(new EntityWrapper<Staff>()
                .eq("POSITION_ID",1).eq("DEPARTMENT_ID",newDepartment.getDepartmentId()));

        if (manager != null // 变动去的部门存在经理
                && newPosition.getPositionId() == 1 // 变动后的职位是经理
                && manager.getDepartureTime() == null // 经理没有离职
                && !(manager.getStaffId().equals(staff.getStaffId())) // 不是同一个人，则存在多经理，返回错误
        ){
            Map<String,Object> res= new HashMap<>();
            res.put("code",500);
            res.put("message","已存在部门经理");
            return res;
        }

        Staff superAdmin = staffService.selectOne(new EntityWrapper<Staff>()
                .eq("POSITION_ID",2));

        if(superAdmin != null // 存在总经理
                && superAdmin.getDepartureTime() == null //未离职
                && newPosition.getPositionId() == 2 // 变动后是总经理
        ){
            Map<String,Object> res= new HashMap<>();
            res.put("code",500);
            res.put("message","已存在总经理");
            return res;
        }

        staff.setStaffName(staffName);
        staff.setGender(gender);
        staff.setDepartment(newDepartment);
        staff.setPosition(newPosition);
        staffService.updateById(staff);

        //变动时间
        Timestamp operationTime = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(new Date(jstime)));

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
