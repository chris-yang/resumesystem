package com.beyondsoft.vo;

import com.beyondsoft.entity.SysLog;
import lombok.Data;

@Data
public class SysLogVo extends SysLog {
    private String logTypeContent;
}
