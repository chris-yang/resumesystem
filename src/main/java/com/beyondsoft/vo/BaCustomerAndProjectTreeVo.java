package com.beyondsoft.vo;

import lombok.Data;

@Data
public class BaCustomerAndProjectTreeVo {
    private String id;
    private String title;
    private String parentId;
    private String projectCode;
    private String checkArr;
    private boolean isLeaf;
}
