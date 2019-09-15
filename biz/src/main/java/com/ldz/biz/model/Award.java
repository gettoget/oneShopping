package com.ldz.biz.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

@Data
@Table(name = "award")
public class Award {

    private String id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "award_name")
    private String awardName;

    private String cjsj;

    private  String status;


    public enum InnerColumn{
        id("ID"),
        userId("USER_ID"),
        awardName("AWARD_NAME"),
        cjsj("cjsj"),
        status("STATUS");

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
            return this.column + " desc";
        }

    }


}
