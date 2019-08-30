package com.ldz.biz.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

@Table(name = "pro_info")
public class ProInfo implements Serializable {


    @Id
    private String id;

    /**
     * 商品基础id。来自于商品基本信息表主键
     */
    @Column(name = "pro_baseid")
    private String proBaseid;

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
     * 商品库存
     */
    @Column(name = "pro_store")
    private String proStore;

    /**
     * 商品状态。上架、下架、已开奖  1 上架  2 下架 3 待开奖 4 已开奖
     */
    @Column(name = "pro_zt")
    private String proZt;

    /**
     * 商品类型，可多个。推荐、上新、热门  1 推荐 2 上新 3热门
     */
    @Column(name = "pro_lx")
    private String proLx;

    /**
     * 商品标签
     */
    @Column(name = "pro_sign")
    private String proSign;

    /**
     * 剩余名额
     */
    @Column(name = "re_price")
    private String rePrice;

    /**
     * 抢购类型
     */
    @Column(name = "r_type")
    private String rType;

    /**
     * 上架时间
     */
    private String cjsj;

    /**
     * 开奖时间
     */
    private String kjsj;

    /**
     * 更新时间
     */
    private String gxsj;

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
     * 参与的真实用户数
     */
    private String cyyhs;

    /**
     * 中奖号码
     */
    private String zjhm;

    /**
     * 中奖人id
     */
    @Column(name = "user_id")
    private String userId;



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

    /**
     * 用户姓名
     */
    @Transient
    private String userName;
    /**
     * 当前用户参与次数
     */
    @Transient
    private int cycs;

    /**
     * 上期中奖记录
     */
    @Transient
    private WinRecord winRecord;

    @Transient
    List<String> nums;

    @Transient
    private List<String> imgUrls;
    @Transient
    private List<String> coverUrls;
    @Transient
    private List<String> refUrls;
    @Transient
    private String zjfs;
    /**
     * 是否收藏  0 为未收藏   1为已收藏
     */
    @Transient
    private String store;

    /**
     * 中奖消息提醒
     */
    @Transient
    private String tip;

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

    public String getProType() {
        return proType;
    }

    public void setProType(String proType) {
        this.proType = proType;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getProPrice() {
        return proPrice;
    }

    public void setProPrice(String proPrice) {
        this.proPrice = proPrice;
    }

    public String getProStore() {
        return proStore;
    }

    public void setProStore(String proStore) {
        this.proStore = proStore;
    }

    public String getProZt() {
        return proZt;
    }

    public void setProZt(String proZt) {
        this.proZt = proZt;
    }

    public String getProLx() {
        return proLx;
    }

    public void setProLx(String proLx) {
        this.proLx = proLx;
    }

    public String getProSign() {
        return proSign;
    }

    public void setProSign(String proSign) {
        this.proSign = proSign;
    }

    public String getRePrice() {
        return rePrice;
    }

    public void setRePrice(String rePrice) {
        this.rePrice = rePrice;
    }

    public String getrType() {
        return rType;
    }

    public void setrType(String rType) {
        this.rType = rType;
    }

    public String getCjsj() {
        return cjsj;
    }

    public void setCjsj(String cjsj) {
        this.cjsj = cjsj;
    }

    public String getKjsj() {
        return kjsj;
    }

    public void setKjsj(String kjsj) {
        this.kjsj = kjsj;
    }

    public String getGxsj() {
        return gxsj;
    }

    public void setGxsj(String gxsj) {
        this.gxsj = gxsj;
    }

    public String getUrls() {
        return urls;
    }

    public void setUrls(String urls) {
        this.urls = urls;
    }

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

    public String getCyyhs() {
        return cyyhs;
    }

    public void setCyyhs(String cyyhs) {
        this.cyyhs = cyyhs;
    }

    public String getZjhm() {
        return zjhm;
    }

    public void setZjhm(String zjhm) {
        this.zjhm = zjhm;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBz1() {
        return bz1;
    }

    public void setBz1(String bz1) {
        this.bz1 = bz1;
    }

    public String getBz2() {
        return bz2;
    }

    public void setBz2(String bz2) {
        this.bz2 = bz2;
    }

    public String getBz3() {
        return bz3;
    }

    public void setBz3(String bz3) {
        this.bz3 = bz3;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getCycs() {
        return cycs;
    }

    public void setCycs(int cycs) {
        this.cycs = cycs;
    }

    public WinRecord getWinRecord() {
        return winRecord;
    }

    public void setWinRecord(WinRecord winRecord) {
        this.winRecord = winRecord;
    }

    public List<String> getNums() {
        return nums;
    }

    public void setNums(List<String> nums) {
        this.nums = nums;
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

    public String getZjfs() {
        return zjfs;
    }

    public void setZjfs(String zjfs) {
        this.zjfs = zjfs;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public ProInfo() {
    }

    public ProInfo(ProBaseinfo baseinfo) {
        this.proBaseid = baseinfo.getId();
        this.proName = baseinfo.getProName();
        this.proPrice = baseinfo.getProPrice();
        this.proSign = baseinfo.getProSign();
        this.proType = baseinfo.getProType();
        this.rePrice = baseinfo.getProPrice();
        this.rType = baseinfo.getrType();
        this.urls = baseinfo.getUrls();
        this.coverUrl = baseinfo.getCoverUrl();
        this.refUrl = baseinfo.getRefUrl();

    }



    public enum InnerColumn {
        id("id"),
        proBaseid("pro_baseid"),
        proType("pro_type"),
        proName("pro_name"),
        proPrice("pro_price"),
        proStore("pro_store"),
        proZt("pro_zt"),
        proLx("pro_lx"),
        proSign("pro_sign"),
        rePrice("re_price"),
        rType("r_type"),
        cjsj("cjsj"),
        kjsj("kjsj"),
        gxsj("gxsj"),
        urls("urls"),
        cyyhs("cyyhs"),
        zjhm("zjhm"),
        userId("user_id"),
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