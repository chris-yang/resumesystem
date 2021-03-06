package com.beyondsoft.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beyondsoft.bo.PageData;
import com.beyondsoft.co.RoleCo;
import com.beyondsoft.entity.SysRole;
import com.beyondsoft.mapper.SysRoleMapper;
import com.beyondsoft.service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beyondsoft.utils.Const;
import com.beyondsoft.utils.HttpResultEnum;
import com.beyondsoft.utils.Result;
import com.beyondsoft.vo.SysRoleVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author kun
 * @since 2019-11-07
 */
@Service
@Transactional
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    /**
     * 加载显示角色列表
     * @param roleCo
     * @return
     */
    @Override
    public PageData listPageData(RoleCo roleCo) {
        IPage<SysRole> page = new Page<>(roleCo.getPage(),roleCo.getLimit());
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(roleCo.getKeyword()),"role_cn_name",roleCo.getKeyword());
        queryWrapper.eq("del_flag",Const.DEL_FLAG_N);
        this.baseMapper.selectPage(page,queryWrapper);
        return new PageData(page.getTotal(),page.getRecords());
    }

    /**
     * 所有的角色
     * @return
     */
    @Override
    public List<SysRoleVo> getRoleVo() {
        Map<String,Object> map = new HashMap<>();
        map.put("delFlag",Const.DEL_FLAG_N);
        return this.baseMapper.getRoleVo(map);
    }

    /**
     * 根据部门id获得对应的选中的角色状态
     * @param deptId
     * @return
     */
    @Override
    public List<SysRoleVo> getRoleVoByDeptId(Integer deptId) {
        Map<String,Object> map = new HashMap<>();
        map.put("deptId",deptId);
        map.put("roleCode",Const.ROLE_CODE_ADMIN);
        map.put("delFlag",Const.DEL_FLAG_N);
        List<SysRoleVo> sysRoleVos = this.baseMapper.getRoleVoExclude(map);
        List<SysRoleVo> sysRoleCheckedVos = this.baseMapper.getRoleVoByDeptId(map);
        for(SysRoleVo sysCheckedRoleVo:sysRoleCheckedVos) {
            for(SysRoleVo sysRoleVo:sysRoleVos) {
                if(sysCheckedRoleVo.getId().equals(sysRoleVo.getId())){
                    sysRoleVo.setChecked(true);
                    break;
                }
            }
        }
        return sysRoleVos;
    }

    /**
     * 查询除roleCode之外的角色
     * @param roleCode
     * @return
     */
    @Override
    public List<SysRoleVo> getRoleVoExclude(String roleCode) {
        Map<String,Object> map = new HashMap<>();
        map.put("roleCode",roleCode);
        map.put("delFlag",Const.DEL_FLAG_N);
        return this.baseMapper.getRoleVoExclude(map);
    }

    /**
     * 根据用户id 查询角色信息
     * @param userId
     * @return
     */
    @Override
    public SysRole findRoleByUserId(Integer userId) {
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("delFlag",Const.DEL_FLAG_N);
        return this.baseMapper.getSysRoleByUserId(map);
    }

    @Override
    public Result getRolesByDeptId(Integer deptId) {
        Map<String,Object> map = new HashMap<>();
        map.put("deptId",deptId);
        map.put("delFlag",Const.DEL_FLAG_N);
        Result r = new Result(HttpResultEnum.CODE_200.getCode(), HttpResultEnum.CODE_200.getMessage());
        r.setData(this.baseMapper.getRolesByDeptId(map));
        return r;
    }

    /**
     * 保存角色
     * @param sysRoleVo
     * @return
     */
    @Override
    public Result saveRole(SysRoleVo sysRoleVo) {
        Result r =  new Result(HttpResultEnum.ADD_CODE_500.getCode(),HttpResultEnum.ADD_CODE_500.getMessage());
        SysRole sysRole = new SysRole();
        sysRole.setRoleCnName(sysRoleVo.getRoleCnName());
        sysRole.setRoleEnName(sysRoleVo.getRoleEnName());
        sysRole.setRoleCode(sysRoleVo.getRoleCode());
        sysRole.setRemark(sysRoleVo.getRemark());
        sysRole.setCreateUserId(sysRoleVo.getCreateUserId());
        sysRole.setCreateTime(new Date());
        sysRole.setDelFlag(Const.DEL_FLAG_N);
        try {
            Integer ret = this.baseMapper.insert(sysRole);
            if (ret != null & ret > 0) {
                r.setCode(HttpResultEnum.ADD_CODE_200.getCode());
                r.setMsg(HttpResultEnum.ADD_CODE_200.getMessage());
                r.setData(sysRole);
            }
        }catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return  r;
    }

    /**
     *  更新角色内容
     * @param sysRoleVo
     * @return
     */
    @Override
    public Result updateRole(SysRoleVo sysRoleVo) {
        Result r = new Result(HttpResultEnum.EDIT_CODE_500.getCode(),HttpResultEnum.EDIT_CODE_500.getMessage());
        SysRole sysRole = new SysRole();
        sysRole.setRoleCnName(sysRoleVo.getRoleCnName());
        sysRole.setRoleEnName(sysRoleVo.getRoleEnName());
        sysRole.setRoleCode(sysRoleVo.getRoleCode());
        sysRole.setRemark(sysRoleVo.getRemark());
        sysRole.setDelFlag(Const.DEL_FLAG_N);
        UpdateWrapper<SysRole> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",sysRoleVo.getId());
        try {
            Integer ret = this.baseMapper.update(sysRole, updateWrapper);
            if (ret != null && ret > 0) {
                r.setCode(HttpResultEnum.EDIT_CODE_200.getCode());
                r.setMsg(HttpResultEnum.EDIT_CODE_200.getMessage());
            }
        }catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return r;
    }

    /**
     * 删除角色
     * @param id
     * @return
     */
    @Override
    public Result deleteRole(Integer id) {
        Result r = new Result(HttpResultEnum.DEL_CODE_500.getCode(),HttpResultEnum.DEL_CODE_500.getMessage());
        try {
            UpdateWrapper<SysRole> updateWrapper = new UpdateWrapper<>();
            updateWrapper.set("del_flag", Const.DEL_FLAG_D);
            updateWrapper.eq("id", id);
            SysRole sysRole = this.baseMapper.selectById(id);
            Integer ret = this.baseMapper.update(sysRole, updateWrapper);
            if (ret != null && ret > 0) {
                r.setCode(HttpResultEnum.DEL_CODE_200.getCode());
                r.setMsg(HttpResultEnum.DEL_CODE_200.getMessage());
            }
        }catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return r;
    }

    /**
     * 检查角色role_code是否存在
     * @param roleCode
     * @return
     */
    @Override
    public Result roleCodeIsExist(String roleCode) {
        Result r = new Result();
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_code",roleCode);
        queryWrapper.eq("del_flag",Const.DEL_FLAG_N);
        Integer count=this.baseMapper.selectCount(queryWrapper);
        if(count!=null && count>0){
            r.setCode(HttpResultEnum.ROLE_CODE_1.getCode());
            r.setMsg(HttpResultEnum.ROLE_CODE_1.getMessage());
        } else{
            r.setCode(HttpResultEnum.ROLE_CODE_0.getCode());
            r.setMsg(HttpResultEnum.ROLE_CODE_0.getMessage());

        }
        return r;
    }


}
