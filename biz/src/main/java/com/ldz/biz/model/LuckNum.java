package com.ldz.biz.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "luck_num")
public class LuckNum {

    @Id
    private String id;

    @Column(name = "pro_baseid")
    private String proBaseid;

    @Column(name = "num")
    private String num;

    @Column(name = "sort")
    private Integer sort;

    @Column(name = "cjsj")
    private String cjsj;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProBaseid() {
        return proBaseid;
    }

    public void setProBaseid(String proBaseid) {
        this.proBaseid = proBaseid;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getCjsj() {
        return cjsj;
    }

    public void setCjsj(String cjsj) {
        this.cjsj = cjsj;
    }

    public enum InnerColumn {
        id("id"),
        proBaseid("pro_baseid"),
        num("num"),
        sort("sort"),
        cjsj("cjsj");

        private final String column;

        public String value() {
            return this.column;
        }

        public String getValue() {
            return this.column;
        }

        InnerColumn(String column) {
            this.column = column;
        }

        public String desc() {
            return this.column + " DESC";
        }

        public String asc() {
            return this.column + " ASC";
        }
    }


}
