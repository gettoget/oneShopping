package com.ldz.biz.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "shared")
@Data
public class Shared {

    @Id
    private String id;

    @Column(name = "USER_ID")
    private String userId;

    private String cjsj;

    private String cjr;

    private String bz;

    private String bz1;

    private String bz2;

    private String bz3;

    public enum InnerColumn{

        id("ID"),
        userId("USER_ID"),
        cjsj("CJSJ"),
        cjr("CJR");

        public final String column;

        InnerColumn(String column) {
            this.column = column;
        }
        public String value(){
            return this.column;
        }
        public String getValue(){
            return this.column;
        }
        public String asc(){
            return this.column + " asc";
        }
        public String desc(){
            return this.column  + " desc";
        }
    }

}
