package com.jxdinfo.hussar.inisalary.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jxdinfo.hussar.core.base.controller.BaseController;
import com.jxdinfo.hussar.core.shiro.ShiroKit;
import com.jxdinfo.hussar.inisalary.model.TInisalaryInforLog;
import com.jxdinfo.hussar.inisalary.service.ITInisalaryInforLogService;
import com.jxdinfo.hussar.inisalary.service.ITInisalaryLevelService;
import com.jxdinfo.salary.PermissionManagement.controller.UtilController;
import com.jxdinfo.salary.PermissionManagement.model.Util;
import com.jxdinfo.salary.PermissionManagement.service.IUtilService;
import com.jxdinfo.salary.position.model.Position;
import com.jxdinfo.salary.staff.model.Staff;
import com.jxdinfo.salary.staff.service.IStaffService;
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
import com.jxdinfo.hussar.inisalary.model.TInisalaryInfo;
import com.jxdinfo.hussar.inisalary.service.ITInisalaryInfoService;
import com.jxdinfo.salary.department.service.IDepartmentService;
import com.jxdinfo.salary.department.model.Department;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 初始工资查询控制器
 *
 * @author yxx
 * @Date 2020-06-16 16:37:51
 */
@Controller
@RequestMapping("/tInisalaryInfo")
public class TInisalaryInfoController extends BaseController {

    private String PREFIX = "/inisalary/tInisalaryInfo/";

    @Autowired
    private ITInisalaryInfoService tInisalaryInfoService;

    @Autowired
    private ITInisalaryLevelService tInisalaryLevelService;

    @Autowired
    private ITInisalaryInforLogService tInisalaryInforLogService;

    @Autowired
    private IStaffService staffService;

    @Autowired
    private IUtilService utilService;

    @Autowired
    private IDepartmentService departmentService;
    /**
     * 跳转到初始工资查询首页
     */
    @RequestMapping("/view")
    @BussinessLog(key = "/tInisalaryInfo/view", type = BussinessLogType.QUERY, value = "初始工资查询页面")
    @RequiresPermissions(" tInisalaryInfo:view")
    public String index() {
        return PREFIX + "tInisalaryInfo.html";
    }

    /**
     * 跳转到添加初始工资查询
     */
    @RequestMapping("/tInisalaryInfo_add")
    @BussinessLog(key = "/tInisalaryInfo/tInisalaryInfo_add", type = BussinessLogType.INSERT, value = "跳转到添加初始工资查询")
    @RequiresPermissions("tInisalaryInfo:tInisalaryInfo_add")
    public String tInisalaryInfoAdd() {
        return PREFIX + "tInisalaryInfo_add.html";
    }

    /**
     * 跳转到修改初始工资查询
     */
    @RequestMapping("/tInisalaryInfo_update/{tInisalaryInfoId}")
    @BussinessLog(key = "/tInisalaryInfo/tInisalaryInfo_update", type = BussinessLogType.MODIFY, value = "跳转到修改初始工资查询")
    @RequiresPermissions("tInisalaryInfo:tInisalaryInfo_update")
    public String tInisalaryInfoUpdate(@PathVariable String tInisalaryInfoId, Model model) {
        TInisalaryInfo tInisalaryInfo = tInisalaryInfoService.selectById(tInisalaryInfoId);
        model.addAttribute("item",tInisalaryInfo);
        return PREFIX + "tInisalaryInfo_edit2.html";
    }


