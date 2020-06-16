/*
 * 金现代轻骑兵V8开发平台 
 * AttachmentDemo.java 
 * 版权所有：金现代信息产业股份有限公司  Copyright (c) 2018-2023 .
 * 金现代信息产业股份有限公司保留所有权利,未经允许不得以任何形式使用.
 */
package com.jxdinfo.hussar.hussardemo.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.jxdinfo.hussar.common.attachment.model.AttachmentManagerModel;
import com.jxdinfo.hussar.common.attachment.service.AttachmentManagerService;
import com.jxdinfo.hussar.common.exception.BizExceptionEnum;
import com.jxdinfo.hussar.config.properties.HussarProperties;
import com.jxdinfo.hussar.core.base.controller.BaseController;
import com.jxdinfo.hussar.core.exception.HussarException;
import com.jxdinfo.hussar.core.shiro.ShiroKit;
import com.jxdinfo.hussar.core.util.ToolUtil;

/**
 * 类的用途：<p>
 * 创建日期：2018年5月30日 <br>
 * 修改历史：<br>
 * 修改日期：2018年5月30日 <br>
 * 修改作者：ChenXin <br>
 * 修改内容：修改内容 <br>
 * @author ChenXin
 * @version 1.0
 */
@Controller
@RequestMapping("/attachmentDemo")
public class AttachmentDemo extends BaseController {

    /**
     * 日志
     */
    private static Logger logger = LogManager.getLogger(AttachmentDemo.class);

    /**
     * 日志Service
     */
    @Resource
    private AttachmentManagerService attachmentManagerService;

    /**
     * 配置文件
     */
    @Autowired
    private HussarProperties hussarProperties;

    /**
     * 跳转到附件管理demo页面
     * @Title: view 
     * @author: ChenXin
     * @return 返回页面
     */
    @RequestMapping(value = "/view")
    public String view() {
        return "/hussardemo/attachmentDemo.html";
    }

    /**
     * 上传文件
     * @Title: upload
     * @author: WangBinBin
     * @param file 上传的文件
     * @return  文件名
     */
    @RequestMapping(method = RequestMethod.POST, path = "/upload")
    @ResponseBody
    public String upload(@RequestPart("file") MultipartFile file) {

        String fileName = file.getOriginalFilename();
        String prefix = fileName.substring(fileName.lastIndexOf("."));
        String fName = IdWorker.get32UUID() + prefix;
        try {
            String fileSavePath = this.hussarProperties.getFileUploadPath();
            file.transferTo(new File(fileSavePath + fName));
        } catch (IOException e) {
            throw new HussarException(BizExceptionEnum.UPLOAD_ERROR);
        }
        return fName;
    }

