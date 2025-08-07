package com.himedia.shop.dao;

import com.himedia.shop.dto.ProductVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface IProductDao {

    ArrayList<ProductVO> selectBest();
    ArrayList<ProductVO> selectNew();
    ArrayList<ProductVO> selectCategory(String category);
    ProductVO getProduct(int pseq);
}
