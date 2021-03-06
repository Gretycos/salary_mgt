package com.jxdinfo.salary.staff.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jxdinfo.hussar.core.base.controller.BaseController;
import com.jxdinfo.hussar.core.shiro.ShiroKit;
import com.jxdinfo.hussar.core.shiro.ShiroUser;
import com.jxdinfo.hussar.inisalary.service.ITInisalaryInfoService;
import com.jxdinfo.salary.PermissionManagement.model.Util;
import com.jxdinfo.salary.PermissionManagement.service.IUtilService;
import com.jxdinfo.salary.bonus.service.IBonusService;
import com.jxdinfo.salary.department.model.Department;
import com.jxdinfo.salary.department.service.IDepartmentService;
import com.jxdinfo.salary.departure.service.IDepartureLogService;
import com.jxdinfo.salary.entry.service.IEntryLogService;
import com.jxdinfo.salary.move.service.IMoveLogService;
import com.jxdinfo.salary.position.model.Position;
import com.jxdinfo.salary.position.service.IPositionService;
import com.jxdinfo.salary.salaryList.service.ITMonthlySalaryService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import com.jxdinfo.hussar.common.annotion.BussinessLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.jxdinfo.hussar.core.log.type.BussinessLogType;
import com.jxdinfo.salary.staff.model.Staff;
import com.jxdinfo.salary.staff.service.IStaffService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

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
    @Autowired
    private IDepartureLogService departureLogService;
    @Autowired
    private IMoveLogService moveLogService;
    @Autowired
    private IEntryLogService entryLogService;
    @Autowired
    private IUtilService utilService;
    @Autowired
    private ITInisalaryInfoService inisalaryInfoService;
    @Autowired
    private ITMonthlySalaryService monthlySalaryService;
    @Autowired
    private IBonusService bonusService;
    //当前用户
    private Staff currentUser;
    //当前用户权限
    private List<Util> permissionList;

    /**
     * 跳转到员工管理首页
     */
    @RequestMapping("/view")
    @BussinessLog(key = "/staff/view", type = BussinessLogType.QUERY, value = "跳转到员工管理首页")
    @RequiresPermissions("staff:view")
    public String index() {
        currentUser = staffService.selectById(getCurrentAccountId()); //获取当前登录用户
        permissionList = utilService.selectList((long)currentUser.getStaffId()); //获取当前登录用户的权限
        return PREFIX + "staff.html";
    }

    /**
     * 跳转到入职页面
     */
    @RequestMapping("/staff_add")
    @BussinessLog(key = "/staff/staff_add", type = BussinessLogType.INSERT, value = "跳转到入职页面")
    @RequiresPermissions("staff:staff_add")
    public String staffAdd() {
        return PREFIX + "staff_add.html";
    }

    /**
     * 跳转到信息修改页面
     */
    @RequestMapping("/staff_update/{staffId}")
    @BussinessLog(key = "/staff/staff_update", type = BussinessLogType.MODIFY, value = "跳转到信息修改页面")
    @RequiresPermissions("staff:staff_update")
    public String staffUpdate(@PathVariable String staffId, Model model) {
        Staff staff = staffService.selectById(staffId);
        model.addAttribute("item",staff);
        return PREFIX + "staff_edit.html";
    }

    /**
     * 获取人员信息列表
     */
    @RequestMapping(value = "/list")
    @BussinessLog(key = "/staff/list", type = BussinessLogType.QUERY, value = "获取人员信息列表")
    @RequiresPermissions("staff:list")
    @ResponseBody
    public Object list(@RequestParam (value="condition", defaultValue = "")String condition,
                       @RequestParam(value="pageNumber", defaultValue="1")int pageNumber,
                       @RequestParam(value="pageSize", defaultValue="20") int pageSize) {
        Page<Staff> page = new Page<>(pageNumber, pageSize);
        Wrapper<Staff> ew = new EntityWrapper<>();

        if (currentUser.getPosition().getPositionId()==0){ //员工---负责部门
            //先查询拥有哪些部门的查询权限，然后筛选出这些部门的员工，如果没有任何部门的权限，传入部门编号为-1
            List<Util> permissionList = utilService.selectList((long)currentUser.getStaffId());
            if (permissionList.size() == 0){
                ew.eq("DEPARTMENT_ID",-1);
            } else {
                boolean canQuery = false;
                for (Util p: permissionList){
                    if(p.getPermissionName().equals("查看信息")){
                        canQuery = true;
                        ew.or().eq("DEPARTMENT_ID",p.getDepartmentId());
                    }
                }
                if (!canQuery){
                    ew.eq("DEPARTMENT_ID",-1);
                }
            }
        }
        if(!condition.equals("")){
            ew.andNew().like("STAFF_ID",condition).or().like("STAFF_NAME",condition);
        }
        List<Staff> list = staffService.selectPage(page, ew).getRecords();
        Map<String, Object> result = new HashMap<>(5);
        result.put("total", page.getTotal());
        result.put("rows", list);
        return result;
    }

    /**
     * 获取人员信息下拉框列表
     */
    @RequestMapping(value = "/list/select")
    @BussinessLog(key = "/staff/list/select", type = BussinessLogType.QUERY, value = "获取人员信息下拉框列表")
    @RequiresPermissions("staff:list")
    @ResponseBody
    public Object selectList(@RequestParam (value="condition", defaultValue = "")String condition,
                       @RequestParam(value="pageNumber", defaultValue="1")int pageNumber,
                       @RequestParam(value="pageSize", defaultValue="20") int pageSize) {
        Page<Staff> page = new Page<>(pageNumber, pageSize);
        Wrapper<Staff> ew = new EntityWrapper<>();

        if (currentUser.getPosition().getPositionId()==0){ //员工---负责部门
            //先查询拥有哪些部门的查询权限，然后筛选出这些部门的员工，如果没有任何部门的权限，传入部门编号为-1
            if (permissionList.size()==0){
                ew.eq("s.DEPARTMENT_ID",-1);
            } else {
                boolean canQuery = false;
                for (Util p: permissionList){
                    if(p.getPermissionName().equals("查看信息")){
                        canQuery = true;
                        ew.or().eq("s.DEPARTMENT_ID",p.getDepartmentId());
                    }
                }
                if (!canQuery){
                    ew.eq("s.DEPARTMENT_ID",-1);
                }
            }
        }
        if(!condition.equals("")){
            ew.andNew().like("STAFF_ID",condition).or().like("STAFF_NAME",condition);
        }

        Set<String> genders = staffService.getGenderSet(ew);
        Set<Department> departments = staffService.getDepartmentSet(ew);
        Set<Position> positions = staffService.getPositionSet(ew);
        Set<String> entryTimes = staffService.getEntryTimeSet(ew);
        Set<String> departureTimes = staffService.getDepartureTimeSet(ew);

        Map<String, Object> result = new HashMap<>();
        result.put("total", page.getTotal());
        result.put("genders", genders);
        result.put("departments", departments);
        result.put("positions", positions);
        result.put("entryTimes", entryTimes);
        result.put("departureTimes", departureTimes);
        return result;
    }

    /**
     * 获取根据条件筛选的人员信息
     */
    @RequestMapping(value = "/list/condition")
    @BussinessLog(key = "/staff/list/condition", type = BussinessLogType.QUERY, value = "获取根据条件筛选的人员信息")
    @RequiresPermissions("staff:list")
    @ResponseBody
    public Object conditionList(@RequestParam (value="condition", defaultValue = "")String condition,
                            @RequestParam (value="selectList")String conditions,
                       @RequestParam(value="pageNumber", defaultValue="1")int pageNumber,
                       @RequestParam(value="pageSize", defaultValue="20") int pageSize) {
        Map<String,String> info = JSONObject.parseObject(conditions,Map.class);
        Page<Staff> page = new Page<>(pageNumber, pageSize);
        String gender = info.get("gender");
        String department = info.get("department");
        String position = info.get("position");
        String entryTime = info.get("entryTime");
        String departureTime = info.get("departureTime");

        Wrapper<Staff> ew = new EntityWrapper<>();
        if (!condition.equals("")){
            ew.andNew().like("STAFF_ID",condition).or().like("STAFF_NAME",condition);
        }
        if (!gender.equals("")){
            ew.andNew().eq("GENDER",gender);
        }
        if (!department.equals("")){
            int departmentId = Integer.parseInt(department);
            ew.andNew().eq("DEPARTMENT_ID",departmentId);
        }else{
            boolean canQuery = false;
            ew.andNew();
            int count = 1;
            for (Util p: permissionList){
                if(p.getPermissionName().equals("查看信息")){
                    if (count == 1){
                        ew.eq("DEPARTMENT_ID",p.getDepartmentId());
                        count+=1;
                    }else{
                        ew.or().eq("DEPARTMENT_ID",p.getDepartmentId());
                    }
                    canQuery = true;
                }
            }
            if (!canQuery){
                ew.eq("DEPARTMENT_ID",-1);
            }
        }
        if (!position.equals("")){
            int positionId = Integer.parseInt(position);
            ew.andNew().eq("POSITION_ID",positionId);
        }
        if (!entryTime.equals("")){
            ew.andNew().like("ENTRY_TIME",entryTime+"%");
        }
        if (!departureTime.equals("")){
            if (departureTime.equals("已离职")){
                ew.andNew().isNotNull("DEPARTURE_TIME");
            }else if (departureTime.equals("在职")){
                ew.andNew().isNull("DEPARTURE_TIME");
            } else {
                ew.andNew().like("DEPARTURE_TIME",departureTime+"%");
            }
        }
        List<Staff> list = staffService.selectPage(page, ew).getRecords();
        Map<String, Object> result = new HashMap<>(5);
        result.put("total", page.getTotal());
        result.put("rows", list);
        return result;
    }

    /**
     * 员工入职
     */
    @RequestMapping(value = "/add")
    @BussinessLog(key = "/staff/add", type = BussinessLogType.INSERT, value = "员工入职")
    @RequiresPermissions("staff:add")
    @ResponseBody
    @Transactional
    public Object add(@RequestParam Map<String, String> staffInfo) {
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
        // 职位
        Position position = positionService.selectOne(new EntityWrapper<Position>()
                .eq("POSITION_ID",positionId));
        // 入职时间
        Timestamp entryTime = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(new Date(jstime)));
        //操作员
        Staff operator = currentUser;

        if (operator==null){
            Map<String,Object> res= new HashMap<>();
            res.put("code",500);
            res.put("message","该用户不在表t_staff中");
            return res;
        }

        if (operator.getPosition().getPositionId()==0){ //员工---负责部门
            //先查询拥有哪些部门的操作权限
            if (permissionList.size()==0){
                Map<String,Object> res= new HashMap<>();
                res.put("code",500);
                res.put("message","该用户无权限");
                return res;
            } else {
                List<Integer> canAddDList = new ArrayList<>();
                for (Util p: permissionList){
                    if (p.getPermissionName().equals("员工入职")){
                        canAddDList.add(p.getDepartmentId());
                    }
                }
                if (!canAddDList.contains(departmentId)){ // 能操作的部门不包含要操作的部门
                    Map<String,Object> res= new HashMap<>();
                    res.put("code",500);
                    res.put("message","该用户无权限");
                    return res;
                }
            }
        }

        // 该部门的部门经理
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

        // 公司的总经理
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
        entryLogService.addEntryLog(operator,staff,entryTime);
        inisalaryInfoService.addEmployee(staff); //录入初始工资
        monthlySalaryService.insertNewStaff(staff); // 录入当月工资表
        bonusService.insertNewStaff(staff); // 录入津贴表
        Map<String,Object> res= new HashMap<>();
        res.put("code",200);
        res.put("message","成功");
        return res;
    }

    /**
     * 员工离职
     */
    @RequestMapping(value = "/delete")
    @BussinessLog(key = "/staff/delete", type = BussinessLogType.DELETE, value = "员工离职")
    @RequiresPermissions("staff:delete")
    @ResponseBody
    @Transactional
    public Object delete(@RequestParam Map<String, String> info) {
        Long jstime = Long.parseLong(info.get("jstime"));
        Staff operator = currentUser;//查询出操作人

        if(operator == null){
            Map<String,Object> res= new HashMap<>();
            res.put("code",500);
            res.put("message","该用户不在表t_staff中");
            return res;
        }

        List<Map<String,Integer>> list = JSONArray.parseObject(info.get("staff"),List.class);
        Set<Integer> dSet = new HashSet<>(); //要离职人的部门ID列表
        for (Map<String,Integer> m : list){
            int departureId = m.get("staffId");//离职人的Id
            Staff departure = staffService.selectOne(new EntityWrapper<Staff>()
                    .eq("STAFF_ID",departureId)); //查询出离职人
            dSet.add(departure.getDepartment().getDepartmentId());
        }
        if (operator.getPosition().getPositionId()==0){ //员工---负责部门
            //先查询拥有哪些部门的修改权限
            if (permissionList.size()==0){
                Map<String,Object> res= new HashMap<>();
                res.put("code",500);
                res.put("message","该用户无权限");
                return res;
            } else {
                Set<Integer> canModifyDSet = new HashSet<>();
                for (Util p: permissionList){
                    if (p.getPermissionName().equals("员工离职")){
                        canModifyDSet.add(p.getDepartmentId());
                    }
                }
                dSet.removeAll(canModifyDSet); // 要离职的人的部门集合减去可以离职的部门的集合
                if (dSet.size() != 0){ // 差集不为空，说明存在有无权限操作的员工
                    Map<String,Object> res= new HashMap<>();
                    res.put("code",500);
                    res.put("message","选中包含无权限操作的员工");
                    return res;
                }
            }
        }

        for (Map<String,Integer> m : list){
            int departureId = m.get("staffId");//离职人的Id
            Staff departure = staffService.selectOne(new EntityWrapper<Staff>()
                    .eq("STAFF_ID",departureId)); //查询出离职人
            Timestamp departureTime = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .format(new Date(jstime)));
            departure.setDepartureTime(departureTime);
            staffService.updateById(departure); // 离职
            inisalaryInfoService.deleteEmployee(departure); // 移出初始工资表
            departureLogService.addDepartureLog(operator,departure,departureTime); // 离职日志
            monthlySalaryService.deleteStaff(departureId); // 移出当月工资表
            bonusService.deleteStaff(departureId); // 移出津贴表
        }

        Map<String,Object> res= new HashMap<>();
        res.put("code",200);
        res.put("message","成功");
        return res;
    }

    /**
     * 员工调动与信息修改
     */
    @RequestMapping(value = "/update")
    @BussinessLog(key = "/staff/update", type = BussinessLogType.MODIFY, value = "员工调动与信息修改")
    @RequiresPermissions("staff:update")
    @ResponseBody
    @Transactional
    public Object update(@RequestParam Map<String, String> staffInfo) {
        int moveId = Integer.parseInt(staffInfo.get("staffId")); //员工ID
        String staffName = staffInfo.get("staffName"); // 员工名称
        String gender = staffInfo.get("gender"); // 性别
        int departmentId = Integer.parseInt(staffInfo.get("departmentId")); // 部门ID
        int positionId = Integer.parseInt(staffInfo.get("positionId")); // 职位ID
        Long jstime = Long.parseLong(staffInfo.get("jstime")); // 变动时间

        Staff move = staffService.selectOne(new EntityWrapper<Staff>()
                .eq("STAFF_ID",moveId));

        Department oldDepartment = move.getDepartment();

        Department newDepartment = departmentService.selectOne(new EntityWrapper<Department>()
                .eq("DEPARTMENT_ID",departmentId));

        Position oldPosition = move.getPosition();

        Position newPosition = positionService.selectOne(new EntityWrapper<Position>()
                .eq("POSITION_ID",positionId));

        Timestamp operationTime = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(new Date(jstime)));        //变动时间

        Staff operator = currentUser;

        if(operator == null){
            Map<String,Object> res= new HashMap<>();
            res.put("code",500);
            res.put("message","该用户不在表t_staff中");
            return res;
        }

        if (operator.getPosition().getPositionId()==0){ //员工---负责部门
            //先查询拥有哪些部门的操作权限
            if (permissionList.size()==0){
                Map<String,Object> res= new HashMap<>();
                res.put("code",500);
                res.put("message","该用户无权限");
                return res;
            } else {
                boolean canModify = true;
                List<Integer> canModifyDList = new ArrayList<>();
                for (Util p: permissionList){
                    if (p.getPermissionName().equals("编辑信息")){
                        canModifyDList.add(p.getDepartmentId());
                    }
                }
                if (!canModifyDList.contains(departmentId)){
                    canModify = false;
                }
                if (!canModify){
                    Map<String,Object> res= new HashMap<>();
                    res.put("code",500);
                    res.put("message","该用户无权限");
                    return res;
                }
            }
        }

        Staff manager = staffService.selectOne(new EntityWrapper<Staff>()
                .eq("POSITION_ID",1).eq("DEPARTMENT_ID",newDepartment.getDepartmentId()));

        if (manager != null // 变动去的部门存在经理
                && newPosition.getPositionId() == 1 // 变动后的职位是经理
                && manager.getDepartureTime() == null // 经理没有离职
                && !(manager.getStaffId().equals(move.getStaffId())) // 不是同一个人，则存在多经理，返回错误
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

        move.setStaffName(staffName);
        move.setGender(gender);
        move.setDepartment(newDepartment);
        move.setPosition(newPosition);
        staffService.updateById(move);
        moveLogService.addMoveLog(operator,move,oldDepartment,newDepartment,oldPosition,newPosition,operationTime);
        monthlySalaryService.updateDeId(departmentId,positionId,moveId);
        inisalaryInfoService.updateEmployee(move);
        Map<String,Object> res= new HashMap<>();
        res.put("code",200);
        res.put("message","成功");
        return res;
    }

    /**
     * 员工信息详情
     */
    @RequestMapping(value = "/detail/{staffId}")
    @BussinessLog(key = "/staff/detail", type = BussinessLogType.QUERY, value = "员工信息详情")
    @RequiresPermissions("staff:detail")
    @ResponseBody
    public Object detail(@PathVariable("staffId") String staffId) {
        return staffService.selectById(staffId);
    }

    /**
     * 获取当前登录用户的Id
     */
    private int getCurrentAccountId(){
        String account = ShiroKit.getUser().getAccount();
        try{
            return Integer.parseInt(account);//当前登录的人的Id 对应STAFF_ID
        }catch (Exception e){
            account = "2020999999";
            return Integer.parseInt(account);//超级用户
        }
    }
}
