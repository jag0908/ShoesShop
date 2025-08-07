package com.himedia.shop.dao;

import com.himedia.shop.dto.Paging;
import com.himedia.shop.dto.QnaVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface ICustomerDao {

    int getAllCount();
    ArrayList<QnaVO> selectQna(Paging paging);
    QnaVO getQna(int qseq);
}
