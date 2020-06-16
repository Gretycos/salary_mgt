/*
 * 金现代轻骑兵V8开发平台 
 * OrgTreeDemo.java 
 * 版权所有：金现代信息产业股份有限公司  Copyright (c) 2018-2023 .
 * 金现代信息产业股份有限公司保留所有权利,未经允许不得以任何形式使用.
 */
package com.jxdinfo.hussar.hussardemo.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.jxdinfo.hussar.bsp.constant.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.jxdinfo.hussar.bsp.organ.dao.SysOrganTypeMapper;
import com.jxdinfo.hussar.common.organutil.OrganUtil;
import com.jxdinfo.hussar.common.treemodel.JSTreeModel;
import com.jxdinfo.hussar.common.userutil.UserUtil;
import com.jxdinfo.hussar.core.base.controller.BaseController;

/**
 * 类的用途：<p>
 * 创建日期：2018年5月28日 <br>
 * 修改历史：<br>
 * 修改日期：2018年5月28日 <br>
 * 修改作者：ChenXin <br>
 * 修改内容：修改内容 <br>
 * @author ChenXin
 * @version 1.0
 */
@Controller
@RequestMapping("orgTreeDemo")
public class OrgTreeDemo extends BaseController {

    /**
     * 组织机构工具类
     */
    @Resource
    private OrganUtil organUtil;

    /**
     * 用户相关的通用方法
     */
    @Resource
    private UserUtil userUtil;

    /**
     * 组织类型 Mapper 接口
     */
    @Resource
    private SysOrganTypeMapper sysOrganTypeMapper;

    /**
     *
     * 组织机构树demo页面
     * @Title: view
     * @author: ChenXin
     * @return 页面
     */
    @RequestMapping("/view")
    public String view(Model model, HttpServletRequest request) {
        JSONObject json = new JSONObject();
        List list = this.sysOrganTypeMapper.getOrganTypeList(new Page(), null, null);
        for (int i = 0; i < list.size(); i++) {
            Map<String, String> map = (Map) list.get(i);
            JSONObject jsonObject = new JSONObject();
            if (request instanceof HttpServletRequest) {
                HttpServletRequest hreq = (HttpServletRequest) request;
                String contextPath = "";
                contextPath = hreq.getSession().getServletContext().getContextPath();
                jsonObject.put("icon", contextPath + map.get("IMGURL"));
            } else {
                jsonObject.put("icon", request.getServletContext().getContextPath() + map.get("IMGURL"));
            }
            json.put(map.get("ORGANTYPE"), jsonObject);
        }
        model.addAttribute("imgUrl", json);// 后台获取节点图标
        return "/hussardemo/orgTreeDemo.html";
    }

    /**
     *
     * 获取组织机构树
     * @Title: orgTree
     * @author: ChenXin
     * @return List<Map<String, Object>>
     */
    @RequestMapping("/orgTree")
    @ResponseBody
    public List<JSTreeModel> orgTree() {
        String treeType = super.getPara("treeType");
        List<JSTreeModel> result = this.organUtil.getOrgTreeByTreeType(treeType);
        // 根节点名称
        JSTreeModel root = new JSTreeModel();
        root.setId(Constants.ROOT_NODE_ID);
        root.setCode("");
        root.setText("组织机构");
        root.setParent("#");
        root.setStruLevel("0");
        root.setIsLeaf("0");
        root.setType("isRoot");
        result.add(root);
        return result;

    }

    /**
     *
     * 获取人员树
     * @Title: employeeTree
     * @author: ChenXin
     * @return List<Map<String, Object>>
     */
    @RequestMapping("/employeeTree")
    @ResponseBody
    public List<JSTreeModel> employeeTree() {
        String treeType = super.getPara("treeType");
        List<JSTreeModel> result = this.organUtil.getOrgTreeByTreeType(treeType);
        // 根节点名称
        JSTreeModel root = new JSTreeModel();
        root.setId(Constants.ROOT_NODE_ID);
        root.setCode("");
        root.setText("人员组织机构");
        root.setParent("#");
        root.setStruLevel("0");
        root.setIsLeaf("0");
        root.setType("isRoot");
        result.add(root);
        return result;

    }

    /**
     *
     * 当前角色用户树
     * @Title: roleUserTree
     * @author: ChenXin
     * @return List<JSTreeModel>
     */
    @RequestMapping("/roleUserTree")
    @ResponseBody
    public List<JSTreeModel> roleUserTree() {
        // roleId 为角色id,当前以'public_role' 为例
        String roleId = super.getPara("roleId");
        List<JSTreeModel> result = this.userUtil.getUserByRole(roleId);
        // 拼入根节点
        JSTreeModel root = new JSTreeModel();
        root.setId(Constants.ROOT_NODE_ID);
        root.setCode(Constants.ROOT_NODE_ID);
        root.setText("当前角色用户树");
        root.setParent("#");
        root.setType("isRoot");
        result.add(root);

        return result;
    }

    /**
     *
     * 用户树
     * @Title: userTree
     * @author: ChenXin
     * @return List<Map<String, Object>>
     */
    @RequestMapping("/userTree")
    @ResponseBody
    public List<JSTreeModel> userTree() {
        String treeType = super.getPara("treeType");
        List<JSTreeModel> result = this.organUtil.getOrgTreeByTreeType(treeType);
        // 拼入根节点
        JSTreeModel root = new JSTreeModel();
        root.setId(Constants.ROOT_NODE_ID);
        root.setCode("");
        root.setText("系统用户");
        root.setParent("#");
        root.setStruLevel("0");
        root.setIsLeaf("0");
        root.setType("isRoot");
        result.add(root);
        return result;
    }

}
