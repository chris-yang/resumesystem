package com.beyondsoft.mapper;

import com.beyondsoft.entity.SysPermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.beyondsoft.vo.SysPermissionVo;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 功能菜单表 Mapper 接口
 * </p>
 *
 * @author kun
 * @since 2019-11-07
 */
@Repository
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    @Select("select * from sys_permission where parent_id=#{parentId} and del_flag=#{delFlag}" )
     List<SysPermissionVo> getSysPermissionVo(Map<String,Object> map);

    /**
     * 根据id获得SysPermissionVo
     */
     SysPermissionVo getSysPermissionVoById(Map<String,Object> map);

    /**
     *根据id获得SysPermissionVo根结点信息
     */
    @Select("select * from sys_permission where id=#{id} and del_flag=#{delFlag}")
    SysPermissionVo getRootSysPermissionVoById(Map<String,Object> map);
    /**根据roleCode获得当前的菜单信息**/
    List<SysPermissionVo> getSysPermissionVoMenuByRoleCode(Map<String,Object> map);

    /**获得新增时的结点的排序数值**/
    @Select("select max(sort) from sys_permission where del_flag = 'N' and parent_id=#{pid}")
    Integer getAddSort(Integer pid);
}
