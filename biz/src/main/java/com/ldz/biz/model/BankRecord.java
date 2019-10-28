package com.ldz.biz.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "bank_record")
@Data
public class BankRecord {

    @Id
    private String id;

    @Column(name = "bank_no")
    private String bankNo;

    private String cjsj;

    public enum InnerColumn{
        id("ID"),
        bankNo("BANK_NO"),
        cjsj("CJSJ");
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
