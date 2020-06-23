package com.jxdinfo.salary.OutSalary.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import com.jxdinfo.salary.OutSalary.controller.BaseFrontController;
import com.jxdinfo.salary.util.ExcelFormatUtil;

import com.jxdinfo.salary.OutSalary.model.MonthlySalary;
import com.jxdinfo.salary.OutSalary.dao.MonthlySalaryMapper;
import com.jxdinfo.salary.OutSalary.service.IMonthlySalaryService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 员工每月工资表 服务实现类
 * </p>
 *
 * @author 
 * @since 2020-06-17
 */
@Service
public class MonthlySalaryServiceImpl extends ServiceImpl<MonthlySalaryMapper, MonthlySalary> implements IMonthlySalaryService {
    Logger logger = LoggerFactory.getLogger(MonthlySalaryServiceImpl.class);

    @Override
    public ResponseEntity<byte[]> exportExcel(HttpServletRequest request, HttpServletResponse response,List<MonthlySalary> list1) {
        try {
            logger.info(">>>>>>>>>>开始导出excel>>>>>>>>>>");

            // 造几条数据
            List<MonthlySalary> list = list1;
           /* List<MonthlySalary> list = new ArrayList<>();
            list.add(new MonthlySalary(2017003012, "xiaoming", 10, 1, 2000, 30,50,300));
            list.add(new MonthlySalary(2015003020, "jack", 10, 1,2000, 30,50,200));
            list.add(new MonthlySalary(2017005015, "tow", 11, 1, 2000, 30,50,250));
            list.add(new MonthlySalary(2016063002, "tom", 12, 0, 2000, 30,50,300));*/
            BaseFrontController baseFrontController = new BaseFrontController();
            return baseFrontController.buildResponseEntity(export((List<MonthlySalary>) list), "工资表.xlsx");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(">>>>>>>>>>导出excel 异常，原因为：" + e.getMessage());
        }
        return null;
    }

    private InputStream export(List<MonthlySalary> list) {
        logger.info(">>>>>>>>>>>>>>>>>>>>开始进入导出方法>>>>>>>>>>");
        ByteArrayOutputStream output = null;
        InputStream inputStream1 = null;
        SXSSFWorkbook wb = new SXSSFWorkbook(1000);// 保留1000条数据在内存中
        SXSSFSheet sheet = wb.createSheet();
        // 设置报表头样式
        CellStyle header = ExcelFormatUtil.headSytle(wb);// cell样式
        CellStyle content = ExcelFormatUtil.contentStyle(wb);// 报表体样式

        // 每一列字段名
        String[] strs = new String[] { "工号", "姓名", "部门", "职位", "基础工资","工龄工资","五险","奖金"};

        // 字段名所在表格的宽度
        int[] ints = new int[] { 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000 };

        // 设置表头样式
        ExcelFormatUtil.initTitleEX(sheet, header, strs, ints);
        logger.info(">>>>>>>>>>>>>>>>>>>>表头样式设置完成>>>>>>>>>>");

        if (list != null && list.size() > 0) {
            logger.info(">>>>>>>>>>>>>>>>>>>>开始遍历数据组装单元格内容>>>>>>>>>>");
            for (int i = 0; i < list.size(); i++) {
                MonthlySalary monthlysalary = list.get(i);

                SXSSFRow row = sheet.createRow(i + 1);
                int j = 0;

                SXSSFCell cell = row.createCell(j++);
                cell.setCellValue(monthlysalary.getStaffId()); // id
                cell.setCellStyle(content);

                cell = row.createCell(j++);
                cell.setCellValue(monthlysalary.getStaffName()); // name
                cell.setCellStyle(content);

                cell = row.createCell(j++);
                cell.setCellValue(monthlysalary.getDepartment().getDepartmentName()); // d_id
                cell.setCellStyle(content);

                cell = row.createCell(j++);
                cell.setCellValue(monthlysalary.getPosition().getPositionName()); // P_id
                cell.setCellStyle(content);

                cell = row.createCell(j++);
                cell.setCellValue(monthlysalary.getSalaryAmount()); // B_salary
                cell.setCellStyle(content);

                cell = row.createCell(j++);
                cell.setCellValue(monthlysalary.getSalaryOfAge()); // age_bouns
                cell.setCellStyle(content);

                cell = row.createCell(j++);
                cell.setCellValue(monthlysalary.getIahf()); // 五险一金
                cell.setCellStyle(content);

                cell = row.createCell(j++);
                cell.setCellValue(monthlysalary.getAwardAmount()); // award
                cell.setCellStyle(content);


            }
            logger.info(">>>>>>>>>>>>>>>>>>>>结束遍历数据组装单元格内容>>>>>>>>>>");
        }
        try {
            output = new ByteArrayOutputStream();
            wb.write(output);
            inputStream1 = new ByteArrayInputStream(output.toByteArray());
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                if (output != null) {
                    output.close();
                    if (inputStream1 != null)
                        inputStream1.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return inputStream1;
    }
}
