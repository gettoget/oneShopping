package com.ldz.biz.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "bank")
@Data
public class Bank {
    @Id
    private String id;

    /**
     * 银行卡号
     */
    @Column(name = "bank_no")
    private String bankNo;

    /**
     * 银行卡类型
     */
    private String type;

    public enum  InnerColumn{

        id("ID"),
        bankNo("BANK_NO"),
        type("TYPE");

        private final String column;

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
            return this.column + " desc";
        }
    }


}
