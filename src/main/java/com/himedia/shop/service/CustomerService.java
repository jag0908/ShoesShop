package com.himedia.shop.service;

import com.himedia.shop.dao.ICustomerDao;
import com.himedia.shop.dto.Paging;
import com.himedia.shop.dto.QnaVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class CustomerService {

    @Autowired
    private ICustomerDao cdao;

    public HashMap<String, Object> selectQna(HttpServletRequest request) {
        HashMap<String, Object> result = new HashMap<>();
        HttpSession session = request.getSession();

        int page=1;
        if(request.getParameter("page")!=null){
            page=Integer.parseInt(request.getParameter("page"));
            session.setAttribute("page",page);
        }else if(session.getAttribute("page")!=null){
            page=(Integer)session.getAttribute("page");
        }
        Paging paging = new Paging();
        paging.setPage(page);
        paging.setDisplayPage(5);
        paging.setDisplayRow(5);

        int count = cdao.getAllCount();
        paging.setTotalCount(count);
        paging.calPaging();

        ArrayList<QnaVO> list = cdao.selectQna(paging);
        result.put("qnaList", list);
        result.put("paging", paging);
        return result;
    }

    public QnaVO getQna(int qseq) {
        return cdao.getQna(qseq);
    }
}
