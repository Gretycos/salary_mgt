package com.jxdinfo.salary.entry.model;

import java.sql.Timestamp;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.jxdinfo.salary.department.model.Department;
import com.jxdinfo.salary.position.model.Position;
import com.jxdinfo.salary.staff.model.Staff;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 入职记录表
 * </p>
 *
 * @author zxn
 * @since 2020-06-19
 */
@TableName("t_entry_log")
public class EntryLog extends Model<EntryLog> {

    private static final long serialVersionUID = 1L;

    /**
     * 操作ID
     */
    @TableId("OPERATION_ID")
    private String operationId;
    /**
     * 操作员
     */
    @TableField(exist = false)
    private Staff operator;
    /**
     * 入职人员
     */
    @TableField(exist = false)
    private Staff entrant;
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

    public Staff getOperator() {
        return operator;
    }

    public void setOperator(Staff operator) {
        this.operator = operator;
    }

    public Staff getEntrant() {
        return entrant;
    }

    public void setEntrant(Staff entrant) {
        this.entrant = entrant;
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
        return "EntryLog{" +
        "operationId=" + operationId +
        ", operator=" + operator +
        ", entrant=" + entrant +
        ", operationTime=" + operationTime +
        "}";
    }
}
