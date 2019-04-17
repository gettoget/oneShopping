package com.ldz.biz.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

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
     * 商品状态。上架、下架、已开奖  1 上架  2 下架 3 已开奖
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

    private static final long serialVersionUID = 1L;

    public WinRecord getWinRecord() {
        return winRecord;
    }

    public void setWinRecord(WinRecord winRecord) {
        this.winRecord = winRecord;
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

    public int getCycs() {
        return cycs;
    }

    public void setCycs(int cycs) {
        this.cycs = cycs;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
     * 获取商品基础id。来自于商品基本信息表主键
     *
     * @return pro_baseid - 商品基础id。来自于商品基本信息表主键
     */
    public String getProBaseid() {
        return proBaseid;
    }

    /**
     * 设置商品基础id。来自于商品基本信息表主键
     *
     * @param proBaseid 商品基础id。来自于商品基本信息表主键
     */
    public void setProBaseid(String proBaseid) {
        this.proBaseid = proBaseid;
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
     * 获取商品库存
     *
     * @return pro_store - 商品库存
     */
    public String getProStore() {
        return proStore;
    }

    /**
     * 设置商品库存
     *
     * @param proStore 商品库存
     */
    public void setProStore(String proStore) {
        this.proStore = proStore;
    }

    /**
     * 获取商品状态。上架、下架、已开奖
     *
     * @return pro_zt - 商品状态。上架、下架、已开奖
     */
    public String getProZt() {
        return proZt;
    }

    /**
     * 设置商品状态。上架、下架、已开奖
     *
     * @param proZt 商品状态。上架、下架、已开奖
     */
    public void setProZt(String proZt) {
        this.proZt = proZt;
    }

    /**
     * 获取商品类型，可多个。推荐、上新、热门
     *
     * @return pro_lx - 商品类型，可多个。推荐、上新、热门
     */
    public String getProLx() {
        return proLx;
    }

    /**
     * 设置商品类型，可多个。推荐、上新、热门
     *
     * @param proLx 商品类型，可多个。推荐、上新、热门
     */
    public void setProLx(String proLx) {
        this.proLx = proLx;
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
     * 获取剩余名额
     *
     * @return re_price - 剩余名额
     */
    public String getRePrice() {
        return rePrice;
    }

    /**
     * 设置剩余名额
     *
     * @param rePrice 剩余名额
     */
    public void setRePrice(String rePrice) {
        this.rePrice = rePrice;
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
     * 获取上架时间
     *
     * @return cjsj - 上架时间
     */
    public String getCjsj() {
        return cjsj;
    }

    /**
     * 设置上架时间
     *
     * @param cjsj 上架时间
     */
    public void setCjsj(String cjsj) {
        this.cjsj = cjsj;
    }

    /**
     * 获取开奖时间
     *
     * @return kjsj - 开奖时间
     */
    public String getKjsj() {
        return kjsj;
    }

    /**
     * 设置开奖时间
     *
     * @param kjsj 开奖时间
     */
    public void setKjsj(String kjsj) {
        this.kjsj = kjsj;
    }

    /**
     * 获取更新时间
     *
     * @return gxsj - 更新时间
     */
    public String getGxsj() {
        return gxsj;
    }

    /**
     * 设置更新时间
     *
     * @param gxsj 更新时间
     */
    public void setGxsj(String gxsj) {
        this.gxsj = gxsj;
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
     * 获取参与的真实用户数
     *
     * @return cyyhs - 参与的真实用户数
     */
    public String getCyyhs() {
        return cyyhs;
    }

    /**
     * 设置参与的真实用户数
     *
     * @param cyyhs 参与的真实用户数
     */
    public void setCyyhs(String cyyhs) {
        this.cyyhs = cyyhs;
    }

    /**
     * 获取中奖号码
     *
     * @return zjhm - 中奖号码
     */
    public String getZjhm() {
        return zjhm;
    }

    /**
     * 设置中奖号码
     *
     * @param zjhm 中奖号码
     */
    public void setZjhm(String zjhm) {
        this.zjhm = zjhm;
    }

    /**
     * 获取中奖人id
     *
     * @return user_id - 中奖人id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置中奖人id
     *
     * @param userId 中奖人id
     */
    public void setUserId(String userId) {
        this.userId = userId;
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