package com.beyondsoft.vo;

import lombok.Data;

@Data
public class SettleProcessVo {
    private String pgId;
    private String projectName;
    private Integer counts;
    private double process;
    private Integer sCount;
    private Integer wCount;
}
