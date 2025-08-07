package com.himedia.shop.service;

import com.himedia.shop.dao.IMemberDao;
import com.himedia.shop.dto.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    IMemberDao mdao;


    public MemberVO getMember(String userid) {
       return mdao.getMember(userid);
    }

    public void insert(MemberVO mvo) {
        mdao.insert(mvo);
    }

    public MemberVO getMemberByNamePhone(String name, String phone) {
        return mdao.getMemberByNamePhone(name, phone);
    }

    public void updatePwd(String userid, String pwd) {
        mdao.updatePwd(userid, pwd);
    }
}
