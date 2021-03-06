package com.ldz.biz.controller;

import com.ldz.biz.model.Order;
import com.ldz.biz.model.ProInfo;
import com.ldz.biz.model.WinRecord;
import com.ldz.biz.service.ProInfoService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/proinfo")
public class ProInfoContrl extends BaseController<ProInfo, String> {

    @Autowired
    private ProInfoService service;

    @Override
    protected BaseService<ProInfo, String> getBaseService() {
        return service;
    }

    /**
     * 手动上架一个商品
     * @param id
     * @return
     */
    @PostMapping("/saveOne")
    public ApiResponse<String> saveOne(String id){
        return service.saveOne(id);
    }

    /**
     * 查询商品上一次中奖的信息
     * @param baseid 商品基本信息的id
     */
    @PostMapping("/getLatestPerson")
    public ApiResponse<ProInfo> getLatestPerson(String baseid){
        return service.getLatestPerson(baseid);
    }


    /**
     * 手动开奖
     */
    @PostMapping("/kj")
    public ApiResponse<String> updateKj(String id){
        return service.updateKj(id);
    }

    /**
     *  更新商品状态
     */
    @PostMapping("/update")
    public ApiResponse<String> update(ProInfo entity){
        return service.updateEntity(entity);
    }


    @PostMapping("/under")
    public ApiResponse<String> updateUnder(String id){
        return service.updateUnder(id);
    }


    /**
     * 获取上架商品的往期中奖记录
     */
    @PostMapping("/getWinRecord")
    public ApiResponse<String> getWinRecordById(String id,@RequestParam(defaultValue = "1") int pageNum ,@RequestParam(defaultValue = "8") int pageSize){
        return service.getWinRecordById(id,pageNum,pageSize);
    }

    /**
     * 获取中奖纪录
     * @return
     */
    @PostMapping("/winrecord")
    public ApiResponse<WinRecord> getWinRecord(String id ){
        return service.getWinRecord(id);
    }

    /**
     * 根据商品id查询当前商品参与的用户列表
     * @return
     */
    @PostMapping("/getUsers")
    public ApiResponse<List<Order>> getUsers(String id){
        return service.getUsers(id);
    }





}
