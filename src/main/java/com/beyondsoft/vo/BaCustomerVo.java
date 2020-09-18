package com.beyondsoft.vo;

import com.beyondsoft.entity.BaCustomer;
import lombok.Data;

/**
 * @description 客户vo
 *
 * @auth kun
 * @date 2019-11-20
 */
@Data
public class BaCustomerVo extends BaCustomer {
    private Integer deptId;
    private String parentName;
    private String chiefName;
    private Integer[] ids;
    private Integer receiver; //接收人
}
