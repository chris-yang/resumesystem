package com.beyondsoft.controller;


import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.beyondsoft.bo.PageData;
import com.beyondsoft.co.RecommendCo;
import com.beyondsoft.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * <p>
 * 推荐记录 前端控制器
 * </p>
 *
 * @author kun
 * @since 2020-09-02
 */
@RestController
@RequestMapping("/recommend")
public class RecommendController {
    @Autowired
    private RecommendService recommendService;


    @GetMapping("/list")
    public ModelAndView toPageList(){
        return  new ModelAndView("recommend/list");
    }

    /**
     * 加载页面数据
     * @param recommendCo
     * @return
     */
    @GetMapping("/pagedata")
    public PageData getRecommendPageList(RecommendCo recommendCo){
        return recommendService.getRecommendPageList(recommendCo);
    }
}
