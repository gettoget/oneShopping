package com.ldz.biz.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserInModel implements Serializable {
    private String userId;
    private String cys;
    private String cz;
    private String xf;
    private String czcg;
}
