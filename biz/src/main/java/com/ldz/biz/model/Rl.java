package com.ldz.biz.model;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "rl")
@Data
public class Rl {


    @Id
    private String date;


    private String type;

    private String year;


}
