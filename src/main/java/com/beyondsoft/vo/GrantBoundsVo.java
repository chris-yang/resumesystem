package com.beyondsoft.vo;

import com.beyondsoft.entity.GrantBounds;
import lombok.Data;

@Data
public class GrantBoundsVo extends GrantBounds {

    private String employeeNo;
    private String name;
    private String mobile;
    private String email;
    private Integer totalBounds;
    private Integer sendBounds;
    private Integer applyBounds;
}
