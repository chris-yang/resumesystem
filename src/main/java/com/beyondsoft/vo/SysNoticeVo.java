package com.beyondsoft.vo;

import com.beyondsoft.entity.SysNotice;
import lombok.Data;

@Data
public class SysNoticeVo extends SysNotice {
    private Integer[] noticeTo;
}
