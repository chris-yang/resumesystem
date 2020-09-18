package com.beyondsoft.mapper;

import com.beyondsoft.entity.SysUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.beyondsoft.vo.SysUserRoleVo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户角色表 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2019-11-14
 */
@Repository
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    Integer insertBatchs(List<SysUserRole> sysUserRoles);

    /**
     * 根据用户ID取得角色列表
     * @param map
     * @return
     */
    List<SysUserRoleVo> getRolesByUserId(Map<String,Object> map);
}
