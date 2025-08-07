package com.himedia.shop.dao;

import com.himedia.shop.dto.CartVO;
import com.himedia.shop.dto.MemberVO;
import com.himedia.shop.dto.OrderVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface IMypageDao {

    void updateMember(MemberVO membervo);
    void insertCart(String userid, int pseq, int quantity);
    ArrayList<CartVO> selectCart(String userid);
    void deleteCart(int i);
    CartVO getCart(int i);
    void insertOrders(String userid);
    int lookupMaxOseq(String userid);
    void insertOrderDetail(OrderVO ovo);
    ArrayList<OrderVO> selectOrderByOseq(int oseq);
    ArrayList<Integer> getOseqIng(String userid);
    ArrayList<Integer> getOseqAll(String userid);
    int lookupMaxCseq(String userid);
    void deleteMember(String userid);
}
