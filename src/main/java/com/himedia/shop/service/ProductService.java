package com.himedia.shop.service;

import com.himedia.shop.dao.IProductDao;
import com.himedia.shop.dto.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class ProductService {

    @Autowired
    IProductDao pdao;

    public HashMap<String, Object> selectNewBest() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("bestList", pdao.selectBest());
        result.put("newList", pdao.selectNew());
        return result;
    }

    public ArrayList<ProductVO> selectCategory(String category) {
        return pdao.selectCategory(category);
    }

    public ProductVO getProduct(int pseq) {
        return pdao.getProduct(pseq);
    }
}
