package com.himedia.shop.dao;

import com.himedia.shop.dto.MemberVO;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IMemberDao {

    MemberVO getMember(String userid);
    void insert(MemberVO mvo);
    MemberVO getMemberByNamePhone(String name, String phone);
    void updatePwd(String userid, String pwd);
}
