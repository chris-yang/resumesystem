package com.beyondsoft.controller;


import com.beyondsoft.aspect.log.SysOperLogAnno;
import com.beyondsoft.bo.PageData;
import com.beyondsoft.co.CityCo;
import com.beyondsoft.entity.BaCity;
import com.beyondsoft.entity.SysUser;
import com.beyondsoft.service.BaCityService;
import com.beyondsoft.utils.Result;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author kun
 * @since 2020-01-08
 */
@RestController
@RequestMapping("/ba-city")
public class BaCityController {
    @Autowired
    private BaCityService baCityService;
    /**
     * 列表显示
     * @param cityCo
     * @return
     */
    @SysOperLogAnno(logType=200,module="城市管理 ",methods="城市管理页面")
    @GetMapping("/pagedata")
    public PageData listPageData(CityCo cityCo){
        PageData pageData = baCityService.listPageData(cityCo);
        return  pageData;
    }

    /**
     * 获取下拉城市列表
     * @param
     * @return
     */
    @GetMapping("/citySelect")
    public Result citySelect(){
        return this.baCityService.getBaCityListAjax();
    }

    /**
     * 添加
     * @param baCity
     * @return
     */
    @SysOperLogAnno(logType=200,module="城市管理 ",methods="添加城市")
    @PostMapping("/add")
    public Result add(BaCity baCity){
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        baCity.setCreateUserId(user.getId());
        baCity.setCreateTime(new Date());
        Result r = baCityService.add(baCity);
        return r;
    }

    /**
     *修改
     * @param baCity
     * @return
     */
    @SysOperLogAnno(logType=200,module="城市管理 ",methods="添加城市")
    @PutMapping("/update")
    public Result update(BaCity baCity){
        Result r = baCityService.update(baCity);
        return r;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @SysOperLogAnno(logType=200,module="城市管理 ",methods="删除城市")
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        Result r = baCityService.delete(id);
        return r;
    }
    /**
     * 启用
     * @param id
     * @return
     */
    @SysOperLogAnno(logType=200,module="城市管理 ",methods="启用城市")
    @PutMapping("/enable/start/{id}")
    public Result updateEnableStart(@PathVariable(value="id") Integer id){
        return baCityService.updateEnableStart(id);
    }

    /**
     * 停用
     * @param id
     * @return
     */
    @SysOperLogAnno(logType=200,module="城市管理 ",methods="停用城市")
    @PutMapping("/enable/stop/{id}")
    public Result updateEnableStop(@PathVariable(value="id") Integer id){
        return baCityService.updateEnableStop(id);
    }
}
