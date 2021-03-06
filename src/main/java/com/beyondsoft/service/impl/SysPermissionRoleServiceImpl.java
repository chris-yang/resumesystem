package com.beyondsoft.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beyondsoft.entity.SysPermission;
import com.beyondsoft.entity.SysPermissionRole;
import com.beyondsoft.mapper.SysPermissionRoleMapper;
import com.beyondsoft.service.SysPermissionRoleService;
import com.beyondsoft.utils.HttpResultEnum;
import com.beyondsoft.utils.Result;
import com.beyondsoft.vo.SysRoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 菜单角色关联表 服务实现类
 * </p>
 *
 * @author kun
 * @since 2019-11-26
 */
@Service
@Transactional
public class SysPermissionRoleServiceImpl extends ServiceImpl<SysPermissionRoleMapper, SysPermissionRole> implements SysPermissionRoleService {

    @Autowired
    private SysPermissionRoleMapper sysPermissionRoleMapper;

    /**
     * 更新角色信息
     * @param sysRoleVo
     * @return
     */
    @Override
    public Result savePermissionRoles(SysRoleVo sysRoleVo) {
        Result r =  new Result(HttpResultEnum.ADD_CODE_500.getCode(),HttpResultEnum.ADD_CODE_500.getMessage());
        List<SysPermissionRole> sysPermissionRoles = new ArrayList<>();
        try{
            //删除
            QueryWrapper<SysPermissionRole> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("role_id",sysRoleVo.getId());
            this.remove(queryWrapper);
            //添加
            for(Integer id: sysRoleVo.getSysPermissions()) {
                if(id!=null) {
                    SysPermissionRole sysPermissionRole = new SysPermissionRole();
                    sysPermissionRole.setPermissionId(id);
                    sysPermissionRole.setRoleId(sysRoleVo.getId());
                    sysPermissionRoles.add(sysPermissionRole);
                }
            }
            boolean ret=this.saveBatch(sysPermissionRoles);
            if(ret) {
                r.setCode(HttpResultEnum.ADD_CODE_200.getCode());
                r.setMsg(HttpResultEnum.ADD_CODE_200.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return r;
    }

    /**
     * 根据roleId获得对应的permission
     * @param roleId
     * @return
     */
    @Override
    public Result getCheckedPermissionValueByRoleId(Integer roleId) {
        Result r = new Result(HttpResultEnum.CODE_1.getCode(),HttpResultEnum.CODE_1.getMessage());
        QueryWrapper<SysPermissionRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id",roleId);
        List<SysPermissionRole> sysPermissionRoles = this.baseMapper.selectList(queryWrapper);
        String permissionIds="";
        for(SysPermissionRole sysPermissionRole : sysPermissionRoles) {
            permissionIds += sysPermissionRole.getPermissionId()+",";
        }
        if(permissionIds.length()>0) {
            permissionIds = permissionIds.substring(0,permissionIds.length()-1);
            r.setCode(HttpResultEnum.CODE_0.getCode());
            r.setMsg(HttpResultEnum.CODE_0.getMessage());
            r.setData(permissionIds);
        }
        return r;
    }

    /**
     * 根据roleId获得对应的permission
     * @param roleId
     * @return
     */
    @Override
    public List<SysPermission> getPermissionByRoleId(Integer roleId) {
        return this.baseMapper.getPermissionByRoleId(roleId);
    }
}
