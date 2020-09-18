package com.beyondsoft.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.beyondsoft.aspect.log.SysOperLogAnno;
import com.beyondsoft.entity.SysUser;
import com.beyondsoft.service.SysDeptService;
import com.beyondsoft.utils.Result;
import com.beyondsoft.utils.Status;
import com.beyondsoft.vo.SysDeptVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 * 部门表 前端控制器
 * </p>
 *
 * @author kun
 * @since 2019-11-07
 */
@RestController
@RequestMapping("/sys-dept")
public class SysDeptController {

    @Autowired
    private SysDeptService sysDeptService;

    /**
     * @description 部门下拉框
     *
     * @author admin
     * @date 2019-11-13
     * @return
     */
    @GetMapping("/deptselect")
    public Result getDeptSelect() {
        return sysDeptService.getDeptSelect();
    }

    /**
     * 部门管理页面
     * @return
     */
    @SysOperLogAnno(logType=200,module="部门管理 ",methods="进入部门管理页面")
    @PostMapping("/dtree/pagedata")
    public Object dtreePageData() {
        List<SysDeptVo> sysPermissions = sysDeptService.getDeptTreeList();
        JSONObject josn = new JSONObject();
        Status status = new Status();
        status.setCode(200);
        status.setMessage("操作成功");
        josn.put("status", JSONArray.toJSON(status));
        josn.put("data", JSONArray.toJSON(sysPermissions));
        System.out.println(josn.toJSONString());
        return josn.toJSONString();
    }

    /**
     * 添加
     * @param sysDeptVo
     * @return
     */
    @SysOperLogAnno(logType=200,module="部门管理 ",methods="添加部门")
    @PostMapping("/add")
    public Result add(SysDeptVo sysDeptVo, HttpSession session){
        SysUser user = (SysUser)session.getAttribute("user");
        sysDeptVo.setCreateUserId(user.getId());
        Result r = sysDeptService.add(sysDeptVo);
        return r;
    }

    /**
     *修改
     * @param sysDeptVo
     * @return
     */
    @SysOperLogAnno(logType=200,module="部门管理 ",methods="修改部门")
    @PutMapping("/update")
    public Result update(SysDeptVo sysDeptVo){
        Result r = sysDeptService.update(sysDeptVo);
        return r;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @SysOperLogAnno(logType=200,module="部门管理 ",methods="删除部门")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        Result r = sysDeptService.delete(id);
        return r;
    }
    /**
     * 检查部门编码deptCode是否存在
     * @param deptCode
     * @return
     */
    @GetMapping("/isexist/{deptCode}")
    public Result dicCodeIsExist(@PathVariable(value="deptCode") String deptCode) {
        Result r = sysDeptService.deptCodeIsExist(deptCode);
        return r;
    }
    /**
     * 启用
     * @param id
     * @return
     */
    @SysOperLogAnno(logType=200,module="部门管理 ",methods="启用部门")
    @PutMapping("/enable/start/{id}")
    public Result updateEnableStart(@PathVariable(value="id") Integer id){
        return sysDeptService.updateEnableStart(id);
    }

    /**
     * 用
     * @param id
     * @return
     */
    @SysOperLogAnno(logType=200,module="部门管理 ",methods="停用部门")
    @PutMapping("/enable/stop/{id}")
    public Result updateEnableStop(@PathVariable(value="id") Integer id){
        return sysDeptService.updateEnableStop(id);
    }

    /**
     * 根据部门id下的角色获得用户
     * @param deptId
     * @return
     */
    @GetMapping("/role/users/{deptId}")
    public Result getDeptRoleUsers(@PathVariable Integer deptId){
        return sysDeptService.getDeptRoleUsers(deptId);
    }
}
