package com.ldz.biz.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "BIZ_LOGIN")
@Data
public class Login {

    @Id
    private String id;

    @Column(name = "ip")
    private String IP;

    private String cjsj;

    @Column(name = "user_id")
    private String userId;

    public enum InnerColumn{

        id("ID"),
        IP("IP"),
        cjsj("CJSJ"),
        userId("USER_ID");

        private final  String column;

        InnerColumn(String column) {
            this.column = column;
        }

        public String value(){
            return this.column;
        }

        public String getValue(){
            return this.column;
        }

        public String desc() {
            return this.column + " desc";
        }

        public String asc() {
            return  this.column + " asc";
        }

    }


}
