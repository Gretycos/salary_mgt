package com.jxdinfo.hussar.inisalary.controller;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jxdinfo.hussar.core.base.controller.BaseController;
import com.jxdinfo.hussar.inisalary.model.TInisalaryInfo;
import com.jxdinfo.hussar.inisalary.model.TInisalaryInforLog;
import com.jxdinfo.hussar.inisalary.service.ITDepartmentService;
import com.jxdinfo.hussar.inisalary.service.ITInisalaryInfoService;

import com.jxdinfo.hussar.inisalary.service.ITInisalaryInforLogService;
import com.jxdinfo.salary.PermissionManagement.controller.UtilController;
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
import com.jxdinfo.hussar.inisalary.model.TInisalaryLevel;
import com.jxdinfo.hussar.inisalary.service.ITInisalaryLevelService;

import java.sql.Timestamp;
import java.util.*;

/**
 * 初始工资维护控制器
 *
 * @author yxx
 * @Date 2020-06-16 16:48:26
 */
@Controller
@RequestMapping("/tInisalaryLevel")
public class TInisalaryLevelController extends BaseController {

    private String PREFIX = "/inisalary/tInisalaryLevel/";

    @Autowired
    private ITInisalaryLevelService tInisalaryLevelService;

    @Autowired
    private ITInisalaryInfoService  tInisalaryInfoService;

    @Autowired
    private ITInisalaryInforLogService tInisalaryInforLogService;

    @Autowired
    private UtilController utilController;

    @Autowired
    private ITDepartmentService departmentService;

    /**
     * 跳转到初始工资维护首页
     */
    @RequestMapping("/view")
    @BussinessLog(key = "/tInisalaryLevel/view", type = BussinessLogType.QUERY, value = "初始工资维护页面")
    @RequiresPermissions("tInisalaryLevel:view")
    public String index() {
        return PREFIX + "tInisalaryLevel.html";
    }

    /**
     * 跳转到添加初始工资维护
     */
    @RequestMapping("/tInisalaryLevel_add")
    @BussinessLog(key = "/tInisalaryLevel/tInisalaryLevel_add", type = BussinessLogType.INSERT, value = "跳转到添加初始工资维护")
    @RequiresPermissions("tInisalaryLevel:tInisalaryLevel_add")
    public String tInisalaryLevelAdd() {
        return PREFIX + "tInisalaryLevel_add.html";
    }

    /**
     * 跳转到修改初始工资维护
     */
    @RequestMapping("/tInisalaryLevel_update/{tInisalaryLevelId}/{tInisalaryLevelId2}")
    @BussinessLog(key = "/tInisalaryLevel/tInisalaryLevel_update", type = BussinessLogType.MODIFY, value = "跳转到修改初始工资维护")
    @RequiresPermissions("tInisalaryLevel:tInisalaryLevel_update")
    public String tInisalaryLevelUpdate(@PathVariable String tInisalaryLevelId,@PathVariable String tInisalaryLevelId2,Model model) {
        //       tin.setDepartmentId(Integer.parseInt(tInisalaryLevelId));
//        tin.setLevel(Integer.parseInt(tInisalaryLevelId2));
     //   TInisalaryLevel tInisalaryLevel = tInisalaryLevelService.selectByTwo(tInisalaryLevelId, tInisalaryLevelId2);
        TInisalaryLevel tInisalaryLevel = tInisalaryLevelService.selectByTwo(tInisalaryLevelId,tInisalaryLevelId2);
        model.addAttribute("item",tInisalaryLevel);
        return PREFIX + "tInisalaryLevel_edit.html";
    }