    /**
     * 多文件上传
     * @Title: uploadfileWithDrag
     * @author: WangBinBin
     * @return 返回json
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST, path = "/uploadfilewithdrag")
    @ResponseBody
    public Map<String, String> uploadfileWithDrag(MultipartHttpServletRequest multipartRequest) throws Exception {
        multipartRequest.setCharacterEncoding("UTF-8");
        Map<String, String> result = new HashMap<String, String>();

        // 业务id
        String businessId = multipartRequest.getParameter("businessId");

        // 多文件上传实现
        MultipartFile multipartFile = null;
        Map<String, MultipartFile> map = multipartRequest.getFileMap();
        for (MultipartFile value : map.values()) {
            multipartFile = value;
        }

        if (null != multipartFile) {
            String fileName = multipartFile.getOriginalFilename();
            // 获取文件后缀
            String suffix = fileName.substring(fileName.lastIndexOf("."));
            // 随机生成上传目录中的文件名称
            String id = IdWorker.get32UUID();

            try {
                // 上传路径
                String fileSavePath = this.hussarProperties.getFileUploadPath();
                multipartFile.transferTo(new File(fileSavePath + id + suffix));

                // 附件信息写数据库
                AttachmentManagerModel attachment = new AttachmentManagerModel();
                attachment.setId(id);
                attachment.setBusinessId(businessId);
                attachment.setAttachmentName(fileName);
                attachment.setAttachmentType(suffix.replace(".", ""));
                attachment.setUploadPer(ShiroKit.getUser().getId());
                attachment.setAttachmentDir(fileSavePath);
                attachment.setUploadDate(new Date());
                this.attachmentManagerService.insert(attachment);

                result.put("id", id);
                result.put("fileName", fileName);
            } catch (IOException e) {
                throw new HussarException(BizExceptionEnum.UPLOAD_ERROR);
            }
        }

        return result;
    }

    /**
     * 文件下载
     * @param request 请求
     * @param response 响应
     */
    @RequestMapping("/fileDownload")
    public void fileDownload(HttpServletRequest request, HttpServletResponse response) {
        // 下载的文件名
        Map<String, String> fileMap = this.attachmentManagerService.findById(request.getParameter("fileId"));
        String fileName = fileMap.get("NAME");
        String id = fileMap.get("ID");
        String prefix = fileName.substring(fileName.lastIndexOf("."));
        // 文件路径
        String path = this.attachmentManagerService.finddirById(id) + id + prefix;
        InputStream bis = null;
        BufferedOutputStream out = null;
        // 获取输入流
        try {
            bis = new BufferedInputStream(new FileInputStream(new File(path)), 1024 * 10);
            // 假如以中文名下载的话
            // 转码，免得文件名中文乱码
            fileName = URLEncoder.encode(fileName, "UTF-8");
            // 设置文件下载头
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            // 设置文件ContentType类型，这样设置，会自动判断下载文件类型
            response.setContentType("multipart/form-data");
            out = new BufferedOutputStream(response.getOutputStream());
            int len = 0;
            int i = bis.available();
            byte[] buff = new byte[i];
            while ((len = bis.read(buff)) > 0) {
                out.write(buff, 0, len);
                out.flush();
            }
        } catch (FileNotFoundException e) {
            logger.error("文件未找到" + e.getMessage());
            throw new HussarException(BizExceptionEnum.FILE_NOT_FOUND);
        } catch (UnsupportedEncodingException e) {
            logger.error("不支持的编码异常：" + e.getMessage());
            throw new HussarException(BizExceptionEnum.DOWNLOAD_ERROR);
        } catch (IOException e) {
            logger.error("IO异常：" + e.getMessage());
            throw new HussarException(BizExceptionEnum.DOWNLOAD_ERROR);
        } finally {
            try {
                if (null != bis) {
                    bis.close();
                }
            } catch (IOException e) {
                logger.error("流关闭异常" + e.getMessage());
            }
            try {
                if (null != out) {
                    out.close();
                }
            } catch (IOException e) {
                logger.error("流关闭异常" + e.getMessage());
            }
        }
    }

