package com.beyondsoft.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.beyondsoft.entity.SysUserRole;
import com.beyondsoft.mapper.SysRoleMapper;
import com.beyondsoft.mapper.SysUserRoleMapper;
import com.beyondsoft.service.SysUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beyondsoft.utils.Const;
import com.beyondsoft.vo.SysRoleVo;
import com.beyondsoft.vo.SysUserRoleVo;
import com.beyondsoft.vo.SysUserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户角色表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2019-11-14
 */
@Service
@Transactional
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    private static Logger logger = LoggerFactory.getLogger(SysUserRoleServiceImpl.class);

    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Override
    public SysUserRole getRoleByUserId(Integer userId) {
        logger.info("根据员工ID取得角色SysUserRoleServiceImpl： getRoleByuserId");
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<SysUserRole>();
        queryWrapper.eq("user_id", userId);
        SysUserRole sur = this.baseMapper.selectOne(queryWrapper);
        return sur;
    }

    /**
     * 批量插入用户角色
     * @param userVo
     * @return
     */
    @Override
    public boolean insertBatchs(SysUserVo userVo) {
        List<Integer> roles = userVo.getRoles();
        List<SysUserRole> sysUserRoles = new ArrayList<>();
        for(Integer role : roles) {
            if(role!=null) {
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setUserId(userVo.getId());
                sysUserRole.setRoleId(role);
                sysUserRoles.add(sysUserRole);
            }
        }
        return this.saveBatch(sysUserRoles);
    }

    /**
     * 根据用户ID取得角色列表
     * @param userId
     * @return
     */
    @Override
    public List<SysUserRoleVo> getRolesByUserId(Integer userId) {
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("enabled", Const.ENABLED_Y);
        return this.baseMapper.getRolesByUserId(map);
    }

    /**
     * 根据用户ID取得角色列表
     * @param userId
     * @return
     */
    @Override
    public List<SysRoleVo> getUserRoleIds(Integer userId) {
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("enabled", Const.ENABLED_Y);
        List<SysUserRoleVo> sysUserRoleVos = this.baseMapper.getRolesByUserId(map);

        Map<String,Object> roleMap = new HashMap<>();
        roleMap.put("delFlag", Const.DEL_FLAG_N);
        List<SysRoleVo> sysRoleVos = sysRoleMapper.getRoleVo(roleMap);
        for(SysUserRoleVo sysUserRoleVo: sysUserRoleVos) {
            for(SysRoleVo sysRoleVo: sysRoleVos) {
                if(sysUserRoleVo.getRoleId()==sysRoleVo.getId()){
                    sysRoleVo.setChecked(true);
                    break;
                }
            }
        }
        return sysRoleVos;
    }
}
