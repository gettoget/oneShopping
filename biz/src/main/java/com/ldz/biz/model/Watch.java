package com.ldz.biz.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

//
@Table(name = "BIZ_WATCH")
@Data
public class Watch {

    @Id
    private String id;

    private String cjsj;

    @Column(name = "pro_id")
    private String proId;

    @Column(name = "pro_baseid")
    private String proBaseid;

    public enum InnerColumn{

        id("ID"),
        cjsj("CJSJ"),
        proId("PRO_ID"),
        proBaseid("PRO_BASEID");

        private final String column;

        InnerColumn(String column) {
            this.column = column;
        }

        private String value(){
            return this.column;
        }

        private String getValue(){
            return this.column;
        }

        private String desc(){
            return this.column + " desc";
        }

        private String asc(){
            return this.column + " asc";
        }


    }

}
