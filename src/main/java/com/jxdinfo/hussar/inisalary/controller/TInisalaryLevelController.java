package com.jxdinfo.hussar.inisalary.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jxdinfo.hussar.core.base.controller.BaseController;
import com.jxdinfo.hussar.inisalary.model.TInisalaryInfo;
import com.jxdinfo.hussar.inisalary.service.ITInisalaryInfoService;
import com.mysql.cj.x.protobuf.Mysqlx;
import org.apache.ibatis.jdbc.Null;
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

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        TInisalaryLevel t = tInisalaryLevelService.selectByTwo(tInisalaryLevel.getDepartmentId()+"",tInisalaryLevel.getLevel()+"");
        if(tInisalaryLevel.getMaximumAge()<tInisalaryLevel.getMinimumAge()) return -1;
        if(t!= null) {
            return 0;
        }
        else{
            tInisalaryLevelService.insert(tInisalaryLevel);
            return 1;
        }

    }

    /**
     * 删除初始工资维护
     */
    @RequestMapping(value = "/delete")
    @BussinessLog(key = "/tInisalaryLevel/delete", type = BussinessLogType.DELETE, value = "删除初始工资维护")
    @RequiresPermissions("tInisalaryLevel:delete")
    @ResponseBody
    public Object delete(@RequestParam String tInisalaryLevelId,@RequestParam String tInisalaryLevelId2) {
        //List idtInisalaryLevel = Arrays.asList(tInisalaryLevelId.split(","));
        Map<String, Object> columnMap = new HashMap<String, Object>();
        columnMap.put("DEPARTMENT_ID",tInisalaryLevelId);
        columnMap.put("LEVEL",tInisalaryLevelId2);

        tInisalaryLevelService.deleteByMap(columnMap);
        return SUCCESS_TIP;
    }


    /**
     * 修改初始工资维护
     */
    @RequestMapping(value = "/update")
    @BussinessLog(key = "/tInisalaryLevel/update", type = BussinessLogType.MODIFY, value = "修改初始工资维护")
    @RequiresPermissions("tInisalaryLevel:update")
    @ResponseBody
    public Object update(TInisalaryLevel tInisalaryLevel) {
        String t1 =tInisalaryLevel.getDepartmentId()+"";
        String t2 =tInisalaryLevel.getLevel()+"";
        TInisalaryLevel tInisalaryLevel1 = tInisalaryLevelService.selectByTwo(t1,t2);
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
            t.setInitialSalary(tInisalaryLevel.getInitialSalary());
            tInisalaryInfoService.updateById(t);
        }

        return SUCCESS_TIP;
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
