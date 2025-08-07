package com.himedia.shop.controller;

import com.himedia.shop.dto.ProductVO;
import com.himedia.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;

@Controller
public class ProductController {

    @Autowired
    ProductService ps;

    @GetMapping("/")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index");
        HashMap<String,Object> result = ps.selectNewBest();
        mav.addObject("bestList",result.get("bestList"));
        mav.addObject("newList",result.get("newList"));
        return mav;
    }

    @GetMapping("/category")
    public ModelAndView category(@RequestParam("category") String category) {
        ModelAndView mav = new ModelAndView();
//        ArrayList<ProductVO> list = ps.selectCategory(category);
        mav.addObject("categoryProduct", ps.selectCategory(category));
        mav.setViewName("product/categoryProduct");
        return mav;
    }

    @GetMapping("/productDetail")
    public ModelAndView productDetail(@RequestParam("pseq") int pseq) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("product", ps.getProduct(pseq));
        mav.setViewName("product/productDetail");
        return mav;
    }


}
