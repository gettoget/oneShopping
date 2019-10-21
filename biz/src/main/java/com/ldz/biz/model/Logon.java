package com.ldz.biz.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "logon")
@Data
public class Logon {


    @Id
    private String id;

    @Column(name = "user_id")
    private String userId;

    private String cjsj;

    private String bz;

    private String bz1;

    public enum  InnerColumn{

        id("ID"),
        userId("USER_ID"),
        cjsj("CJSJ"),
        bz("BZ"),
        bz1("BZ1");

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

        public String asc() {
            return this.column + " asc";
        }

        public String desc(){
            return this.column + " desc";
        }

    }

}
