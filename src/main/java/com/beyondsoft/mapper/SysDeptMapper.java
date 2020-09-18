package com.beyondsoft.mapper;

import com.beyondsoft.entity.SysDept;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.beyondsoft.vo.SysDeptRoleUserVo;
import com.beyondsoft.vo.SysDeptVo;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 部门表 Mapper 接口
 * </p>
 *
 * @author kun
 * @since 2019-11-07
 */
@Repository
public interface SysDeptMapper extends BaseMapper<SysDept> {
    /**根据id获得SysPermissionVo**/
    SysDeptVo getSysDeptVoById(Map<String,Object> map);
    /**根据id获得SysPermissionVo根结点信息**/
    @Select("select * from sys_dept where dept_code=#{rootCode} and del_flag=#{delFlag} and enabled=#{enabled}")
    SysDeptVo getRootSysDeptVoById(Map<String,Object> map);
    /**根据部门id下的角色获得用户**/
    List<SysDeptRoleUserVo> getDeptRoleUsers(Map<String,Object> map);
    /**根据客户id获得所在的部门**/
    SysDept getDeptByCustomerId(Integer customerId);
    /**根据负责人查询负责的部门**/
    @Select("select * from sys_dept where email=#{email} and del_flag=#{delFlag} and enabled=#{enabled}")
    List<SysDept> isSettleChief(Map<String, Object> map);
    /**添加时自动获取当前节点的默认排序值**/
    @Select("select max(sort) from sys_dept where parent_id=#{pid} and del_flag='N' and enabled='Y'")
    Integer getSortValue(Integer pid);
}
