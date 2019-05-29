package com.ldz.biz.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

@Table(name = "pro_baseinfo")
public class ProBaseinfo implements Serializable {
    @Id
    private String id;

    /**
     * 商品类目
     */
    @Column(name = "pro_type")
    private String proType;

    /**
     * 商品名称
     */
    @Column(name = "pro_name")
    private String proName;

    /**
     * 商品价格
     */
    @Column(name = "pro_price")
    private String proPrice;

    /**
     * 商品库存。该商品可以上架多少次，每上架一次减一
     */
    @Column(name = "pro_store")
    private String proStore;

    /**
     * 商品标签
     */
    @Column(name = "pro_sign")
    private String proSign;

    /**
     * 抢购类型  1 人  2机
     */
    @Column(name = "r_type")
    private String rType;

    /**
     * 创建时间
     */
    private String cjsj;

    /**
     * 商品轮播图。多个图片使用','分隔存储
     */
    private String urls;

    /**
     * 商品封面图
     */
    @Column(name = "cover_url")
    private String coverUrl;

    /**
     * 商品推荐图
     */
    @Column(name = "ref_url")
    private String refUrl;


    /**
     * 备注一
     */
    private String bz1;

    /**
     * 备注 二
     */
    private String bz2;

    /**
     * 备注三
     */
    private String bz3;
    @Transient
    private List<String> imgUrls;
    @Transient
    private List<String> coverUrls;
    @Transient
    private List<String> refUrls;
    /**
     * 已开奖数
     */
    @Transient
    private int kjs;

    public int getKjs() {
        return kjs;
    }

    public void setKjs(int kjs) {
        this.kjs = kjs;
    }

    public List<String> getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(List<String> imgUrls) {
        this.imgUrls = imgUrls;
    }

    public List<String> getCoverUrls() {
        return coverUrls;
    }

    public void setCoverUrls(List<String> coverUrls) {
        this.coverUrls = coverUrls;
    }

    public List<String> getRefUrls() {
        return refUrls;
    }

    public void setRefUrls(List<String> refUrls) {
        this.refUrls = refUrls;
    }

    private static final long serialVersionUID = 1L;

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getRefUrl() {
        return refUrl;
    }

    public void setRefUrl(String refUrl) {
        this.refUrl = refUrl;
    }

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取商品类目
     *
     * @return pro_type - 商品类目
     */
    public String getProType() {
        return proType;
    }

    /**
     * 设置商品类目
     *
     * @param proType 商品类目
     */
    public void setProType(String proType) {
        this.proType = proType;
    }

    /**
     * 获取商品名称
     *
     * @return pro_name - 商品名称
     */
    public String getProName() {
        return proName;
    }

    /**
     * 设置商品名称
     *
     * @param proName 商品名称
     */
    public void setProName(String proName) {
        this.proName = proName;
    }

    /**
     * 获取商品价格
     *
     * @return pro_price - 商品价格
     */
    public String getProPrice() {
        return proPrice;
    }

    /**
     * 设置商品价格
     *
     * @param proPrice 商品价格
     */
    public void setProPrice(String proPrice) {
        this.proPrice = proPrice;
    }

    /**
     * 获取商品库存。该商品可以上架多少次，每上架一次减一
     *
     * @return pro_store - 商品库存。该商品可以上架多少次，每上架一次减一
     */
    public String getProStore() {
        return proStore;
    }

    /**
     * 设置商品库存。该商品可以上架多少次，每上架一次减一
     *
     * @param proStore 商品库存。该商品可以上架多少次，每上架一次减一
     */
    public void setProStore(String proStore) {
        this.proStore = proStore;
    }

    /**
     * 获取商品标签
     *
     * @return pro_sign - 商品标签
     */
    public String getProSign() {
        return proSign;
    }

    /**
     * 设置商品标签
     *
     * @param proSign 商品标签
     */
    public void setProSign(String proSign) {
        this.proSign = proSign;
    }

    /**
     * 获取抢购类型
     *
     * @return r_type - 抢购类型
     */
    public String getrType() {
        return rType;
    }

    /**
     * 设置抢购类型
     *
     * @param rType 抢购类型
     */
    public void setrType(String rType) {
        this.rType = rType;
    }

    /**
     * 获取创建时间
     *
     * @return cjsj - 创建时间
     */
    public String getCjsj() {
        return cjsj;
    }

    /**
     * 设置创建时间
     *
     * @param cjsj 创建时间
     */
    public void setCjsj(String cjsj) {
        this.cjsj = cjsj;
    }

    /**
     * 获取商品轮播图。多个图片使用','分隔存储
     *
     * @return urls - 商品轮播图。多个图片使用','分隔存储
     */
    public String getUrls() {
        return urls;
    }

    /**
     * 设置商品轮播图。多个图片使用','分隔存储
     *
     * @param urls 商品轮播图。多个图片使用','分隔存储
     */
    public void setUrls(String urls) {
        this.urls = urls;
    }

    /**
     * 获取备注一
     *
     * @return bz1 - 备注一
     */
    public String getBz1() {
        return bz1;
    }

    /**
     * 设置备注一
     *
     * @param bz1 备注一
     */
    public void setBz1(String bz1) {
        this.bz1 = bz1;
    }

    /**
     * 获取备注 二
     *
     * @return bz2 - 备注 二
     */
    public String getBz2() {
        return bz2;
    }

    /**
     * 设置备注 二
     *
     * @param bz2 备注 二
     */
    public void setBz2(String bz2) {
        this.bz2 = bz2;
    }

    /**
     * 获取备注三
     *
     * @return bz3 - 备注三
     */
    public String getBz3() {
        return bz3;
    }

    /**
     * 设置备注三
     *
     * @param bz3 备注三
     */
    public void setBz3(String bz3) {
        this.bz3 = bz3;
    }

    public enum InnerColumn {
        id("id"),
        proType("pro_type"),
        proName("pro_name"),
        proPrice("pro_price"),
        proStore("pro_store"),
        proSign("pro_sign"),
        rType("r_type"),
        cjsj("cjsj"),
        urls("urls"),
        bz1("bz1"),
        bz2("bz2"),
        bz3("bz3");

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