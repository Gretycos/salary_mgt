package com.jxdinfo.hussar.inisalary.controller;

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
import com.jxdinfo.hussar.inisalary.model.TInisalaryInfo;
import com.jxdinfo.hussar.inisalary.service.ITInisalaryInfoService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return PREFIX + "tInisalaryInfo_edit.html";
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
        Map<String, Object> result = new HashMap<>(5);
        String STAFF_ID = super.getPara("STAFF_ID");
        String DEPARTMENT_ID =super.getPara("DEPARTMENT_ID");
        String STAFF = STAFF_ID.replaceAll("[0-9]","");//将所有数字转换为空来判断是否为整型
        List<TInisalaryInfo> list;
        if(STAFF.length()==0){
            ew_id.like("STAFF_ID",STAFF_ID);//是整型 搜索员工ID
            ew_id.like("DEPARTMENT_ID",DEPARTMENT_ID);
            list = tInisalaryInfoService.selectPage(page, ew_id).getRecords();
        }else{//不是 查询员工姓名
            ew_name.like("STAFF_NAME",STAFF_ID);
            ew_name.like("DEPARTMENT_ID",DEPARTMENT_ID);
            list = tInisalaryInfoService.selectPage(page, ew_name).getRecords();
        }
        result.put("total", page.getTotal());
        result.put("rows", list);
        return result;
    }

    /**
     * 新增初始工资查询
     */
    @RequestMapping(value = "/add")
    @BussinessLog(key = "/tInisalaryInfo/add", type = BussinessLogType.INSERT, value = "新增初始工资查询")
    @RequiresPermissions("tInisalaryInfo:add")
    @ResponseBody
    public Object add(TInisalaryInfo tInisalaryInfo) {
        tInisalaryInfoService.insert(tInisalaryInfo);
        return SUCCESS_TIP;
    }

    /**
     * 删除初始工资查询
     */
    @RequestMapping(value = "/delete")
    @BussinessLog(key = "/tInisalaryInfo/delete", type = BussinessLogType.DELETE, value = "删除初始工资查询")
    @RequiresPermissions("tInisalaryInfo:delete")
    @ResponseBody
    public Object delete(@RequestParam String tInisalaryInfoId) {
        tInisalaryInfoService.deleteById(tInisalaryInfoId);
        return SUCCESS_TIP;
    }

    /**
     * 修改初始工资查询
     */
    @RequestMapping(value = "/update")
    @BussinessLog(key = "/tInisalaryInfo/update", type = BussinessLogType.MODIFY, value = "修改初始工资查询")
    @RequiresPermissions("tInisalaryInfo:update")
    @ResponseBody
    public Object update(TInisalaryInfo tInisalaryInfo) {
        TInisalaryInfo tInisalaryInfo1 = tInisalaryInfoService.selectById(tInisalaryInfo);
        LogObjectHolder.me().set(tInisalaryInfo1);
        tInisalaryInfoService.updateById(tInisalaryInfo);
        return SUCCESS_TIP;
    }

    /**
     * 初始工资查询详情
     */
    @RequestMapping(value = "/detail/{tInisalaryInfoId}")
    @BussinessLog(key = "/tInisalaryInfo/detail", type = BussinessLogType.QUERY, value = "初始工资查询详情")
    @RequiresPermissions("tInisalaryInfo:detail")
    @ResponseBody
    public Object detail(@PathVariable("tInisalaryInfoId") String tInisalaryInfoId) {
        return tInisalaryInfoService.selectById(tInisalaryInfoId);
    }
}
