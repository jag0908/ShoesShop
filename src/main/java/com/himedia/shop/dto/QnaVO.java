package com.himedia.shop.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class QnaVO {

    private Integer qseq;
    private String secret;
    private String pass;
    @NotEmpty @NotNull
    private String subject;
    @NotEmpty @NotNull
    private String content;
    private String reply;
    @NotEmpty @NotNull
    private String userid;
    private Timestamp indate;
}
