package com.beyondsoft.vo;

import com.beyondsoft.entity.SysUserRole;
import lombok.Data;

@Data
public class SysUserRoleVo extends SysUserRole {
    private String realName;
    private String roleCnName;
    private String roleCode;
    private String roleEnName;
}
