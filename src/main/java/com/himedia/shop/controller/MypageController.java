package com.himedia.shop.controller;

import com.himedia.shop.dto.CartVO;
import com.himedia.shop.dto.MemberVO;
import com.himedia.shop.service.MypageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;

@Controller
public class MypageController {
    @Autowired
    MypageService ms;

    /*
    @GetMapping("/mypage")
    public String mypage(HttpSession session) {
            MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
            String url = "member/loginForm";
            if(loginUser!=null){
                url = "member/mypage";
            }
        return url;
     */

    @PostMapping("/orderFormOne")
    public String orderFormOne(@RequestParam("pseq") int pseq,
                               @RequestParam("quantity") int quantity, HttpSession session ){
        // pseq 와 quantity 그리고 로그인 유저의 아이디를 이용해서 cart 테이블에 추가하고
        // 방금 추가한 cart 테이블의 cseq 를 orderForm 으로 redrect 로 전달
        MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
        String url = "member/loginForm";
        if(loginUser!=null){
            ms.insertCart(loginUser.getUserid(),pseq,quantity);
            int cseq = ms.lookupMaxCseq(loginUser.getUserid());
            url = "redirect:/orderForm?cseq=" + cseq;
        }
        return url;

    }

    @GetMapping("/orderDetail")
    public String orderDetail(@RequestParam("oseq") int oseq, Model model) {
        HashMap<String,Object> result = ms.orderListByOseq(oseq);
        model.addAttribute("orderList", result.get("orderList"));
        model.addAttribute("orderDetail", result.get("orderDetail"));
        model.addAttribute("totalPrice", result.get("totalPrice"));
        return "mypage/orderDetail";
    }

    @PostMapping ("/deleteCart")
    public String deleteCart(@RequestParam("cseq") String [] cseqs){
        ms.deleteCart(cseqs);
        return "redirect:/cartList";
    }

    @GetMapping("/cartList")
    public String cartList(HttpSession session, Model model) {
        MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
        String url = "member/loginForm";
        if(loginUser!=null){
            url = "mypage/cartList";
            HashMap<String,Object> result = ms.selectCart(loginUser.getUserid());
            model.addAttribute("cartList",result.get("cartList"));
            model.addAttribute("totalPrice",result.get("totalPrice"));
        }
        return url;
    }

    @PostMapping("/cartInsert")
    public String cartInsert(
            @RequestParam("pseq") int pseq, @RequestParam("quantity") int quantity, HttpSession session, Model model) {
        MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
        String url = "member/loginForm";
        if(loginUser!=null){
            ms.insertCart(loginUser.getUserid(), pseq, quantity);
            model.addAttribute("pseq", pseq);
            url = "mypage/cartInsertComplete";
        }
        return url;
    }

    @GetMapping("/mypage")
    public String mypage(HttpSession session,  Model model) {
        MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
        String url = "member/loginForm";
        if(loginUser!=null){
            url = "mypage/mypage";
            model.addAttribute("finalList", ms.orderIng(loginUser.getUserid()));
            model.addAttribute("title", "진행중인 주문내역");
        }
        return url;
    }

    @GetMapping("/orderAll")
    public String orderAll(HttpSession session, Model model) {
        MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
        String url = "member/loginForm";
        if(loginUser!=null){
            url = "mypage/mypage";
            model.addAttribute("finalList", ms.orderAll(loginUser.getUserid()));
            model.addAttribute("title", "총 주문내역");
        }
        return url;
    }

    @GetMapping("/updateMemberForm")
    public String updateMemberForm(HttpSession session, Model model) {
        MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
        String url = "member/loginForm";
        if(loginUser!=null){
            model.addAttribute("dto",loginUser);
            url = "mypage/updateMemberForm";
        }
        return url;
    }

    @PostMapping("/updateMember")
    public String updateMember(
            @ModelAttribute("dto") @Valid MemberVO membervo, BindingResult result,
            @RequestParam(value = "pwdCheck", required = false,defaultValue = "") String pwdCheck,
            HttpSession session, Model model) {
        String url = "mypage/updateMemberForm";
        if("non".equals(membervo.getProvider()) && result.hasFieldErrors("pwd"))
            model.addAttribute("message", "비밀번호를 입력하세요");
        else if("non".equals(membervo.getProvider()) && result.hasFieldErrors("pwdCheck"))
            model.addAttribute("message", "비밀번호 확인이 일치하지 않습니다");
        else if(result.hasFieldErrors("name"))
            model.addAttribute("message", "이름을 입력하세요");
        else if(result.hasFieldErrors("email"))
            model.addAttribute("message", "이메일을 입력하세요");
        else if(result.hasFieldErrors("phone"))
            model.addAttribute("message", "전화번호를 입력하세요");
        else {
            url = "redirect:/";
            ms.updateMember(membervo);
            session.setAttribute("loginUser", membervo);
        }
        return url;
        }

    @GetMapping("/orderForm")
    public String orderForm(@RequestParam("cseq") String [] cseqs, HttpSession session, Model model) {
        // 주문하기 위한 카트번호로 cart_view 의 레코드들을 조회하고, totalPrice 계산해서 orderForm.jsp 로 이동
        HashMap<String, Object> result = ms.makeCartList(cseqs);
        model.addAttribute("cartList",result.get("cartList"));
        model.addAttribute("totalPrice",result.get("totalPrice"));
        return "mypage/orderForm";
    }


    @PostMapping("/orderInsert")
    public String orderInsert(
            @RequestParam("cseq") String [] cseqs,
            @RequestParam("zip_num") String zip_num,
            @RequestParam("address1") String address1,
            @RequestParam("address2") String address2,
            @RequestParam(value = "address3", required = false) String address3, HttpSession session) {
        int oseq;

        MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
        String url = "member/loginForm";
        if(loginUser!=null){
            oseq = ms.insertOrder(cseqs, zip_num, address1, address2, address3, loginUser.getUserid());
            url = "redirect:/orderList?oseq=" + oseq;
        }
        return url;
    }

    @GetMapping("/orderList")
    public ModelAndView orderList(@RequestParam("oseq") int oseq) {
        ModelAndView mav = new ModelAndView();
        HashMap<String, Object> result = ms.orderListByOseq(oseq);
        mav.addObject("orderList",result.get("orderList"));  // 주문의 상세리스트
        mav.addObject("totalPrice",result.get("totalPrice"));  // 총 결제 금액
        mav.addObject("orderDetail", result.get("orderDetail"));  // 상세리스트의 첫번째 주문상품레코드
        mav.setViewName("mypage/orderList");
        return mav;
    }

    @GetMapping("/deleteMember")
    public String deleteMember(HttpSession session, Model model) {
        MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
        String url = "redirect:/mypage";
        if(loginUser!=null){
            ms.deleteMember(loginUser.getUserid());
            session.removeAttribute("loginUser");
            url = "redirect:/";
        }
        return url;

    }
}
