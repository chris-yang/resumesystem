package com.beyondsoft.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beyondsoft.bo.PageData;
import com.beyondsoft.co.DemandCo;
import com.beyondsoft.entity.BaCity;
import com.beyondsoft.entity.Demand;
import com.beyondsoft.entity.SysUser;
import com.beyondsoft.service.BaCityService;
import com.beyondsoft.service.DemandService;
import com.beyondsoft.service.SysDataDicService;
import com.beyondsoft.utils.Const;
import com.beyondsoft.utils.Result;
import com.beyondsoft.vo.DemandVo;
import com.beyondsoft.vo.SysDataDicVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author kun
 * @since 2020-09-01
 */
@RestController
@RequestMapping("/demand")
public class DemandController {
    @Autowired
    private SysDataDicService sysDataDicService;
    @Autowired
    private BaCityService baCityService;
    @Autowired
    private DemandService demandService;

    /**
     * 添加
     * @param mode
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/toAddorUpdate/{mode}/{id}")
    public ModelAndView toAddorUpdate(@PathVariable String mode, @PathVariable Integer id, Model model) {
        List<SysDataDicVo> jobStyles = sysDataDicService.getDataDicSelectByParentCode(Const.JOB_STYLES);
        List<BaCity> cites = baCityService.list();
        List<SysDataDicVo> salaryAreas = sysDataDicService.getDataDicSelectByParentCode(Const.SALARY_AREAS);
        List<SysDataDicVo> jobLevels = sysDataDicService.getDataDicSelectByParentCode(Const.JOB_LEVELS);
        List<SysDataDicVo> degrees = sysDataDicService.getDataDicSelectByParentCode(Const.DEGREES);
        List<SysDataDicVo> workYears = sysDataDicService.getDataDicSelectByParentCode(Const.WORK_YEARS);
        List<SysDataDicVo> genders = sysDataDicService.getDataDicSelectByParentCode(Const.GENDER);
        List<SysDataDicVo> internalRecommends = sysDataDicService.getDataDicSelectByParentCode(Const.INTERNAL_RECEOMMEND);



        if(mode.equals(Const.MODE_ADD)) {
            Demand demand = new Demand();
            model.addAttribute("demand",demand);
            model.addAttribute("mode",Const.MODE_ADD);
        } else if(mode.equals(Const.MODE_UPDADTE)) {
            Demand demand = demandService.getById(id);
            model.addAttribute("demand",demand);
            model.addAttribute("mode",Const.MODE_UPDADTE);
        }
        model.addAttribute("jobStyles",jobStyles);
        model.addAttribute("cites",cites);
        model.addAttribute("salaryAreas",salaryAreas);
        model.addAttribute("jobLevels",jobLevels);
        model.addAttribute("degrees",degrees);
        model.addAttribute("workYears",workYears);
        model.addAttribute("genders",genders);
        model.addAttribute("internalRecommends",internalRecommends);


        return new ModelAndView("demand/add");
    }

    /**
     * 跳转职位列表
     * @return
     */
    @GetMapping("/list")
    public ModelAndView toDemandList(Model model) {
        List<SysDataDicVo> jobStyles = sysDataDicService.getDataDicSelectByParentCode(Const.JOB_STYLES);
        List<BaCity> cites = baCityService.list();
        List<SysDataDicVo> salaryAreas = sysDataDicService.getDataDicSelectByParentCode(Const.SALARY_AREAS);
        List<SysDataDicVo> jobLevels = sysDataDicService.getDataDicSelectByParentCode(Const.JOB_LEVELS);
        List<SysDataDicVo> degrees = sysDataDicService.getDataDicSelectByParentCode(Const.DEGREES);
        List<SysDataDicVo> workYears = sysDataDicService.getDataDicSelectByParentCode(Const.WORK_YEARS);
        List<SysDataDicVo> genders = sysDataDicService.getDataDicSelectByParentCode(Const.GENDER);
        List<SysDataDicVo> internalRecommends = sysDataDicService.getDataDicSelectByParentCode(Const.INTERNAL_RECEOMMEND);

        model.addAttribute("jobStyles",jobStyles);
        model.addAttribute("cites",cites);
        model.addAttribute("salaryAreas",salaryAreas);
        model.addAttribute("jobLevels",jobLevels);
        model.addAttribute("degrees",degrees);
        model.addAttribute("workYears",workYears);
        model.addAttribute("genders",genders);
        model.addAttribute("internalRecommends",internalRecommends);
        return new ModelAndView("demand/list");
    }

    /**
     * 加载职位列表数据
     * @param demandCo
     * @return
     */
    @GetMapping("/pagedata")
    public PageData demandList(DemandCo demandCo){
       return demandService.demandList(demandCo);
    }

    /**
     * 保存
     * @param demand
     * @return
     */
    @PostMapping("/add")
    public Result saveDemand(Demand demand){
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        demand.setUserId(user.getId());
        return  demandService.saveDemand(demand);
    }

    /**
     * 修改
     * @param demand
     * @return
     */
    @PutMapping("/update")
    public Result updateDemand(Demand demand){
        return  demandService.updateDemand(demand);
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        return  demandService.deleteDemand(id);
    }

    @PutMapping("/start/{id}")
    public Result start(@PathVariable Integer id){
        return demandService.start(id);
    }

    @PutMapping("/stop/{id}")
    public Result stop(@PathVariable Integer id){
        return demandService.stop(id);
    }

}
