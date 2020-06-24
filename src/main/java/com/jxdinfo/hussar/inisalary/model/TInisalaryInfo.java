package com.jxdinfo.hussar.inisalary.model;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 初始工资信息
 * </p>
 *
 * @author yxx
 * @since 2020-06-16
 */
@TableName("t_inisalary_info")
public class TInisalaryInfo extends Model<TInisalaryInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 员工ID
     */
    @TableId(value = "STAFF_ID")
    private Integer staffId;
    /**
     * 员工姓名
     */
    @TableField("STAFF_NAME")
    private String staffName;
    /**
     * 部门ID
     */
    @TableField("DEPARTMENT_ID")
    private Integer departmentId;
    /**
     * 部门
     */
    @TableField(exist = false)
    private TDepartment department;
    /**
     * 工作年限
     */
    @TableField("YEARS")
    private Integer years;
    /**
     * 工资等级
     */
    @TableField("LEVEL")
    private Integer level;
    /**
     * 初始工资
     */
    @TableField("INITIAL_SALARY")
    private Integer initialSalary;

    public TInisalaryInfo(){}

    public TInisalaryInfo(Integer staffId,String staffName,Integer departmentId,Integer years,Integer level,Integer initialSalary){
        this.staffId=staffId;
        this.staffName=staffName;
        this.departmentId=departmentId;
        this.years=years;
        this.level=level;
        this.initialSalary=initialSalary;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public TDepartment getDepartment() {
        return department;
    }

    public void setDepartment(TDepartment department) {
        this.department = department;
    }


    public Integer getYears() {
        return years;
    }

    public void setYears(Integer years) {
        this.years = years;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getInitialSalary() {
        return initialSalary;
    }

    public void setInitialSalary(Integer initialSalary) {
        this.initialSalary = initialSalary;
    }

    @Override
    protected Serializable pkVal() {
        return this.staffId;
    }

    @Override
    public String toString() {
        return "TInisalaryInfo{" +
        "staffId=" + staffId +
        ", staffName=" + staffName +
        ", departmentId=" + departmentId +
        ", years=" + years +
        ", level=" + level +
        ", initialSalary=" + initialSalary +
        "}";
    }
}
