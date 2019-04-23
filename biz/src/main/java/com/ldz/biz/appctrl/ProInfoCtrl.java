package com.ldz.biz.appctrl;

import com.ldz.biz.model.ProInfo;
import com.ldz.biz.service.ProInfoService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/guest/proinfo")
public class ProInfoCtrl extends BaseController<ProInfo, String> {

    @Autowired
    private ProInfoService service;

    @Override
    protected BaseService<ProInfo, String> getBaseService() {
        return service;
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
   @PostMapping("/getRePager")
    public PageResponse<ProInfo> getRePager(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "8") int pageSize){
       return service.getRePager(pageNum, pageSize);
   }

}
