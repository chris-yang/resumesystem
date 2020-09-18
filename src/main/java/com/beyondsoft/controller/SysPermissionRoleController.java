package com.beyondsoft.controller;


import com.beyondsoft.aspect.log.SysOperLogAnno;
import com.beyondsoft.service.SysPermissionRoleService;
import com.beyondsoft.utils.Result;
import com.beyondsoft.vo.SysRoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 菜单角色关联表 前端控制器
 * </p>
 *
 * @author kun
 * @since 2019-11-26
 */
@RestController
@RequestMapping("/sys-permission-role")
public class SysPermissionRoleController {

    @Autowired
    private SysPermissionRoleService sysPermissionRoleService;

    /**
     * 更新角色信息
     * @param permissions
     * @param roleId
     * @return
     */
    @SysOperLogAnno(logType=200,module="菜单角色管理 ",methods="更新角色信息")
    @PutMapping("/update")
    public Result update(Integer[] permissions,Integer roleId){
        SysRoleVo sysRoleVo = new SysRoleVo();
        sysRoleVo.setId(roleId);
        sysRoleVo.setSysPermissions(permissions);
       return sysPermissionRoleService.savePermissionRoles(sysRoleVo);
    }

    /**
     * 根据roleId获得对应的permission
     * @param roleId
     * @return
     */
    @GetMapping("/checked/{roleId}")
    public Result getCheckedPermissionValueByRoleId(@PathVariable(value="roleId") Integer roleId){
        return sysPermissionRoleService.getCheckedPermissionValueByRoleId(roleId);
    }
}
