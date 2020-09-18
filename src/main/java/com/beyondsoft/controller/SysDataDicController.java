package com.beyondsoft.controller;


import com.beyondsoft.aspect.log.SysOperLogAnno;
import com.beyondsoft.bo.PageData;
import com.beyondsoft.co.DataDicCo;
import com.beyondsoft.entity.SysDataDic;
import com.beyondsoft.entity.SysUser;
import com.beyondsoft.service.SysDataDicService;
import com.beyondsoft.utils.Const;
import com.beyondsoft.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * <p>
 * 数据字典表 前端控制器
 * </p>
 *
 * @author kun
 * @since 2019-11-19
 */
@RestController
@RequestMapping("/sys-data-dic")
public class SysDataDicController {

    @Autowired
    private SysDataDicService sysDataDicService;

    /**
     * 数据字典面页
     * @param dataDicCo
     * @return
     */
    @SysOperLogAnno(logType=200,module="数据字典管理 ",methods="进入数据字典面页")
    @GetMapping("/pagedata")
    public PageData getPageData(DataDicCo dataDicCo) {
          return  sysDataDicService.listPageData(dataDicCo);
    }

    /**
     * 添加数据字典
     * @param sysDataDic
     * @return
     */
    @SysOperLogAnno(logType=200,module="数据字典管理 ",methods="添加数据字典")
    @PostMapping("/add")
    public Result dataDicAdd(SysDataDic sysDataDic, HttpSession session) {
        sysDataDic.setCreateTime(new Date());
        sysDataDic.setEnabled(Const.ENABLED_Y);
        sysDataDic.setDelFlag(Const.DEL_FLAG_N);
        SysUser user = (SysUser)session.getAttribute("user");
        sysDataDic.setCreateUserId(user.getId());
        Result r = sysDataDicService.saveDataDic(sysDataDic);
        return r;
    }

    /**
     * 修改数据字典
     * @param sysDataDic
     * @return
     */
    @SysOperLogAnno(logType=200,module="数据字典管理 ",methods="修改数据字典")
    @PutMapping("/update")
    public Result dataDicUpdate(SysDataDic sysDataDic) {
        return sysDataDicService.updateDataDic(sysDataDic);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @SysOperLogAnno(logType=200,module="数据字典管理 ",methods="删除数据字典")
    @PutMapping("/del/{id}")
    public Result dataDicDelete(@PathVariable(value="id") Integer id) {
        return sysDataDicService.deleteDataDic(id);
    }

    /**
     * 下拉列表数据加载
     * @return
     */
    @GetMapping("/pidSelect")
   public Result pidSelectData() {
        return sysDataDicService.pidSelectData();
   }

    /**
     * 获得当前的结点下子结点的排序值
     * @param pid
     * @return
     */
   @GetMapping("/child/sort/{pid}")
   public Result getChildSort(@PathVariable String pid){
        return sysDataDicService.getChildSort(pid);
   }
    /**
     * 检查dicCode是否存在
     * @param dicCode
     * @return
     */
   @GetMapping("/isexist/{dicCode}")
   public Result dicCodeIsExist(@PathVariable(value="dicCode") String dicCode) {
       Result r = sysDataDicService.dicCodeIsExist(dicCode);
       return r;
   }
    /**
     * 启用数据字典
     * @param id
     * @return
     */
    @SysOperLogAnno(logType=200,module="数据字典管理 ",methods="启用数据字典")
   @PutMapping("/enable/start/{id}")
   public Result updateEnableStart(@PathVariable(value="id") Integer id){
        return sysDataDicService.updateDataDicStart(id);
    }

    /**
     * 停用数据字典
     * @param id
     * @return
     */
    @SysOperLogAnno(logType=200,module="数据字典管理 ",methods="停用数据字典")
    @PutMapping("/enable/stop/{id}")
    public Result updateEnableStop(@PathVariable(value="id") Integer id){
        return sysDataDicService.updateDataDicStop(id);
    }

    @GetMapping("/pagedata/list")
    public Result pagedataList(DataDicCo dataDicCo) {
        return null;
    }
}
