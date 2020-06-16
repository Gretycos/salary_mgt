/*
 * 金现代轻骑兵V8开发平台 
 * ChangeToPinyinDemo.java 
 * 版权所有：金现代信息产业股份有限公司  Copyright (c) 2018-2023 .
 * 金现代信息产业股份有限公司保留所有权利,未经允许不得以任何形式使用.
 */
package com.jxdinfo.hussar.hussardemo.controller;

import com.jxdinfo.hussar.common.projecttool.PinYinUtil;
import com.jxdinfo.hussar.core.base.controller.BaseController;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 类的用途：汉字转拼音Demo
 * 创建日期：2018年10月17日
 * 修改历史：
 * 修改日期：2018年10月17日
 * 修改作者：XinXueLei
 * 修改内容：修改内容
 * @author XinXueLei
 * @version 1.0
 */
@RequestMapping("/changeToPinyinDemo")
@Controller
public class ChangeToPinyinDemo extends BaseController {
    /**
     *
     * 汉字转拼音demo页面
     * @Title: view
     * @author: XinXueLei
     * @return 页面
     */
    @RequestMapping("/view")
    public String view(Model model) {
        return "/hussardemo/changeToPinyinDemo.html";
    }

    /**
     *
     * 将汉字转为拼音，全部大写
     * @author: XinXueLei
     * @return String
     */
    @RequestMapping("/changeToPinyinUp")
    @ResponseBody
    public String changeToPinyinUp() {
        String beforeChangeStr = super.getPara("beforeChangeOne");
        String afterChangeStr = PinYinUtil.changeToPinyin(beforeChangeStr, HanyuPinyinCaseType.UPPERCASE);
        return afterChangeStr;
    }

    /**
     *
     * 将汉字转为拼音，全部小写
     * @author: XinXueLei
     * @return String
     */
    @RequestMapping("/changeToPinyinLow")
    @ResponseBody
    public String changeToPinyinLow() {
        String beforeChangeStr = super.getPara("beforeChangeTwo");
        String afterChangeStr = PinYinUtil.changeToPinyin(beforeChangeStr, HanyuPinyinCaseType.LOWERCASE);
        return afterChangeStr;
    }

    /**
     *
     * 获取每个汉字的首字母大写
     * @author: XinXueLei
     * @return String
     */
    @RequestMapping("/getFirstLettersUp")
    @ResponseBody
    public String getFirstLettersUp() {
        String beforeChangeStr = super.getPara("beforeChangeThree");
        String afterChangeStr = PinYinUtil.getFirstLetters(beforeChangeStr,HanyuPinyinCaseType.UPPERCASE);
        return afterChangeStr;
    }

    /**
     *
     * 获取每个汉字的首字母小写
     * @author: XinXueLei
     * @return String
     */
    @RequestMapping("/getFirstLettersLow")
    @ResponseBody
    public String getFirstLettersLow() {
        String beforeChangeStr = super.getPara("beforeChangeFour");
        String afterChangeStr = PinYinUtil.getFirstLetters(beforeChangeStr,HanyuPinyinCaseType.LOWERCASE);
        return afterChangeStr;
    }
}
