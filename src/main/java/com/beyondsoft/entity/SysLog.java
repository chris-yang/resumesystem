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
 * 系统日志
 * </p>
 *
 * @author kun
 * @since 2020-04-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysLog extends Model<SysLog> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 操作用户
     */
    private String userName;

    /**
     * 操作类型
     */
    private Integer logType;

    /**
     * 模块名
     */
    private String module;

    /**
     * 方法名
     */
    private String method;

    /**
     * 请求IP
     */
    private String ip;

    /**
     * 执行描述
     */
    private String comment;

    /**
     * 请求URL
     */
    private String url;

    /**
     * 执行时间 
     */
    private Date createTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
