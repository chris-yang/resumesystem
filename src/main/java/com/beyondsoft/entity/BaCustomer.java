package com.beyondsoft.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 客户表
 * </p>
 *
 * @author kun
 * @since 2019-11-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BaCustomer extends Model<BaCustomer> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String customerName;

    private String customerCode;

    private Integer chief;

    private Integer pid;

    private Integer level;

    private String enabled;

    private Integer createUserId;

    private Date createTime;

    private String delFlag;

    private Integer sort;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
