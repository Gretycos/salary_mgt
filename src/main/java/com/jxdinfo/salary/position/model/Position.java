package com.jxdinfo.salary.position.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.jxdinfo.salary.staff.model.Staff;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 职位表
 * </p>
 *
 * @author hyc
 * @since 2020-06-17
 */
@TableName("t_position")
public class Position extends Model<Position> {

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
    private List<Staff> staffList;

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

    public List<Staff> getStaffList() {
        return staffList;
    }

    public void setStaffList(List<Staff> staffList) {
        this.staffList = staffList;
    }

    @Override
    protected Serializable pkVal() {
        return this.positionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position)) return false;
        Position position = (Position) o;
        return Objects.equals(getPositionId(), position.getPositionId()) &&
                Objects.equals(getPositionName(), position.getPositionName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPositionId(), getPositionName());
    }

    @Override
    public String toString() {
        return "Position{" +
        "positionId=" + positionId +
        ", positionName=" + positionName +
        "}";
    }
}
