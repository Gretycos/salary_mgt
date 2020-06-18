package com.jxdinfo.salary.position.controller;

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
import com.jxdinfo.salary.position.model.Position;
import com.jxdinfo.salary.position.service.IPositionService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 职位控制器
 *
 * @author hyc
 * @Date 2020-06-17 16:19:57
 */
@Controller
@RequestMapping("/position")
public class PositionController extends BaseController {

    private String PREFIX = "/position/position/";

    @Autowired
    private IPositionService positionService;

    /**
     * 跳转到职位首页
     */
    @RequestMapping("/view")
    @BussinessLog(key = "/position/view", type = BussinessLogType.QUERY, value = "职位页面")
    @RequiresPermissions("position:view")
    public String index() {
        return PREFIX + "position.html";
    }

    /**
     * 跳转到添加职位
     */
    @RequestMapping("/position_add")
    @BussinessLog(key = "/position/position_add", type = BussinessLogType.INSERT, value = "跳转到添加职位")
    @RequiresPermissions("position:position_add")
    public String positionAdd() {
        return PREFIX + "position_add.html";
    }

    /**
     * 跳转到修改职位
     */
    @RequestMapping("/position_update/{positionId}")
    @BussinessLog(key = "/position/position_update", type = BussinessLogType.MODIFY, value = "跳转到修改职位")
    @RequiresPermissions("position:position_update")
    public String positionUpdate(@PathVariable String positionId, Model model) {
        Position position = positionService.selectById(positionId);
        model.addAttribute("item",position);
        return PREFIX + "position_edit.html";
    }

    /**
     * 获取职位列表，包含职位人员信息
     */
    @RequestMapping(value = "/list/details")
    @BussinessLog(key = "/position/list/details", type = BussinessLogType.QUERY, value = "获取职位列表")
    @RequiresPermissions("position:list")
    @ResponseBody
    public Object listDetails(String condition,
                       @RequestParam(value="pageNumber", defaultValue="1")int pageNumber,
                       @RequestParam(value="pageSize", defaultValue="20") int pageSize) {
        Page<Position> page = new Page<>(pageNumber, pageSize);
        Wrapper<Position> ew = new EntityWrapper<>();
        Map<String, Object> result = new HashMap<>(5);
        List<Position> list = positionService.selectDetailsPage(page, ew).getRecords();
        result.put("total", page.getTotal());
        result.put("rows", list);
        return result;
    }

    /**
     * 获取职位列表
     */
    @RequestMapping(value = "/list")
    @BussinessLog(key = "/position/list", type = BussinessLogType.QUERY, value = "获取职位列表")
    @RequiresPermissions("position:list")
    @ResponseBody
    public Object list(String condition,
                       @RequestParam(value="pageNumber", defaultValue="1")int pageNumber,
                       @RequestParam(value="pageSize", defaultValue="20") int pageSize) {
        Page<Position> page = new Page<>(pageNumber, pageSize);
        Wrapper<Position> ew = new EntityWrapper<>();
        Map<String, Object> result = new HashMap<>(5);
        List<Position> list = positionService.selectPage(page, ew).getRecords();
        result.put("total", page.getTotal());
        result.put("rows", list);
        return result;
    }

    /**
     * 新增职位
     */
    @RequestMapping(value = "/add")
    @BussinessLog(key = "/position/add", type = BussinessLogType.INSERT, value = "新增职位")
    @RequiresPermissions("position:add")
    @ResponseBody
    public Object add(Position position) {
        positionService.insert(position);
        return SUCCESS_TIP;
    }

    /**
     * 删除职位
     */
    @RequestMapping(value = "/delete")
    @BussinessLog(key = "/position/delete", type = BussinessLogType.DELETE, value = "删除职位")
    @RequiresPermissions("position:delete")
    @ResponseBody
    public Object delete(@RequestParam String positionId) {
        positionService.deleteById(positionId);
        return SUCCESS_TIP;
    }

    /**
     * 修改职位
     */
    @RequestMapping(value = "/update")
    @BussinessLog(key = "/position/update", type = BussinessLogType.MODIFY, value = "修改职位")
    @RequiresPermissions("position:update")
    @ResponseBody
    public Object update(Position position) {
        Position position1 = positionService.selectById(position);
        LogObjectHolder.me().set(position1);
        positionService.updateById(position);
        return SUCCESS_TIP;
    }

    /**
     * 职位详情
     */
    @RequestMapping(value = "/detail/{positionId}")
    @BussinessLog(key = "/position/detail", type = BussinessLogType.QUERY, value = "职位详情")
    @RequiresPermissions("position:detail")
    @ResponseBody
    public Object detail(@PathVariable("positionId") String positionId) {
        return positionService.selectById(positionId);
    }
}
