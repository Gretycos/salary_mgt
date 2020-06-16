/*
 * 金现代轻骑兵V8开发平台 
 * EncoderQRCodeDemo.java 
 * 版权所有：金现代信息产业股份有限公司  Copyright (c) 2018-2023 .
 * 金现代信息产业股份有限公司保留所有权利,未经允许不得以任何形式使用.
 */
package com.jxdinfo.hussar.hussardemo.controller;

import com.jxdinfo.hussar.core.base.controller.BaseController;
import com.jxdinfo.hussar.core.util.QRCodeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 类的用途：二维码生成Demo
 * 创建日期：2018年10月18日
 * 修改历史：
 * 修改日期：2018年10月18日
 * 修改作者：XinXueLei
 * 修改内容：修改内容
 * @author XinXueLei
 * @version 1.0
 */
@RequestMapping("/encoderQRCodeDemo")
@Controller
public class EncoderQRCodeDemo extends BaseController {
    /**
     *
     * 二维码生成demo页面
     * @Title: view
     * @author: XinXueLei
     * @return 页面
     */
    @RequestMapping("/view")
    public String view(Model model) {
        return "/hussardemo/encoderQRCodeDemo.html";
    }

    /**
     *
     * 生成二维码
     * @author: XinXueLei
     * @return String
     */
    @RequestMapping("/createAndDisplay")
    @ResponseBody
    public void createAndDisplay(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String content = super.getPara("content");
        BufferedImage bufImage = QRCodeUtil.getBufferedImage(content, null);

        // 直接打开图片
        response.setContentType("image/jpg");
        // 将图片写出到浏览器
        OutputStream out = response.getOutputStream();
        ImageIO.write(bufImage, "jpg", out);
        out.close();
    }

}
