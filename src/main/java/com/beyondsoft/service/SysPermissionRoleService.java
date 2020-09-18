package com.beyondsoft.service;

import com.beyondsoft.entity.SysPermission;
import com.beyondsoft.entity.SysPermissionRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.beyondsoft.utils.Result;
import com.beyondsoft.vo.SysRoleVo;

import java.util.List;

/**
 * <p>
 * 菜单角色关联表 服务类
 * </p>
 *
 * @author kun
 * @since 2019-11-26
 */
public interface SysPermissionRoleService extends IService<SysPermissionRole> {
    /**更新角色信息**/
    Result savePermissionRoles(SysRoleVo sysRoleVo);
    /**根据roleId获得对应的permission**/
    Result getCheckedPermissionValueByRoleId(Integer roleId);
    /**根据roleId获得对应的permission**/
    List<SysPermission> getPermissionByRoleId(Integer roleId);
}
