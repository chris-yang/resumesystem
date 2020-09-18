package com.beyondsoft.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author kun
 * @since 2020-09-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Demand extends Model<Demand> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 需求(职位)名称
     */
    private String demandName;

    /**
     * 职位类型
     */
    private String jobCategory;

    /**
     * 职位
     */
    private Integer jobId;

    /**
     * 需求(职位)性质 全职 兼职 实习
     */
    private String jobStyle;

    /**
     * 职位级别
     */
    private String jobLevel;

    /**
     * 地区
     */
    private Integer area;

    /**
     * 岗位信息
     */
    private String postInfo;

    /**
     * 岗位职责
     */
    private String jobInfo;

    /**
     * 学历要求
     */
    private String degree;

    private String workYears;

    /**
     * 性别要求
     */
    private String gender;

    /**
     * 开启内推
     */
    private String internalRecommend;

    /**
     * 内推状态
     */
    private String internalRecommendStatus;

    /**
     * 客户
     */
    private String customerId;
    private Date startTime;
    /**
     * 关闭时间
     */
    private Date closeTime;

    /**
     * 需求状态
     */
    private String demandStatus;

    /**
     * 需求人数
     */
    private Integer personCount;

    /**
     * 薪资范围
     */
    private String salaryArea;

    /**
     * 需求类型
     */
    private String demandType;

    /**
     * 所属用户
     */
    private Integer userId;

    /**
     * 需求开始时间
     */
    private Date createTime;

    private Date updateTime;

    /**
     * 显示顺序
     */
    private Integer jobOrders;

    /**
     * 标志位
     */
    private String delFlag;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
