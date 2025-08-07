package com.himedia.shop.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.security.Timestamp;

@Data
public class ProductVO {
    private int pseq;
    @NotNull @NotEmpty
    private String name;
    @NotNull @NotEmpty
    private String category;
    private int price1;
    @NotNull @NotEmpty
    private int price2;
    private int price3;
    @NotNull @NotEmpty
    private String content;
    @NotNull @NotEmpty
    private String image;
    private String useyn;
    private String bestyn;
    private Timestamp indate;
    @NotNull @NotEmpty
    private String savefilename;
}