    /**
     * 获取初始工资查询列表
     */
    @RequestMapping(value = "/list")
    @BussinessLog(key = "/tInisalaryInfo/list", type = BussinessLogType.QUERY, value = "获取初始工资查询列表")
    @RequiresPermissions("tInisalaryInfo:list")
    @ResponseBody
    public Object list(String condition,
                       @RequestParam(value="pageNumber", defaultValue="1")int pageNumber,
                       @RequestParam(value="pageSize", defaultValue="20") int pageSize) {

        Page<TInisalaryInfo> page = new Page<>(pageNumber, pageSize);
        Wrapper<TInisalaryInfo> ew_id = new EntityWrapper<>();
        Wrapper<TInisalaryInfo> ew_name = new EntityWrapper<>();
        Wrapper<TInisalaryInfo> ew = new EntityWrapper<>();
        Map<String, Object> result = new HashMap<>(5);
        List<TInisalaryInfo> list;

        //获得当前登录的账号
        Integer operatorId = getCurrentAccountId();
        Staff operator =staffService.selectById(operatorId);
        int positionId =operator.getPosition().getPositionId();
        Integer depId =operator.getDepartment().getDepartmentId();

        List<Integer> departmentList = new ArrayList<>();

        String STAFF_ID = super.getPara("STAFF_ID");
        String DEPARTMENT_ID =super.getPara("DEPARTMENT_ID");
        String STAFF = STAFF_ID.replaceAll("[0-9]","");//将所有数字转换为空来判断是否为整型

        if(operatorId == 2020999999 || (positionId==1&&depId==12)){//超级管理员和财务部经理可以查看所有的
            if(STAFF.length()==0){
                ew_id.like("STAFF_ID",STAFF_ID);//是整型 搜索员工ID
                ew_id.like("DEPARTMENT_ID",DEPARTMENT_ID);

                list = tInisalaryInfoService.selectPage(page, ew_id).getRecords();
            }else{//不是 查询员工姓名
                ew_name.like("STAFF_NAME",STAFF_ID);
                ew_name.like("DEPARTMENT_ID",DEPARTMENT_ID);
                list = tInisalaryInfoService.selectPage(page, ew_name).getRecords();
            }
        } else if(positionId==1){//部门经理
             ew_id.like("DEPARTMENT_ID",operator.getDepartment().getDepartmentId().toString());
             ew_name.like("DEPARTMENT_ID",operator.getDepartment().getDepartmentId().toString());

            if(STAFF.length()==0){
                ew_id.like("STAFF_ID",STAFF_ID);//是整型 搜索员工ID
              //  ew_id.like("DEPARTMENT_ID",DEPARTMENT_ID);
                list = tInisalaryInfoService.selectPage(page, ew_id).getRecords();
            }else{//不是 查询员工姓名
                ew_name.like("STAFF_NAME",STAFF_ID);
             //   ew_name.like("DEPARTMENT_ID",DEPARTMENT_ID);
                list = tInisalaryInfoService.selectPage(page, ew_name).getRecords();
            }
        }else if(depId==12){//财务部员工
            //查询拥有哪些部门的权限
            List<Util> permissionList = utilService.selectList((long)operator.getStaffId());

            for (Util p: permissionList){
                    if (p.getPermissionName().equals("查看工资")){
//                        ew_id.or().eq("DEPARTMENT_ID",p.getDepartmentId());
//                        ew_name.or().eq("DEPARTMENT_ID",p.getDepartmentId());
                        ew.or().eq("DEPARTMENT_ID",p.getDepartmentId());
                    }
            }

            if(DEPARTMENT_ID.length()==0&&STAFF_ID.length()==0){
                    list = tInisalaryInfoService.selectPage(page, ew).getRecords();
            }else{
//                if(STAFF.length()==0){
//                    ew_id.like("STAFF_ID",STAFF_ID);//是整型 搜索员工ID
//                    ew_id.like("DEPARTMENT_ID",DEPARTMENT_ID);
//                    list = tInisalaryInfoService.selectPage(page, ew_id).getRecords();
//                }else{//不是 查询员工姓名
//                    ew_name.like("STAFF_NAME",STAFF_ID);
//                    ew_name.like("DEPARTMENT_ID",DEPARTMENT_ID);
//                    list = tInisalaryInfoService.selectPage(page, ew_name).getRecords();
//                }

                if(DEPARTMENT_ID.length()!=0){
                    if(STAFF.length()==0){
                        ew_id.like("STAFF_ID",STAFF_ID);//是整型 搜索员工ID
                        ew_id.like("DEPARTMENT_ID",DEPARTMENT_ID);
                        list = tInisalaryInfoService.selectPage(page, ew_id).getRecords();
                    }else{//不是 查询员工姓名
                        ew_name.like("STAFF_NAME",STAFF_ID);
                        ew_name.like("DEPARTMENT_ID",DEPARTMENT_ID);
                        list = tInisalaryInfoService.selectPage(page, ew_name).getRecords();
                    }
                }else{
                    if(STAFF.length()==0){
                        ew_id.like("STAFF_ID",STAFF_ID);//是整型 搜索员工ID
                        ew_id.notLike("DEPARTMENT_ID",99+"");
                        ew_id.notLike("DEPARTMENT_ID",12+"");
                        list = tInisalaryInfoService.selectPage(page, ew_id).getRecords();
                    }else{//不是 查询员工姓名
                        ew_name.like("STAFF_NAME",STAFF_ID);
                        ew_name.notLike("DEPARTMENT_ID",99+"");
                        ew_name.notLike("DEPARTMENT_ID",12+"");
                        list = tInisalaryInfoService.selectPage(page, ew_name).getRecords();
                    }
//                    ew_id.like("STAFF_NAME",STAFF_ID).or().like("STAFF_ID",STAFF_ID);
//                    ew_id.notLike("DEPARTMENT_ID",99+"");
//                    ew_id.notLike("DEPARTMENT_ID",12+"");
//                    System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
//                    list = tInisalaryInfoService.selectPage(page, ew_id).getRecords();

                }

            }


          //   ew.like("STAFF_ID",STAFF_ID).or().like("STAFF_NAME",STAFF_ID);
          //   ew.like("DEPARTMENT_ID",DEPARTMENT_ID);
         //    list = tInisalaryInfoService.selectPage(page, ew).getRecords();

        }
        else{//是普通员工
                ew_id.like("STAFF_ID",-1+"");
                list = tInisalaryInfoService.selectPage(page, ew_name).getRecords();
            }


        result.put("total", page.getTotal());
        result.put("rows", list);
        return result;

    }



