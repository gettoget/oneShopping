package com.ldz.biz.appctrl;

import com.github.pagehelper.Page;
import com.ldz.biz.model.RefferRecord;
import com.ldz.biz.service.RefferRecordService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/user/refferrecord")
public class RefferRecordCtrl  {

    @Autowired
    private RefferRecordService service;

    /**
     * 根据用户输入邀请码生成邀请记录
     * @param refCode
     * @return
     */
    @PostMapping("/saveRecord")
    public ApiResponse<String> saveRecord(String refCode){
        return service.saveRecord(refCode);
    }

    /**
     * 当前用户的邀请记录
     */
    @GetMapping("/newPager")
    public PageResponse<RefferRecord> getNewPager(Page<RefferRecord> page){
        return service.getNewPager(page);
    }


}
