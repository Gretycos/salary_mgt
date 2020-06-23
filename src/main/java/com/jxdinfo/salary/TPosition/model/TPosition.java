package com.jxdinfo.salary.TPosition.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.jxdinfo.salary.OutSalary.model.MonthlySalary;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 职位表
 * </p>
 *
 * @author hyc
 * @since 2020-06-17
 */
@TableName("t_position")
public class TPosition extends Model<TPosition> {

    private static final long serialVersionUID = 1L;

    /**
     * 职位ID
     */
    @TableId("POSITION_ID")
    private Integer positionId;
    /**
     * 职位名称
     */
    @TableField("POSITION_NAME")
    private String positionName;
    /**
     * 职位下的员工列表
     */
    @TableField(exist = false)
    private List<MonthlySalary> MonthlySalaryList;

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public List<MonthlySalary> getMonthlySalaryList() {
        return MonthlySalaryList;
    }

    public void setMonthlySalaryList(List<MonthlySalary> MonthlySalaryList) {
        this.MonthlySalaryList = MonthlySalaryList;
    }

    @Override
    protected Serializable pkVal() {
        return this.positionId;
    }

    @Override
    public String toString() {
        return "Position{" +
        "positionId=" + positionId +
        ", positionName=" + positionName +
        "}";
    }
}
