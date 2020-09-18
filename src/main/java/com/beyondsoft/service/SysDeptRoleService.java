package com.beyondsoft.service;

import com.beyondsoft.entity.SysDeptRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.beyondsoft.utils.Result;
import com.beyondsoft.vo.SysDeptVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kun
 * @since 2019-12-01
 */
public interface SysDeptRoleService extends IService<SysDeptRole> {
    /**添加部门角色**/
    Result add(SysDeptVo sysDeptVo);
    /**修改部门角色**/
    Result update(SysDeptVo sysDeptVo);
    /**修改部门角色**/
    Result delete(Integer deptId);

}
