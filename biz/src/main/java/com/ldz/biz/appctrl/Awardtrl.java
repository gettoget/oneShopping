package com.ldz.biz.appctrl;

import com.ldz.biz.model.Award;
import com.ldz.biz.service.AwardService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/user/award")
public class Awardtrl extends BaseController<Award, String> {
    @Autowired
    private AwardService service;

    @Override
    protected BaseService getBaseService() {
        return service;
    }

    /**
     * 查询用户今日是否抽奖
     */
    @GetMapping("/getAwardRecord")
    public ApiResponse<String> getAwardRecord(){
        return service.getAwardRecord();
    }

    @PostMapping("/save")
    public ApiResponse<String> save(Award entity){
        return service.validAndSave(entity);
    }


}
