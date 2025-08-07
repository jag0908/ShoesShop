package com.himedia.shop.service;

import com.himedia.shop.dao.IMypageDao;
import com.himedia.shop.dto.CartVO;
import com.himedia.shop.dto.MemberVO;
import com.himedia.shop.dto.OrderVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

@Service
public class MypageService {

    @Autowired
    IMypageDao mdao;

    public void updateMember(MemberVO membervo) { mdao.updateMember(membervo); }

    public void insertCart(String userid, int pseq, int quantity) {
        mdao.insertCart(userid, pseq, quantity);
    }

    public HashMap<String, Object> selectCart(String userid) {
        HashMap<String, Object> result = new HashMap<>();
        ArrayList<CartVO> list = mdao.selectCart(userid);
        result.put("cartList", list);
        int totalPrice = 0;
        for (CartVO cartvo : list)
            totalPrice += cartvo.getPrice2()*cartvo.getQuantity();
        result.put("totalPrice", totalPrice);
        return result;
    }

    public void deleteCart(String[] cseqs) {
        for(String cseq : cseqs){
            mdao.deleteCart(Integer.parseInt(cseq));
        }
    }

    public HashMap<String, Object> makeCartList(String[] cseqs) {
        HashMap<String, Object> result = new HashMap<>();
        ArrayList<CartVO> list = new ArrayList<>();
        int totalPrice = 0;
        for(String cseq : cseqs){
            CartVO cvo = mdao.getCart(Integer.parseInt(cseq));  // cseq로 cart_view에서 레코드 검색
            list.add(cvo);  // 검색한 레코드를 list에 추가
            totalPrice += cvo.getPrice2()*cvo.getQuantity(); // 레코드의 일부 항목으로 총금액을 누적 계
        }
        result.put("cartList", list);
        result.put("totalPrice", totalPrice);
        return result;
    }
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public int insertOrder(String[] cseqs, String zipNum, String address1, String address2, String address3, String userid) {
        // 로그인 유저의 아이디로 orders 테이블에 레코드르 추가
        mdao.insertOrders(userid);

        // 방금 추가한 레코드의 oseq를 조회
        int oseq = mdao.lookupMaxOseq(userid);

        // cseqs 안의 cseq로 장바구니 상품을 조회 후 oseq 와 함께 order_detail 테이블에 레코드를 추가
        for(String cseq : cseqs){
            CartVO cvo = mdao.getCart(Integer.parseInt(cseq));
            OrderVO ovo = new OrderVO();
            ovo.setOseq(oseq);
            ovo.setAddress1(address1);
            ovo.setAddress2(address2);
            ovo.setAddress3(address3);
            ovo.setZip_num(zipNum);
            ovo.setPseq(cvo.getPseq());
            ovo.setQuantity(cvo.getQuantity());
            mdao.insertOrderDetail(ovo);
            // cart 테이블에서 해당 상품 삭제
            mdao.deleteCart(Integer.parseInt(cseq));
        }



        return oseq;
    }

    public HashMap<String, Object> orderListByOseq(int oseq) {
        HashMap<String, Object> result = new HashMap<>();
        ArrayList<OrderVO> list = mdao.selectOrderByOseq(oseq);
        result.put("orderList", list);
        int totalPrice = 0;
        for (OrderVO ordervo : list)
            totalPrice += ordervo.getPrice2()*ordervo.getQuantity();
        result.put("totalPrice", totalPrice);
        result.put("orderDetail", list.get(0));
        return result;
    }

    public ArrayList<OrderVO> orderIng(String userid) {
        ArrayList<OrderVO> finalList = new ArrayList<>();

        // oseq 들을 중복없이 조회
        ArrayList<Integer> oseqList = mdao.getOseqIng(userid);

        // 조회된 oseq 들로 상세 주문조회(반복실행)
        for(Integer oseq:oseqList){
            // oseq 로 주문 상세를 조회
            ArrayList<OrderVO> list = mdao.selectOrderByOseq(oseq);

            // 조회된 내역으로 finalList 에 넣을 항목을 만들어서
            OrderVO ovo = list.get(0);
            ovo.setPname(ovo.getPname() + " 포함 " + list.size() + "건");

            int totalPrice = 0;
            for (OrderVO ovo1 : list)
                totalPrice += ovo1.getPrice2()*ovo1.getQuantity();
            ovo.setPrice2(totalPrice);

            // finalList 에 추가
            finalList.add(ovo);
        }
        return finalList;
    }


    public Object orderAll(String userid) {
        ArrayList<OrderVO> finalList = new ArrayList<>();
        ArrayList<Integer> oseqList = mdao.getOseqAll(userid);
        for(Integer oseq:oseqList){
            ArrayList<OrderVO> list = mdao.selectOrderByOseq(oseq);
            OrderVO ovo = list.get(0);
            ovo.setPname(ovo.getPname() + " 포함 " + list.size() + "건");
            int totalPrice = 0;
            for (OrderVO ovo1 : list)
                totalPrice += ovo1.getPrice2()*ovo1.getQuantity();
            ovo.setPrice2(totalPrice);
            finalList.add(ovo);
        }
        return finalList;
    }

    public int lookupMaxCseq(String userid) {
        int cseq = mdao.lookupMaxCseq(userid);
        return cseq;
    }

    public void deleteMember( String userid) {
        mdao.deleteMember(userid);
    }
}
