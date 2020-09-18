package com.beyondsoft.vo;

import com.beyondsoft.entity.Demand;
import lombok.Data;

@Data
public class DemandVo extends Demand {


    /**
     * 职位类型
     */
    private String jobCategoryContent;

    /**
     * 职位
     */
    private String jobIdContent;

    /**
     * 需求(职位)性质 全职 兼职 实习
     */
    private String jobStyleContent;

    /**
     * 职位级别
     */
    private String jobLevelContent;

    /**
     * 地区
     */
    private String areaContent;



    /**
     * 学历要求
     */
    private String degreeContent;

    private String workYearsContent;

    /**
     * 性别要求
     */
    private String genderContent;

    /**
     * 开启内推
     */
    private String internalRecommendContent;

    /**
     * 内推状态
     */
    private String internalRecommendStatusContent;

    /**
     * 客户
     */
    private String customerContent;


    /**
     * 需求状态
     */
    private Integer demandStatusContent;


    /**
     * 薪资范围
     */
    private String salaryAreaContent;

    /**
     * 需求类型
     */
    private String demandTypeContent;

    /**
     * 所属用户
     */
    private String realName;
}
