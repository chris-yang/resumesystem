package com.beyondsoft.service;

import com.beyondsoft.entity.SysUserRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.beyondsoft.vo.SysRoleVo;
import com.beyondsoft.vo.SysUserRoleVo;
import com.beyondsoft.vo.SysUserVo;

import java.util.List;

/**
 * <p>
 * 用户角色表 服务类
 * </p>
 *
 * @author admin
 * @since 2019-11-14
 */
public interface SysUserRoleService extends IService<SysUserRole> {
    /** 根据用户ID取得角色 */
    SysUserRole getRoleByUserId(Integer userId);
    /**批量插入用户角色**/
    boolean insertBatchs(SysUserVo userVo);
    /** 根据用户ID取得角色列表 */
    List<SysUserRoleVo> getRolesByUserId(Integer userId);
    /** 根据用户ID取得角色列表 */
    List<SysRoleVo> getUserRoleIds(Integer userId);
}
