package com.beyondsoft.vo;

import com.beyondsoft.entity.SysDept;
import lombok.Data;

import java.util.List;
/**
 * @description 部门vo
 *
 * @auth kun
 * @date 2019-11-20
 */
@Data
public class SysDeptVo extends SysDept {
    private String parentContent;
    private String checkArr;
    private String title;
    private List<Integer> roles ;
    private boolean open;
    private boolean checked;

}
