package com.jxdinfo.hussar.inisalary.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.sql.Timestamp;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author yxx
 * @since 2020-06-23
 */
@TableName("t_inisalary_infor_log")
public class TInisalaryInforLog extends Model<TInisalaryInforLog> {

    private static final long serialVersionUID = 1L;

    /**
     * 员工ID
     */
    @TableId(value = "STAFF_ID")
    private Integer staffId;
    /**
     * 姓名
     */
    @TableField("STAFF_NAME")
    private String staffName;
    /**
     * 部门ID
     */
    @TableField("DEPARTMENT_ID")
    private Integer departmentId;
    /**
     * 等级
     */
    @TableField("LEVEL")
    private Integer level;
    /**
     * 初始工资
     */
    @TableField("INITIAL_SALARY")
    private Integer initialSalary;
    /**
     * 时间ID
     */
    @TableField("INFORMATION_TIME")
    private Timestamp informationTime;
    /**
     * 有效性
     */
    @TableField("EFFECTIVENESS")
    private Integer effectiveness;

    public TInisalaryInforLog(){}

    public TInisalaryInforLog(Integer staffId,String staffName,Integer departmentId,Integer level,Integer initialSalary,Timestamp informationTime
            ,Integer effectiveness){
        this.staffId=staffId;
        this.staffName=staffName;
        this.departmentId=departmentId;
        this.level=level;
        this.initialSalary=initialSalary;
        this.informationTime=informationTime;
        this.effectiveness=effectiveness;
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

    public Timestamp getInformationTime() {
        return informationTime;
    }

    public void setInformationTime(Timestamp informationTime) {
        this.informationTime = informationTime;
    }

    public Integer getEffectiveness() {
        return effectiveness;
    }

    public void setEffectiveness(Integer effectiveness) {
        this.effectiveness = effectiveness;
    }

    @Override
    protected Serializable pkVal() {
        return this.staffId;
    }

    @Override
    public String toString() {
        return "TInisalaryInforLog{" +
        "staffId=" + staffId +
        ", staffName=" + staffName +
        ", departmentId=" + departmentId +
        ", level=" + level +
        ", initialSalary=" + initialSalary +
        ", informationTime=" + informationTime +
        ", effectiveness=" + effectiveness +
        "}";
    }
}
