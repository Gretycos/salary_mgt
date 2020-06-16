/*
 * 金现代轻骑兵V8开发平台 
 * RestDemoController.java 
 * 版权所有：金现代信息产业股份有限公司  Copyright (c) 2018-2023 .
 * 金现代信息产业股份有限公司保留所有权利,未经允许不得以任何形式使用.
 */
package com.jxdinfo.hussar.hussardemo.controller;

import java.util.List;

import javax.annotation.Resource;

import com.jxdinfo.hussar.bsp.constant.Constants;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jxdinfo.hussar.bsp.organ.dao.SysOrganTypeMapper;
import com.jxdinfo.hussar.common.organutil.OrganUtil;
import com.jxdinfo.hussar.common.treemodel.JSTreeModel;
import com.jxdinfo.hussar.common.userutil.UserUtil;
import com.jxdinfo.hussar.core.base.controller.BaseController;

/**
 * 类的用途：Rest示例Controller<p>
 * 创建日期：2018年6月1日 <br>
 * 修改历史：<br>
 * 修改日期：2018年6月1日 <br>
 * 修改作者：WangBinBin <br>
 * 修改内容：修改内容 <br>
 *
 * @author WangBinBin
 * @version 1.0
 */
@RestController
@RequestMapping("/rest")
public class RestDemoController extends BaseController {

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

    public static final String ROLEID = "public_role";

    /**
     * 获取组织机构树
     *
     * @return List<Map<String, Object>>
     * @Title: orgTree
     * @author: ChenXin
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
     * 获取人员树
     *
     * @return List<Map<String, Object>>
     * @Title: employeeTree
     * @author: ChenXin
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
     * 当前角色用户树
     *
     * @return List<Map<String, Object>>
     * @Title: roleUserTree
     * @author: ChenXin
     */
    @RequestMapping("/roleUserTree")
    @ResponseBody
    public List<JSTreeModel> roleUserTree() {
        // roleId 为角色id,当前以'public_role' 为例
        List<JSTreeModel> result = this.userUtil.getUserByRole(RestDemoController.ROLEID);
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
     * 用户树
     *
     * @return List<Map<String, Object>>
     * @Title: userTree
     * @author: ChenXin
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
