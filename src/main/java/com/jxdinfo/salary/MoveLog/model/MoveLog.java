package com.jxdinfo.salary.MoveLog.model;

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
 * 调动记录表
 * </p>
 *
 * @author zxn
 * @since 2020-06-17
 */
@TableName("t_move_log")
public class MoveLog extends Model<MoveLog> {

    private static final long serialVersionUID = 1L;

    /**
     * 操作ID
     */
    @TableId("OPERATION_ID")
    private String operationId;
    /**
     * 操作员ID
     */
    @TableField("OPERATOR_ID")
    private Integer operatorId;
    /**
     * 被操做人员ID
     */
    @TableField("MOVE_ID")
    private Integer moveId;
    /**
     * 原部门ID
     */
    @TableField("OLD_DEPARTMENT_ID")
    private Integer oldDepartmentId;
    /**
     * 新部门ID
     */
    @TableField("NEW_DEPARTMENT_ID")
    private Integer newDepartmentId;
    /**
     * 原职务ID
     */
    @TableField("OLD_POSITION_ID")
    private Integer oldPositionId;
    /**
     * 新职务ID
     */
    @TableField("NEW_POSITION_ID")
    private Integer newPositionId;
    /**
     * 操作时间
     */
    @TableField("OPERATION_TIME")
    private Timestamp operationTime;


    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public Integer getMoveId() {
        return moveId;
    }

    public void setMoveId(Integer moveId) {
        this.moveId = moveId;
    }

    public Integer getOldDepartmentId() {
        return oldDepartmentId;
    }

    public void setOldDepartmentId(Integer oldDepartmentId) {
        this.oldDepartmentId = oldDepartmentId;
    }

    public Integer getNewDepartmentId() {
        return newDepartmentId;
    }

    public void setNewDepartmentId(Integer newDepartmentId) {
        this.newDepartmentId = newDepartmentId;
    }

    public Integer getOldPositionId() {
        return oldPositionId;
    }

    public void setOldPositionId(Integer oldPositionId) {
        this.oldPositionId = oldPositionId;
    }

    public Integer getNewPositionId() {
        return newPositionId;
    }

    public void setNewPositionId(Integer newPositionId) {
        this.newPositionId = newPositionId;
    }

    public Timestamp getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Timestamp operationTime) {
        this.operationTime = operationTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.operationId;
    }

    @Override
    public String toString() {
        return "MoveLog{" +
        "operationId=" + operationId +
        ", operatorId=" + operatorId +
        ", moveId=" + moveId +
        ", oldDepartmentId=" + oldDepartmentId +
        ", newDepartmentId=" + newDepartmentId +
        ", oldPositionId=" + oldPositionId +
        ", newPositionId=" + newPositionId +
        ", operationTime=" + operationTime +
        "}";
    }
}
