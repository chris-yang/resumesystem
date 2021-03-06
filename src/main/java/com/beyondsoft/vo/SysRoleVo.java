package com.beyondsoft.vo;

import com.beyondsoft.entity.SysRole;
import lombok.Data;
/**
 * @description 角色vo
 *
 * @auth kun
 * @date 2019-11-20
 */
@Data
public class SysRoleVo extends SysRole {
    private Integer[] sysPermissions;
    private boolean isChecked;
    private Integer detpId;
}