    /**
     * 获取初始工资维护列表
     */
    @RequestMapping(value = "/list")
    @BussinessLog(key = "/tInisalaryLevel/list", type = BussinessLogType.QUERY, value = "获取初始工资维护列表")
    @RequiresPermissions("tInisalaryLevel:list")
    @ResponseBody
    public Object list(String condition,
                       @RequestParam(value="pageNumber", defaultValue="1")int pageNumber,
                       @RequestParam(value="pageSize", defaultValue="20") int pageSize) {
        Page<TInisalaryLevel> page = new Page<>(pageNumber, pageSize);
        Wrapper<TInisalaryLevel> ew = new EntityWrapper<>();
        Map<String, Object> result = new HashMap<>(5);
        String DEPARTMENT_ID =super.getPara("DEPARTMENT_ID");
        List<TInisalaryLevel> list;
        ew.like("DEPARTMENT_ID",DEPARTMENT_ID);
        list = tInisalaryLevelService.selectPage(page, ew).getRecords();
        result.put("total", page.getTotal());
        result.put("rows", list);
        return result;
    }

    /**
     * 新增初始工资维护
     */
    @RequestMapping(value = "/add")
    @BussinessLog(key = "/tInisalaryLevel/add", type = BussinessLogType.INSERT, value = "新增初始工资维护")
    @RequiresPermissions("tInisalaryLevel:add")
    @ResponseBody
    public Object add(TInisalaryLevel tInisalaryLevel) {
        //获取权限
        String depName = departmentService.selectById(tInisalaryLevel.getDepartmentId()).getDepartmentName();
        System.out.println("======"+utilController.getPermission("维护工资管理",depName)+"==========");
        if(utilController.getPermission("维护工资管理",depName).equals(true)){
            TInisalaryLevel t = tInisalaryLevelService.selectByTwo(tInisalaryLevel.getDepartmentId()+"",tInisalaryLevel.getLevel()+"");
            if(tInisalaryLevel.getMaximumAge()<tInisalaryLevel.getMinimumAge()) return -1;
            if(t!= null) {
                Map<String,Object> res= new HashMap<>();
                res.put("code",500);
                res.put("message","该部门等级已存在表中");
                return res;
            }
            else{
                tInisalaryLevelService.insert(tInisalaryLevel);
                Map<String,Object> res= new HashMap<>();
                res.put("code",200);
                res.put("message","添加成功");
                return res;
            }
        }else{
            Map<String,Object> res= new HashMap<>();
            res.put("code",500);
            res.put("message","没有权限操作");
            return res;
        }


    }

    /**
     * 删除初始工资维护
     */
    @RequestMapping(value = "/delete")
    @BussinessLog(key = "/tInisalaryLevel/delete", type = BussinessLogType.DELETE, value = "删除初始工资维护")
    @RequiresPermissions("tInisalaryLevel:delete")
    @ResponseBody
    public Object delete(@RequestParam List<String> tInisalaryLevelId,@RequestParam List<String> tInisalaryLevelId2) {
        //List idtInisalaryLevel = Arrays.asList(tInisalaryLevelId.split(","));

//        System.out.println("==========="+s+"==============");
//        System.out.println("==========="+tInisalaryLevelId.get(0)+"==============");
//        System.out.println("==========="+tInisalaryLevelId2.get(0)+"==============");
        int len = tInisalaryLevelId.size();
        System.out.println("==========="+len+"==============");
        boolean hasPermission = true;
        for(int i=0;i<len;i++){ //先遍历判断是否有权限
           // Map<String, Object> columnMap = new HashMap<String, Object>();
            //获取权限
            String id = tInisalaryLevelId.get(i).replaceAll("[^\\d]", "");
           // String lev = tInisalaryLevelId2.get(i).replaceAll("[^\\d]", "");
            String depName = departmentService.selectById(Integer.parseInt(id)).getDepartmentName();
            System.out.println("======"+utilController.getPermission("维护工资管理",depName)+"==========");

            if(utilController.getPermission("维护工资管理",depName).equals(true)){
//                columnMap.put("DEPARTMENT_ID",id);
//                columnMap.put("LEVEL",lev);
//                tInisalaryLevelService.deleteByMap(columnMap);
            }else{
                hasPermission =false;//没权限
            }

        }
        if(hasPermission){//有权限则删除
            for(int i=0;i<len;i++){
                Map<String, Object> columnMap = new HashMap<String, Object>();
                //获取权限
                String id = tInisalaryLevelId.get(i).replaceAll("[^\\d]", "");
                String lev = tInisalaryLevelId2.get(i).replaceAll("[^\\d]", "");

                columnMap.put("DEPARTMENT_ID",id);
                columnMap.put("LEVEL",lev);
                tInisalaryLevelService.deleteByMap(columnMap);

            }
            Map<String,Object> res= new HashMap<>();
            res.put("code",200);
            res.put("message","删除成功");
            return res;
        }else{
            System.out.println("======没有权限aa========");
            Map<String,Object> res= new HashMap<>();
            res.put("code",500);
            res.put("message","没有权限操作");
            return res;
        }


    }


