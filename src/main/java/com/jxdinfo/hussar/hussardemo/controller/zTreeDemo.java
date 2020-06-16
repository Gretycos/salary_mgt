package com.jxdinfo.hussar.hussardemo.controller;

import com.jxdinfo.hussar.bsp.constant.Constants;
import com.jxdinfo.hussar.common.organutil.OrganUtil;
import com.jxdinfo.hussar.common.treemodel.ZTreeModel;
import com.jxdinfo.hussar.core.base.controller.BaseController;
import com.jxdinfo.hussar.core.util.ToolUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 类的用途：zTree Demo
 * 创建日期：2019-02-13 09:30;
 * 修改者：ChenXin;
 *
 * @author ChenXin;
 * @version 1.0
 */
@Controller
@RequestMapping("/ztreeDemo")
public class zTreeDemo extends BaseController{

    /**
     * 组织机构工具类
     */
    @Resource
    private OrganUtil organUtil;


     /**
      * 跳转ztree demo页面
      * @return
      */
    @RequestMapping("/view")
    @RequiresPermissions("ztreeDemo:view")
    public String view(){
        return "/hussardemo/ztreeDemo.html";
    }

     /**
      * 获取ztree数据
      * @return
      */
    @RequestMapping("/getNodes")
    @ResponseBody
    public List<ZTreeModel> getNodes(){
        String parentId = super.getPara("id");
        ZTreeModel zTreeModel = new ZTreeModel();
        List<ZTreeModel> zTree = new ArrayList<ZTreeModel>();
        //懒加载，首次加载parentId为null
        if(ToolUtil.isEmpty(parentId)){
            parentId = Constants.ROOT_NODE_ID;
            zTreeModel.setId(Constants.ROOT_NODE_ID);
            zTreeModel.setName("组织机构");
            zTreeModel.setpId(Constants.ROOT_NODE_PARENT);
            //根节点图标className
            zTreeModel.setIconSkin(Constants.ROOT_NODE_ID);
            //根节点是否展开
            zTreeModel.setOpen(true);
            //拼入接根节点
            zTree.add(zTreeModel);
        }
        zTree.addAll(organUtil.getZtreeToOrg(parentId));
        return zTree;
    }
}