    /**
     * 批量下载
     * @author: ChenXin
     */
    @RequestMapping(value = "/BatchDownload", method = RequestMethod.GET)
    @ResponseBody
    public void batchDownload(@RequestParam(value = "fileId") String ids, HttpServletResponse response)
            throws ServletException, IOException {
        // 文件路径最后带分隔符"/"
        OutputStream os = response.getOutputStream();
        // 获取zip的输出流
        ZipOutputStream zos = new ZipOutputStream(os);
        // 定义输入流
        BufferedInputStream bis = null;
        // 通过连接得到输入流
        InputStream inputStream = null;
        // 从数据库中取出要下载的文件记录
        List<String> fileIdList = Arrays.asList(ids.split(","));
        List<AttachmentManagerModel> attachList = attachmentManagerService.selectBatchIds(fileIdList);

        try {
            // 设置压缩后的zip文件名
            String sourceFilePath = "批量下载.zip";

            // 设置content-disposition响应头控制浏览器弹出保存框，若没有此句则浏览器会直接打开并显示文件。
            // 中文名要经过URLEncoder.encode编码，否则虽然客户端能下载但显示的名字是乱码

            response.setHeader("content-disposition",
                    "attachment;filename=" + URLEncoder.encode(sourceFilePath, "UTF-8"));

            // 文件编号
            int count = 0;
            for (AttachmentManagerModel attach : attachList) {
                count++;
                // ID
                String id = attach.getId();
                // 文件名
                String fileName = attach.getAttachmentName();
                // 文件后缀
                String suffix = fileName.substring(fileName.lastIndexOf("."));
                String path = attach.getAttachmentDir() + id + suffix;

                // 通过连接得到输入流
                inputStream = new BufferedInputStream(new FileInputStream(new File(path)));
                byte[] buf = new byte[inputStream.available()];
                int len = 0;

                // 创建ZIP实体，并添加进压缩包
                ZipEntry zipEntry = new ZipEntry(count + "-" + fileName);
                zos.putNextEntry(zipEntry);
                bis = new BufferedInputStream(inputStream, 1024 * 10);
                while ((len = bis.read(buf)) > 0) {
                    // 使用OutputStream将缓冲区的数据输出到客户端浏览器
                    zos.write(buf, 0, len);
                    zos.flush();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new HussarException(BizExceptionEnum.DOWNLOAD_ERROR);
        } finally {
            try {
                if (null != zos) {
                    zos.close();
                }
            } catch (IOException e) {
                logger.error("流关闭异常" + e.getMessage());
            }
            try {
                if (null != bis) {
                    bis.close();
                }
            } catch (IOException e) {
                logger.error("流关闭异常" + e.getMessage());
            }
            try {
                if (null != inputStream) {
                    inputStream.close();
                }
            } catch (IOException e) {
                logger.error("流关闭异常" + e.getMessage());
            }
        }
    }

    /**
     * 删除文件
     * @Title: FileDelete
     * @author: WangBinBin
     * @param fileId 文件ID
     * @return 成功提示
     */
    @RequestMapping("/delete")
    public Object fileDelete(@RequestParam String fileId) {
        String fId = fileId.replace("[\"", "").replace("\"]", "");
        Map<String, String> fileMap = this.attachmentManagerService.findById(fId);
        String fileName = fileMap.get("NAME");
        String id = fileMap.get("ID");
        String fileSavePath = this.attachmentManagerService.finddirById(id);
        String prefix = fileName.substring(fileName.lastIndexOf("."));
        File file = new File(fileSavePath.replace("\\", "") + "/" + id + prefix);
        if (file.exists() && file.isFile()) {
            file.delete();
        }
        this.attachmentManagerService.deleteFile(fId);
        return BaseController.SUCCESS_TIP;
    }

    /**
     * 附件信息列表查询
     * @Title: testPage
     * @author: WangBinBin
     * @param request 请求
     * @return 页面json
     */
    @RequestMapping("/getAttachmentList")
    @ResponseBody
    public JSONObject testPage(HttpServletRequest request) {
        String pageName = super.getPara("curr");
        String limitName = super.getPara("nums");
        String attachmentName = super.getPara("attachmentName");
        String startDate = super.getPara("dateStart");
        String endDate = super.getPara("dateEnd");
        if (ToolUtil.isNotEmpty(attachmentName)) {
            attachmentName = attachmentName.replaceAll("%", "\\\\%").replaceAll("_", "\\\\_");
        }
        Page page = new Page(Integer.valueOf(pageName), Integer.valueOf(limitName));
        page = this.attachmentManagerService.getPageList(page, attachmentName, startDate, endDate);
        JSONObject json = new JSONObject();
        json.put("data", page.getRecords());
        json.put("code", "0");
        json.put("msg", "");
        json.put("count", page.getTotal());
        return json;

    }

    /**
     * 显示图片
     * @Title: showImage
     * @author: WangBinBin
     * @param response 响应
     */
    @RequestMapping(value = "/showPicture")
    public void showImage(HttpServletResponse response) {
        response.setContentType("text/html; charset=UTF-8");
        response.setContentType("image/jpeg");
        String id = super.getPara("image");

        AttachmentManagerModel image = attachmentManagerService.selectById(id);

        String fileName = image.getAttachmentName();
        String fileId = image.getId();
        String fileSavePath = image.getAttachmentDir();
        String suffix = fileName.substring(fileName.lastIndexOf("."));

        String absolutePath = fileSavePath.replace("\\", "") + "/" + fileId + suffix;
        FileInputStream fis = null;
        OutputStream os = null;
        try {
            fis = new FileInputStream(absolutePath);
            os = response.getOutputStream();
            byte[] byt = new byte[fis.available()];
            fis.read(byt);
            os.write(byt);
            os.flush();
        } catch (IOException e) {
            throw new HussarException(BizExceptionEnum.SHOWIMG_ERROR);
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
            }catch (IOException e){
                logger.error("流关闭异常" + e.getMessage());
            }
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                logger.error("流关闭异常" + e.getMessage());
            }
        }

    }

}
