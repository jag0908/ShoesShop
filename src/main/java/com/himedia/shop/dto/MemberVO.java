package com.himedia.shop.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.security.Timestamp;

@Data
public class MemberVO {
    @NotNull @NotEmpty
    private String userid;
    @NotNull @NotEmpty
    private String pwd;
    @NotNull @NotEmpty
    private String name;
    @NotNull @NotEmpty
    private String email;
    @NotNull @NotEmpty
    private String phone;
    private String zip_num;
    private String address1;
    private String address2;
    private String address3;
    private Timestamp indate;
    private String provider;
}
