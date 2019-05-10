package com.ldz.biz.appctrl;

import com.ldz.biz.model.ProBaseinfo;
import com.ldz.biz.model.WinRecord;
import com.ldz.biz.service.ProBaseinfoService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/guest/probaseinfo")
public class ProBaseInfoCtrl extends BaseController<ProBaseinfo, String> {

    @Autowired
    private ProBaseinfoService service;

    @Override
    protected BaseService<ProBaseinfo, String> getBaseService() {
        return service;
    }

    @Override
    @PostMapping("/save")
    public ApiResponse<String> save(ProBaseinfo entity){
        return null;
    }


    /**
     * 数据删除方法
     * 如果对数据要求高，请重写该方法或是不直接继承该类，防止数据泄露
     * @param id
     * @return
     */
    @RequestMapping(value="/remove/{pkid}", method={RequestMethod.POST})
    public ApiResponse<String> remove(@PathVariable("pkid")String id){

        return ApiResponse.success();
    }

    /**
     * 批量删除操作
     * @param ids
     * @return
     */
    @RequestMapping(value="/removeIds", method={RequestMethod.POST})
    public ApiResponse<String> remove(String[] ids){
        return null;
    }

    @GetMapping("/winrecords")
    public ApiResponse<List<WinRecord>> getWinRecords(String id ){
        return service.getWinRecord(id);
    }






}