    /**
     * 修改员工初始工资等级
     */
    @RequestMapping(value = "/update")
    @BussinessLog(key = "/tInisalaryInfo/update", type = BussinessLogType.MODIFY, value = "修改初始工资查询")
    @RequiresPermissions("tInisalaryInfo:update")
    @ResponseBody
    public Object update(TInisalaryInfo tInisalaryInfo) {
        TInisalaryInfo tInisalaryInfo1 = tInisalaryInfoService.selectById(tInisalaryInfo);
        TInisalaryInfo t =tInisalaryInfo1;
        LogObjectHolder.me().set(tInisalaryInfo1);
        tInisalaryInfo.setInitialSalary(tInisalaryLevelService.getInisalary(tInisalaryInfo.getDepartmentId().toString(),tInisalaryInfo.getLevel().toString()));
        Date date = new Date();
        Timestamp nousedate = new Timestamp(date.getTime());
        TInisalaryInforLog tInisalaryInforLog = new TInisalaryInforLog(t.getStaffId(),t.getStaffName(),t.getDepartmentId(),t.getLevel(),
                t.getInitialSalary(),nousedate,0);
        tInisalaryInforLogService.insert(tInisalaryInforLog);
        tInisalaryInfoService.updateById(tInisalaryInfo);
        return SUCCESS_TIP;
    }

