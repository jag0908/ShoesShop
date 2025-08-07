package com.himedia.shop.controller;

import com.himedia.shop.dto.QnaVO;
import com.himedia.shop.service.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService cs;

    @GetMapping("/customer")
    public String customer(){
        return "customer/customer";
    }

    @GetMapping("/qnaList")
    public String qnaList(HttpServletRequest request, Model model){
        HashMap<String, Object> result = cs.selectQna(request);
        model.addAttribute("qnaList", result.get("qnaList"));
        model.addAttribute("paging", result.get("paging"));
        return "customer/qnaList";
    }

    @GetMapping("/comfirmPass")
    public String comfirmPass(@RequestParam("pass") String pass, @RequestParam("qseq") int qseq, Model model){
        QnaVO qvo = cs.getQna(qseq);
        String url = "";
        if(qvo.getPass().equals(pass)){
            url = "redirect:/qnaView?qseq=" + qseq;
        }else{
            url = "customer/passwordFail";
        }
        return url;
    }

    @GetMapping("/qnaView")
    public String qnaView(@RequestParam("qseq") int qseq, Model model){
        QnaVO qvo = cs.getQna(qseq);
        model.addAttribute("qnaVO", qvo);
        return "customer/qnaView";

    }

}
