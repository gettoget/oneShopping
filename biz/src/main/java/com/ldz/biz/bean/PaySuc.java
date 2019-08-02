package com.ldz.biz.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class PaySuc implements Serializable {
    private String amount;

    private String trans_id;

    private String words;

}
