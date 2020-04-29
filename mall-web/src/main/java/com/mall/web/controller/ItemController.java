package com.mall.web.controller;

import com.mall.web.html.HtmlService;
import com.mall.web.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * 用于前端请求商品详情页的controller
 * @author pan
 * @create 2020-04-28-18:16
 */
@Controller
@RequestMapping("item")
public class ItemController {

    @Autowired
    GoodsService goodsService;

    @Autowired
    HtmlService htmlService;

    @GetMapping("{id}.html")
    public String getGoodsHtml(Model model, @PathVariable("id") Long id){
        // 加载所需的数据
        Map<String, Object> modelMap = this.goodsService.loadData(id);
        // 放入模型
        model.addAllAttributes(modelMap);
        //缓存
        this.htmlService.asyncExecute(id);
        //跳转到item.html渲染数据
        return "item";
    }


}