    private int getCurrentAccountId(){
        String account = ShiroKit.getUser().getAccount();
        try{
            return Integer.parseInt(account);//当前登录的人的Id 对应STAFF_ID
        }catch (Exception e){
            account = "2020999999";
            return Integer.parseInt(account);//超级用户
        }
    }
/*************************************************修改部分***************************************************/
    /**
     * 权限判断
     */
    @RequestMapping(value = "/ifPermition")
    @BussinessLog(key = "/tInisalaryInfo/ifPermition", type = BussinessLogType.QUERY, value = "详情")
    @RequiresPermissions("tInisalaryInfo:ifPermition")
    @ResponseBody
    public boolean IfPermission(String tInisalaryInfoId,String pertype) {

        Staff staff=staffService.selectById(tInisalaryInfoId);
        String departmentname=staff.getDepartment().getDepartmentName();
        TInisalaryInfo tInisalaryInfo = tInisalaryInfoService.selectById(tInisalaryInfoId);
        System.out.println(tInisalaryInfoId+"传入后台的id");
        if(tInisalaryInfo ==null){
            System.out.println("传到后台的数据为空");
        }
        Staff currentUser;
        System.out.println("传到后台的权限名称："+pertype);
        System.out.println("传到后台的部门名称："+departmentname);
        boolean flag = false;
        Integer departmentid=0;
        if(departmentname!=""){
            Wrapper<Department> w1=new EntityWrapper<>();
            w1.eq("DEPARTMENT_NAME",departmentname);
            Department department=departmentService.selectOne(w1);
            departmentid=department.getDepartmentId();
        }
        currentUser = staffService.selectById(getCurrentAccountId());
        if (currentUser.getPosition().getPositionId() == 0) {
            //员工---负责部门
            List<Util> permissionList = utilService.selectList((long) currentUser.getStaffId());
            if (permissionList.size() == 0) {
                flag = false;
            } else {
                if(departmentname!=null&&pertype!=null&&departmentname!=""){
                    for (Util p: permissionList){
                        if(p.getDepartmentId().equals(departmentid)&&p.getPermissionName().equals(pertype)){
                            flag=true;
                        }
                    }
                }else{
                    for (Util p: permissionList){
                        if(p.getPermissionName().equals(pertype)){
                            flag = true;
                        }
                    }
                    if (!flag){
                        flag=false;
                    }
                }

            }
        } else if (currentUser.getDepartment().getDepartmentId() == 12 || currentUser.getPosition().getPositionId() == 2) {
            flag = true;
        }

        return flag;
    }

/********************************************************************************************************/


    /**
     * 动态加载下拉框
     */
    @RequestMapping(value = "/select")
    @BussinessLog(key = "/tInisalaryInfo/select", type = BussinessLogType.QUERY, value = "动态加载下拉框")
    @RequiresPermissions("tInisalaryInfo:select")
    @ResponseBody
    public Object selectList(@RequestParam(value="pageNumber", defaultValue="1")int pageNumber,
                             @RequestParam(value="pageSize", defaultValue="20") int pageSize) {
        Page<TInisalaryInfo> page = new Page<>(pageNumber, pageSize);
        Wrapper<TInisalaryInfo> ew = new EntityWrapper<>();
        Wrapper<Department> ew_dep = new EntityWrapper<>();
        Staff currentUser = staffService.selectById(getCurrentAccountId()); //获取当前登录用户
        Integer currentPosId = currentUser.getPosition().getPositionId() ;
        Integer depId =currentUser.getDepartment().getDepartmentId();
        String depName = currentUser.getDepartment().getDepartmentName();
        String customsCode ="";

        List<Department> departmentList = new ArrayList<>();
        List<Department> allDepList = departmentService.selectList(ew_dep);
        if(getCurrentAccountId() == 2020999999 || (currentPosId==1&&depId==12)){//超级管理员或者财务部经理
               for(Department d : allDepList){
                   departmentList.add(d);
               }
        }
        else if(currentPosId == 1){//其他部门经理只能查看自己部门的
               Department dep = new Department();
               dep.setDepartmentId(depId);
               dep.setDepartmentName(depName);
               departmentList.add(dep);
               customsCode=depId.toString();
               System.out.println("======================添加"+dep.getDepartmentName()+"下拉菜单==================");
        }
        else if (currentPosId==0){ //员工---负责部门
            //先查询拥有哪些部门的查询权限，然后筛选出这些部门的员工，如果没有任何部门的权限，传入部门编号为-1
            List<Util> permissionList = utilService.selectList((long)currentUser.getStaffId());
            if (permissionList.size()==0){

            } else {

                for (Util p: permissionList){
                    if (p.getPermissionName().equals("查看工资")){
                        Department dep = new Department();
                        dep.setDepartmentId(p.getDepartmentId());
                        dep.setDepartmentName(p.getDepartmentName());
                        departmentList.add(dep);
                    }
                }
             //   customsCode=permissionList.get(0).getDepartmentId().toString();
            }
        }



        Set<Department> departments = new HashSet<>(departmentList);

        Map<String, Object> result = new HashMap<>();
        result.put("total", page.getTotal());
        result.put("departments", departments);
        result.put("customsCode",customsCode);
        return result;
    }
}
