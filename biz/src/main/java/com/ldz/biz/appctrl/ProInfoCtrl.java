package com.ldz.biz.appctrl;

import com.github.pagehelper.Page;
import com.ldz.biz.model.CyyhModel;
import com.ldz.biz.model.ProInfo;
import com.ldz.biz.model.WinRecord;
import com.ldz.biz.service.ProInfoService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/guest/proinfo")
public class ProInfoCtrl  {

    @Autowired
    private ProInfoService service;

    /**
     * 查询商品上一次中奖的信息
     * @param baseid 商品基本信息的id
     */
    @PostMapping("/getLatestPerson")
    public ApiResponse<ProInfo> getLatestPerson(String baseid){
        return service.getLatestPerson(baseid);
    }

    /**
     * 查询当前商品的信息 顺便带上你参与的量
     * @param id 抢购商品的id
     */
    @PostMapping("/getProInfo")
    public ApiResponse<ProInfo> getProInfo(String id){
        return service.getProInfo(id);
    }

    /**
     * 获取当前商品参与用户
     * @param id
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("/getUserInfo")
    public ApiResponse<String> getUserInfo(String id, @RequestParam(defaultValue = "1") int pageNum,@RequestParam(defaultValue = "8") int pageSize){
        return service.getUserInfo(id,pageNum,pageSize);
    }

   /*
    即将结束
    */
   @GetMapping("/getRePager")
    public PageResponse<ProInfo> getRePager(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "8") int pageSize){
       return service.getRePager(pageNum, pageSize);
   }
    /**
     * 获取商品上一期的中奖记录
     */
    @PostMapping("/getLastestRecord")
    public ApiResponse<WinRecord> getLastestRecord(String id){
        return service.getLastestRecord(id);
    }

    /**
     * 获取你当前参与的号码
     */
    @PostMapping("/getMyNums")
    public ApiResponse<List<String>> getMyNums(String id){
        return service.getMyNums(id);
    }

    /**
     * 往期中奖列表
     * @return
     */
    @PostMapping("/winrecords")
    public PageResponse<WinRecord> winrecords(String id, @RequestParam(defaultValue = "1")int pageNum, @RequestParam(defaultValue = "8")int pageSize){
        return service.winrecoeds(id, pageNum, pageSize);
    }

    /**
     * 商品分页接口
     */
    @GetMapping("/newPager")
    public PageResponse<ProInfo> getNewPager(Page<ProInfo> page){
        return service.getNewPager(page);
    }

    @GetMapping("/getCyyh")
    public PageResponse<CyyhModel> getCyyh(@RequestParam(defaultValue =  "1") int pageNum, @RequestParam(defaultValue =  "8") int pageSize, String id ){
        return  service.getCyyh(pageNum, pageSize , id);
    }

    /**
     * 查询某个用户的参与记录
     */
    @GetMapping("/getInUser")
    public PageResponse<CyyhModel> getInUser(String userId, @RequestParam(defaultValue = "0")int pageNum, @RequestParam(defaultValue = "8") int pageSize){
        return service.getInUser(userId, pageNum, pageSize);
    }

    /**
     * 查询某个用户的获奖记录
     */
    @GetMapping("/getWin")
    public PageResponse<CyyhModel> getWin(String userId, @RequestParam(defaultValue = "0")int pageNum, @RequestParam(defaultValue = "8") int pageSize){
        return service.getWin(userId, pageNum, pageSize);
    }


    /**
     * 清空不存在的商品中奖号码
     */
    @GetMapping("/delLuckNums")
    public ApiResponse<String> delLuckNums(){
        return service.delLuckNums();
    }

    /**
     * 重新缓存所有的机器人
     */
    @GetMapping("/initRobot")
    public ApiResponse<String> initRobot(){
        return service.initRobot();
    }

    /**
     * 上架所有商品
     */
    @GetMapping("/saveAll")
    public ApiResponse<String> saveAll(){
        return service.saveAll();
    }

    /**
     * 获取与往期商品相同的最新商品
     */
    @GetMapping("/latestPro")
    public ApiResponse<String> getLatestPro(String id ){
        return service.getLatestPro(id);
    }


}
