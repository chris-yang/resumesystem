package com.beyondsoft.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.beyondsoft.aspect.log.SysOperLogAnno;
import com.beyondsoft.bo.PageData;
import com.beyondsoft.co.CustomerCo;
import com.beyondsoft.entity.SysUser;
import com.beyondsoft.service.BaCustomerService;
import com.beyondsoft.service.SysRoleService;
import com.beyondsoft.utils.Result;
import com.beyondsoft.utils.Status;
import com.beyondsoft.vo.BaCustomerAndProjectTreeVo;
import com.beyondsoft.vo.BaCustomerVo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;


/**
 * <p>
 * 客户表 前端控制器
 * </p>
 *
 * @author kun
 * @since 2019-11-28
 */
@RestController
@RequestMapping("/ba-customer")
public class BaCustomerController {
    @Autowired
    private BaCustomerService baCustomerService;
    @Autowired
    private SysRoleService roleService;
    /**
     *
     * @param customerCo
     * @return
     */
    @SysOperLogAnno(logType=200,module="客户管理 ",methods="进入客户管理页")
    @GetMapping("/pagedata")
    public PageData listPageData(CustomerCo customerCo){
        PageData pageData = baCustomerService.listPageData(customerCo);
        return  pageData;
    }

    /**
     * 树展示客户信息
     * @return
     */
    @PostMapping("/dtree/pagedata")
    public Object dtreePageData() {
        List<BaCustomerAndProjectTreeVo> baCustomerVos = baCustomerService.getCustomersDtreeList();
        JSONObject josn = new JSONObject();
        Status status = new Status();
        status.setCode(200);
        status.setMessage("操作成功");
        josn.put("status", JSONArray.toJSON(status));
        josn.put("data", JSONArray.toJSON(baCustomerVos));
        System.out.println(josn.toJSONString());
        return josn.toJSONString();
    }
    /**
     *
     * @param session
     * @return
     */
    @PostMapping("/list/tree")
    public Object customerAndProjectTree(HttpSession session){
        SysUser user = (SysUser)session.getAttribute("user");
        Integer userId = user.getId();
        return baCustomerService.getCustomerAndProjectTreeByUserId(userId);

    }

    /**
     * 根据用户id查询当前的first node
     * @return
     */
    @GetMapping("/firstchild")
    public Result getTreeFirstchild(){
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        Integer userId = user.getId();
        return baCustomerService.getFirstNode(userId);
    }
    /**
     * 添加
     * @param baCustomerVo
     * @return
     */
    @SysOperLogAnno(logType=200,module="客户管理 ",methods="添加客户")
    @PostMapping("/add")
    public Result add(BaCustomerVo baCustomerVo, HttpSession session){
        SysUser user = (SysUser)session.getAttribute("user");
        baCustomerVo.setCreateUserId(user.getId());
        Result r = baCustomerService.add(baCustomerVo);
        return r;
    }

    /**
     *修改
     * @param baCustomerVo
     * @return
     */
    @SysOperLogAnno(logType=200,module="客户管理 ",methods="修改客户")
    @PutMapping("/update")
    public Result update(BaCustomerVo baCustomerVo){
        Result r = baCustomerService.update(baCustomerVo);
        return r;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        Result r = baCustomerService.delete(id);
        return r;
    }
    /**
     * 启用
     * @param id
     * @return
     */
    @SysOperLogAnno(logType=200,module="客户管理 ",methods="启用客户")
    @PutMapping("/enable/start/{id}")
    public Result updateEnableStart(@PathVariable(value="id") Integer id){
        return baCustomerService.updateEnableStart(id);
    }

    /**
     * 停用
     * @param id
     * @return
     */
    @SysOperLogAnno(logType=200,module="客户管理 ",methods="停用客户")
    @PutMapping("/enable/stop/{id}")
    public Result updateEnableStop(@PathVariable(value="id") Integer id){
        return baCustomerService.updateEnableStop(id);
    }

    /**
     * 多级部门下拉列表
     * @return
     */
    @GetMapping("/customerselect")
    public Result getCustomerSelect(){
        return baCustomerService.getCustomerSelect();
    }
}
