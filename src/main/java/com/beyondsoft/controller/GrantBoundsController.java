package com.beyondsoft.controller;


import com.beyondsoft.bo.PageData;
import com.beyondsoft.co.GrantBoundsCo;
import com.beyondsoft.service.GrantBoundsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * <p>
 * 奖励管理 前端控制器
 * </p>
 *
 * @author kun
 * @since 2020-09-03
 */
@RestController
@RequestMapping("/grantbounds")
public class GrantBoundsController {

    @Autowired
    private GrantBoundsService grantBoundsService;



    @GetMapping("/list")
    public ModelAndView toPageList(){
        return  new ModelAndView("recommend/list");
    }

    @GetMapping("/pagedata")
    public PageData getPageDataList(GrantBoundsCo grantBoundsCo){
        return grantBoundsService.getPageDataList(grantBoundsCo);
    }

}
