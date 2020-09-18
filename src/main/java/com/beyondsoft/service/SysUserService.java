package com.beyondsoft.service;

import com.beyondsoft.bo.PageData;
import com.beyondsoft.co.UserCo;
import com.beyondsoft.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.beyondsoft.utils.Result;
import com.beyondsoft.vo.SysUserVo;
import com.beyondsoft.vo.UserStopVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  @description 用户表 服务类
 * </p>
 *
 * @author admin
 * @since 2019-11-07
 */
public interface SysUserService extends IService<SysUser> {
    /** 查询用户列表 **/
    PageData getUserList(UserCo userCo);
    /** 根据邮箱查询用户 */
    SysUser findUserByEmail(String email);
    /** 新增用户 */
    Result saveUser(SysUserVo userVo);
    /**  修改用户*/
    Result updateUser(SysUserVo user);
    /** 删除用户：标志位更新为Y */
    Result deleteUser(Integer id);
    /** 重置默认密码 */
    Result updateUserDefaultPassword(Integer id);
    /** 启用 */
    Result updateUserStart(Integer id);
    /** 根据权限ID查询用户 */
    Result getUserSelectByRoleDept(Integer roleId, Integer userId, Integer deptId);
    /** 停用用户信息 */
    SysUserVo getUserStop(Integer id);
    /** 根据部门查询客户经理下拉列表 */
    Result getAmSelect(Map<String, Object> map);
    /** 助理下拉框 */
    Result getAssistantSelect(Map<String, Object> map);
    /** 结算负责人下拉框 */
    Result getSettlementSelect(Map<String, Object> map);
    /** 根据部门和角色查询客户经理 */
    List<SysUser> getAmListByDeptAndRole(Map<String, Object> map);
    /** 根据deptId和roleCode获得当前部门下的AM角色的用户 **/
    Result getUserByDeptIdAndRoleCode(Integer deptId);
    /**用户停用**/
    Result userStop(UserStopVo userStopVo);
    /** 根据角色CODE查询用户列表 */
    List<SysUser> getUsersByRoleCode(String roleCode);
    /**根据用户id和角色获取当前用户的同部门的用户**/
    public Result getUsersByDeptIdAndRoleCode(Integer userId,String roleCode);
    /** 根据角色CODE查询用户列表 排除自己 */
    List<SysUser> getUsersByRoleCodeExcludeSelf(String roleCodeDm, Integer selfId);
}