    /**
     * 修改初始工资维护
     */
    @RequestMapping(value = "/update")
    @BussinessLog(key = "/tInisalaryLevel/update", type = BussinessLogType.MODIFY, value = "修改初始工资维护")
    @RequiresPermissions("tInisalaryLevel:update")
    @ResponseBody
    public Object update(TInisalaryLevel tInisalaryLevel) {
        //获取权限
        String depName = departmentService.selectById(tInisalaryLevel.getDepartmentId()).getDepartmentName();
        System.out.println("======"+utilController.getPermission("维护工资管理",depName)+"==========");

        if(utilController.getPermission("维护工资管理",depName).equals(true)){
            String t1 =tInisalaryLevel.getDepartmentId()+"";
            String t2 =tInisalaryLevel.getLevel()+"";
            int inisal1 =tInisalaryLevel.getInitialSalary();
            TInisalaryLevel tInisalaryLevel1 = tInisalaryLevelService.selectByTwo(t1,t2);
            int inisal2 = tInisalaryLevel1.getInitialSalary();
            boolean equal =(inisal1==inisal2);
            //    System.out.println(inisal1+"--------------"+inisal2+"-----------"+equal);

            LogObjectHolder.me().set(tInisalaryLevel1);
            Wrapper<TInisalaryLevel> wrapper = new EntityWrapper<>();
            wrapper.eq("DEPARTMENT_ID",tInisalaryLevel.getDepartmentId());
            wrapper.eq("LEVEL",tInisalaryLevel.getLevel());
            tInisalaryLevelService.update(tInisalaryLevel,wrapper);

            //修改员工初始工资表
            Map<String, Object> columnMap = new HashMap<String, Object>();
            columnMap.put("DEPARTMENT_ID",tInisalaryLevel.getDepartmentId());
            columnMap.put("LEVEL",tInisalaryLevel.getLevel());
            List<TInisalaryInfo> infoList = tInisalaryInfoService.selectByMap(columnMap);

            for (TInisalaryInfo t : infoList) {
                Date date = new Date();
                Timestamp nousedate = new Timestamp(date.getTime());
                TInisalaryInforLog tInisalaryInforLog = new TInisalaryInforLog(t.getStaffId(),t.getStaffName(),t.getDepartmentId(),t.getLevel(),
                        t.getInitialSalary(),nousedate,0);
                if(!equal){
                    t.setInitialSalary(tInisalaryLevel.getInitialSalary());
                    tInisalaryInfoService.updateById(t);
                    tInisalaryInforLogService.insert(tInisalaryInforLog);
                }else{
                    t.setInitialSalary(tInisalaryLevel.getInitialSalary());
                    tInisalaryInfoService.updateById(t);
                }

            }
            Map<String,Object> res= new HashMap<>();
            res.put("code",200);
            res.put("message","修改成功");
            return res;
        }else{
            Map<String,Object> res= new HashMap<>();
            res.put("code",500);
            res.put("message","没有权限操作");
            return res;
        }

    }

    /**
     * 初始工资维护详情
     */
    @RequestMapping(value = "/detail/{tInisalaryLevelId}")
    @BussinessLog(key = "/tInisalaryLevel/detail", type = BussinessLogType.QUERY, value = "初始工资维护详情")
    @RequiresPermissions("tInisalaryLevel:detail")
    @ResponseBody
    public Object detail(@PathVariable("tInisalaryLevelId") String tInisalaryLevelId) {
        return tInisalaryLevelService.selectById(tInisalaryLevelId);
    }
}
