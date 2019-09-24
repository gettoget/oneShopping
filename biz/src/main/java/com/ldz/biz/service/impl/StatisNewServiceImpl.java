package com.ldz.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ldz.biz.mapper.ExchangeMapper;
import com.ldz.biz.mapper.RechargeMapper;
import com.ldz.biz.model.*;
import com.ldz.biz.service.ProBaseinfoService;
import com.ldz.biz.service.RechargeService;
import com.ldz.biz.service.StatisNewService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.MessageUtils;
import com.ldz.util.exception.RuntimeCheck;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatisNewServiceImpl implements StatisNewService {

    @Autowired
    private RechargeMapper mapper;
    @Autowired
    private ExchangeMapper exchangeMapper;
    @Autowired
    private ProBaseinfoService baseinfoService;
    @Autowired
    private RechargeService rechargeService;

    @Override
    public ApiResponse<Map<String, Object>> statisNewRecharge(String time) {
        //  先查当天的
//        String time = DateTime.now().toString("yyyy-MM-dd");
        List<Recharge> dtCz = mapper.getDtCz(time);
        // 当天充值但是未成功的
        int wcz = CollectionUtils.isEmpty(dtCz) ? 0 :
                dtCz.stream().filter(recharge -> StringUtils.equals(recharge.getCzzt(), "1")).mapToInt(value -> Integer.parseInt(value.getAmonut())).sum();
        // 当天实际充值成功金额
        int dtcz = CollectionUtils.isEmpty(dtCz) ? 0 :
                dtCz.stream().filter(recharge -> StringUtils.equals(recharge.getCzzt(), "2") && StringUtils.equals(recharge.getCzqd(), "1")).mapToInt(value -> Integer.parseInt(value.getAmonut())).sum();
        // 当天充值赠送金币
        int czzs = CollectionUtils.isEmpty(dtCz) ? 0 :
                dtCz.stream().filter(recharge -> StringUtils.equals("2", recharge.getCzzt()) && (Integer.parseInt(recharge.getCzjb()) - Integer.parseInt(recharge.getAmonut()) > 0)).mapToInt(value -> Integer.parseInt(value.getCzjb()) - Integer.parseInt(value.getAmonut())).sum();
        //当天活动奖励金币
        int jljb = CollectionUtils.isEmpty(dtCz) ? 0 :
                dtCz.stream().filter(recharge -> StringUtils.equals(recharge.getCzqd(), "2")).mapToInt(value -> Integer.parseInt(value.getCzjb())).sum();
        // 统计出当天充值记录
        Map<String, Object> dtMap = new HashMap<>();
        dtMap.put("czsb", wcz + "");
        dtMap.put("czcg", dtcz + "");
        dtMap.put("zsjb", czzs + jljb + "");

        return ApiResponse.success(dtMap);
    }

    @Override
    public ApiResponse<Map<String, Object>> statisNewCharge() {
        String time = DateTime.now().toString("yyyy-MM-dd");
        List<Exchange> dtXf = mapper.getDtXf(time);
        // 统计当天消费金币
        double sum = dtXf.stream().mapToDouble(value -> Double.parseDouble(value.getXfjb())).sum();
        Map<String, Object> map = new HashMap<>();
        map.put("day", sum);

        time = DateTime.now().toString("yyyy-MM");
        dtXf = mapper.getDtXf(time);
        double sum1 = dtXf.stream().mapToDouble(value -> Double.parseDouble(value.getXfjb())).sum();
        map.put("mon", sum1);

        return ApiResponse.success(map);
    }

    @Override
    public ApiResponse<List<String>> statisNewWater(String day) {
        if (StringUtils.isBlank(day)) {
            day = "7";
        }
        DateTime now = DateTime.now();
        List<String> list = new ArrayList<>();
        int num = Integer.parseInt(day);
        for (int i = num - 1; i >= 0; i--) {
            String s = now.minusDays(i).toString("yyyy-MM-dd");
            List<Recharge> dtCz = mapper.getDtCz(s);
            // 当天充值但是未成功的
            int wcz = CollectionUtils.isEmpty(dtCz) ? 0 :
                    dtCz.stream().filter(recharge -> StringUtils.equals(recharge.getCzzt(), "1")).mapToInt(value -> Integer.parseInt(value.getAmonut())).sum();
            // 当天实际充值成功金额
            int dtcz = CollectionUtils.isEmpty(dtCz) ? 0 :
                    dtCz.stream().filter(recharge -> StringUtils.equals(recharge.getCzzt(), "2") && StringUtils.equals(recharge.getCzqd(), "1")).mapToInt(value -> Integer.parseInt(value.getAmonut())).sum();
            // 当天充值赠送金币
            int czzs = CollectionUtils.isEmpty(dtCz) ? 0 :
                    dtCz.stream().filter(recharge -> StringUtils.equals("2", recharge.getCzzt()) && (Integer.parseInt(recharge.getCzjb()) - Integer.parseInt(recharge.getAmonut()) > 0)).mapToInt(value -> Integer.parseInt(value.getCzjb()) - Integer.parseInt(value.getAmonut())).sum();
            //当天活动奖励金币
            int jljb = CollectionUtils.isEmpty(dtCz) ? 0 :
                    dtCz.stream().filter(recharge -> StringUtils.equals(recharge.getCzqd(), "2")).mapToInt(value -> Integer.parseInt(value.getCzjb())).sum();
            long sumAmount = exchangeMapper.sumAmount(s);

            String data = s + "|" + wcz + "," + dtcz + "," + (czzs + jljb) + "|" + sumAmount;
            list.add(data);
        }
        return ApiResponse.success(list);
    }

    @Override
    public ApiResponse<List<String>> statisNewUser(String day) {
        if (StringUtils.isBlank(day)) {
            day = "30";
        }
        List<String> result = new ArrayList<>();
        DateTime now = DateTime.now();
        int anInt = Integer.parseInt(day);
        DateTime minusDays = now.minusDays(anInt - 1);
        for (int i = 0; i < anInt; i++) {
            String key = minusDays.plusDays(i).toString("yyyy-MM-dd");
            int zc = mapper.countZc(key);
            key = key + "," + zc;
            result.add(key);
        }
        return ApiResponse.success(result);
    }

    @Override
    public ApiResponse<List<String>> statisCzqd(String day) {
        if (StringUtils.isBlank(day)) {
            day = "30";
        }
//        Map<String,String> map = new HashMap<>();
//        map.put("51491197",	"CIMB");
//        map.put("89220177",	"Danamon");
//        map.put("11111029",	"Alfa");
//        map.put("88561113",	"Permata");
//        map.put("82910053",	"BNI");
//        map.put("89022179",	"Mandiri");
        List<String> result = new ArrayList<>();
        DateTime now = DateTime.now();
        int anInt = Integer.parseInt(day);
        DateTime minusDays = now.minusDays(anInt - 1);
        String start = minusDays.toString("yyyy-MM-dd");
        String end = now.toString("yyyy-MM-dd");
        SimpleCondition condition = new SimpleCondition(Recharge.class);
        condition.gte(Recharge.InnerColumn.cjsj, start);
        condition.lte(Recharge.InnerColumn.cjsj, end + " 23:59:59.999");
        condition.eq(Recharge.InnerColumn.czqd, "1");
        condition.eq(Recharge.InnerColumn.czzt, "2");
        List<Recharge> recharges = mapper.selectByExample(condition);
        Map<String, List<Recharge>> collect =
                recharges.stream().collect(Collectors.groupingBy(recharge -> recharge.getCjsj().substring(0, 10)));
        for (int i = 0; i < anInt; i++) {
            String key = minusDays.plusDays(i).toString("yyyy-MM-dd");
            String value = key;
            if (collect.size() > 0) {
//                value += "," + map.get(key);
                List<Recharge> list = collect.get(key);
                if (list != null) {
                    Map<String, List<Recharge>> listMap =
                            list.stream().filter(recharge -> StringUtils.isNotBlank(recharge.getBz1())).collect(Collectors.groupingBy(Recharge::getBz1));
                    int i1 = listMap.get("51491197") == null ? 0 :
                            listMap.get("51491197").stream().mapToInt(value1 -> Integer.parseInt(value1.getAmonut())).sum();
                    int i2 = listMap.get("89220177") == null ? 0 :
                            listMap.get("89220177").stream().mapToInt(value1 -> Integer.parseInt(value1.getAmonut())).sum();
                    int i3 = listMap.get("11111029") == null ? 0 :
                            listMap.get("11111029").stream().mapToInt(value1 -> Integer.parseInt(value1.getAmonut())).sum();
                    int i4 = listMap.get("88561113") == null ? 0 :
                            listMap.get("88561113").stream().mapToInt(value1 -> Integer.parseInt(value1.getAmonut())).sum();
                    int i5 = listMap.get("82910053") == null ? 0 :
                            listMap.get("82910053").stream().mapToInt(value1 -> Integer.parseInt(value1.getAmonut())).sum();
                    int i6 = listMap.get("89022179") == null ? 0 :
                            listMap.get("89022179").stream().mapToInt(value1 -> Integer.parseInt(value1.getAmonut())).sum();
                    value = key + "," + i1 + "," + i2 + "," + i3 + "," + i4 + "," + i5 + "," + i6;
                } else {
                    value += ",0,0,0,0,0,0";
                }

            } else {
                value += ",0,0,0,0,0,0";
            }
            result.add(value);
        }
        return ApiResponse.success(result);
    }

    @Override
    public ApiResponse<Map<String, String>> statisXzYh() {
        String time = DateTime.now().toString("yyyy-MM-dd");
        // 当天
        int xzYh = mapper.statisXzYh(time);
        // 本月
        time = DateTime.now().toString("yyyy-MM");
        int yxzyh = mapper.statisXzYh(time);
        // 累计
        int ljXzYh = mapper.statisXzYh("%");
        Map<String, String> map = new HashMap<>();
        map.put("day", xzYh + "");
        map.put("mon", yxzyh + "");
        map.put("lj", ljXzYh + "");
        return null;
    }

    @Override
    public ApiResponse<Map<String, String>> statisAllQd(String day) {

        SimpleCondition condition = new SimpleCondition(Recharge.class);
        condition.eq(Recharge.InnerColumn.czqd, "1");
        condition.eq(Recharge.InnerColumn.czzt, "2");
        if (StringUtils.isNotBlank(day)) {
            String string = DateTime.now().toString("yyyy-MM-dd");
            condition.startWith(Recharge.InnerColumn.cjsj, string);
        }
        condition.and().andIsNotNull(Recharge.InnerColumn.bz1.name());
        List<Recharge> recharges = mapper.selectByExample(condition);
        Map<String, List<Recharge>> map = recharges.stream().collect(Collectors.groupingBy(Recharge::getBz1));
        int i1 = map.get("51491197") == null ? 0 :
                map.get("51491197").stream().mapToInt(value1 -> Integer.parseInt(value1.getAmonut())).sum();
        int i2 = map.get("89220177") == null ? 0 :
                map.get("89220177").stream().mapToInt(value1 -> Integer.parseInt(value1.getAmonut())).sum();
        int i3 = map.get("11111029") == null ? 0 :
                map.get("11111029").stream().mapToInt(value1 -> Integer.parseInt(value1.getAmonut())).sum();
        int i4 = map.get("88561113") == null ? 0 :
                map.get("88561113").stream().mapToInt(value1 -> Integer.parseInt(value1.getAmonut())).sum();
        int i5 = map.get("82910053") == null ? 0 :
                map.get("82910053").stream().mapToInt(value1 -> Integer.parseInt(value1.getAmonut())).sum();
        int i6 = map.get("89022179") == null ? 0 :
                map.get("89022179").stream().mapToInt(value1 -> Integer.parseInt(value1.getAmonut())).sum();
        Map<String, String> mm = new HashMap<>();
        mm.put("CIMB", i1 + "");
        mm.put("Danamon", i2 + "");
        mm.put("Alfa", i3 + "");
        mm.put("Permata", i4 + "");
        mm.put("BNI", i5 + "");
        mm.put("Mandiri", i6 + "");
        return ApiResponse.success(mm);
    }

    @Override
    public ApiResponse<Map<String, String>> statisQdJe() {

        SimpleCondition condition = new SimpleCondition(Recharge.class);
        condition.eq(Recharge.InnerColumn.czqd, "1");
//        condition.eq(Recharge.InnerColumn.czzt, "2");
        String string = DateTime.now().toString("yyyy-MM-dd");
        condition.startWith(Recharge.InnerColumn.cjsj, string);
        condition.and().andIsNotNull(Recharge.InnerColumn.bz1.name());
        List<Recharge> recharges = mapper.selectByExample(condition);
        Map<String, List<Recharge>> map = recharges.stream().collect(Collectors.groupingBy(Recharge::getBz1));
        int i1 = map.get("51491197") == null ? 0 :
                map.get("51491197").stream().mapToInt(value1 -> Integer.parseInt(value1.getAmonut())).sum();
        int i2 = map.get("89220177") == null ? 0 :
                map.get("89220177").stream().mapToInt(value1 -> Integer.parseInt(value1.getAmonut())).sum();
        int i3 = map.get("11111029") == null ? 0 :
                map.get("11111029").stream().mapToInt(value1 -> Integer.parseInt(value1.getAmonut())).sum();
        int i4 = map.get("88561113") == null ? 0 :
                map.get("88561113").stream().mapToInt(value1 -> Integer.parseInt(value1.getAmonut())).sum();
        int i5 = map.get("82910053") == null ? 0 :
                map.get("82910053").stream().mapToInt(value1 -> Integer.parseInt(value1.getAmonut())).sum();
        int i6 = map.get("89022179") == null ? 0 :
                map.get("89022179").stream().mapToInt(value1 -> Integer.parseInt(value1.getAmonut())).sum();

        int i7 = map.get("51491197") == null ? 0 :
                (int) map.get("51491197").stream().map(Recharge::getUserId).distinct().count();
        int i8 = map.get("89220177") == null ? 0 :
                (int) map.get("89220177").stream().map(Recharge::getUserId).distinct().count();
        int i9 = map.get("11111029") == null ? 0 :
                (int) map.get("11111029").stream().map(Recharge::getUserId).distinct().count();
        int i10 = map.get("88561113") == null ? 0 :
                (int) map.get("88561113").stream().map(Recharge::getUserId).distinct().count();
        int i11 = map.get("82910053") == null ? 0 :
                (int) map.get("82910053").stream().map(Recharge::getUserId).distinct().count();
        int i12 = map.get("89022179") == null ? 0 :
                (int) map.get("89022179").stream().map(Recharge::getUserId).distinct().count();

        Map<String, String> mm = new HashMap<>();
        mm.put("CIMB", i1 + "," + i7);
        mm.put("Danamon", i2 + "," + i8);
        mm.put("Alfa", i3 + "," + i9);
        mm.put("Permata", i4 + "," + i10);
        mm.put("BNI", i5 + "," + i11);
        mm.put("Mandiri", i6 + "," + i12);
        return ApiResponse.success(mm);
    }

    @Override
    public ApiResponse<Object> spgm() {
        // 商品字段 备注一 用来显示浏览数  备注二用来显示购买数
        // 查询出现有的所有商品(基础)
        // 按天来 ,  可查询
        // 记录每个商品总的浏览数和购买数
        List<Map<String, Map<String, String>>> tj = new ArrayList<>();
        List<ProBaseinfo> all = baseinfoService.findAll();
        all.forEach(proBaseinfo -> {
            Map<String, Map<String, String>> mm = new HashMap<>();
            Map<String, String> map = mapper.getGmAndLl(proBaseinfo.getId());
            map.put("id", proBaseinfo.getId());
            mm.put(proBaseinfo.getProName(), map);

            tj.add(mm);
        });
        return ApiResponse.success(tj);
    }

    @Override
    public ApiResponse<Object> dtLlGm(String time, String id) {
        if (StringUtils.isBlank(time)) {
            time = DateTime.now().toString("yyyy-MM-dd");
        }
        // 查询当天每次已开奖 商品的 浏览数 , 用户购买数 , 以及用户机器购买比例


        return null;
    }

    @Override
    public ApiResponse<Object> dgkj(String time, String proName, String orderBy, int pageNum, int pageSize) {
        // 查询单个商品开奖的详细情况
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String id = request.getParameter("id");
        RuntimeCheck.ifBlank(id, MessageUtils.get("pro.baseIdIsBlank"));
        //  根据条件排序
        if (StringUtils.isBlank(time)) {
            time = null;
        }
        if (StringUtils.isBlank(proName)) {
            proName = null;
        }
        if (StringUtils.isBlank(orderBy)) {
            orderBy = " count desc";
        }
        String finalTime = time;
        String finalProName = proName;
        String finalOrderBy = orderBy;
        PageInfo<ProInfo> info = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> {
            mapper.dgkj(id, finalTime, finalProName, finalOrderBy);
        });
        ApiResponse<Object> res = new ApiResponse<>();
        res.setPage(info);
        return res;
    }

    @Override
    public ApiResponse<Object> dgyh(String time) {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String userId = request.getParameter("id");
        RuntimeCheck.ifBlank(userId, "Silahkan pilih pengguna !");
        // 查询单个用户的最近购买情况
        if (StringUtils.isBlank(time)) {
            time = DateTime.now().toString("yyyy-MM-dd");
        }
        List<ProInfo> dqyh = mapper.dqyh(userId, time);
        return ApiResponse.success(dqyh);
    }

    @Override
    public ApiResponse<Object> zhbh(String id, int pageNum, int pageSize) {
        // 查询单个用户的账户变化情况 , 可以通过查询充值表来
        SimpleCondition condition = new SimpleCondition(Recharge.class);
        condition.eq(Recharge.InnerColumn.userId, id);
        condition.setOrderByClause(" cjsj desc");
        PageInfo<Recharge> info =
                PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> rechargeService.findByCondition(condition));
        ApiResponse<Object> res = new ApiResponse<>();
        res.setPage(info);
        return res;
    }

    @Override
    public ApiResponse<Object> kj(String time, String end, String proName, String orderBy, int pageNum, int pageSize) {
        // 查看一类商品的真是用户购买和浏览数量
        if (StringUtils.isBlank(time)) {
            time = DateTime.now().toString("yyyy-MM-dd") + " 00:00:00.000";
        }else{
            time = time + " 00:00:00.000";
        }
        if(StringUtils.isBlank(end)){
            end = DateTime.now().toString("yyyy-MM-dd") + " 23:59:59.999";
        }else{
            end = end + " 23:59:59.999";
        }
        if (StringUtils.isBlank(proName)) {
            proName = null;
        }
        if (StringUtils.isBlank(orderBy)) {
            orderBy = "s.sm desc";
        }

        String finalTime = time;
        String finalOrderBy = orderBy;
        String finalProName = proName;
        String finalEnd = end;
        PageInfo<ProBaseinfo> info = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> {
            mapper.kj(finalTime, finalEnd, finalProName, finalOrderBy);
        });
        ApiResponse<Object> res = new ApiResponse<>();
        res.setPage(info);

        return res;
    }

    @Override
    public ApiResponse<Object> yhgm(String time, String end, String name, String orderBy, int pageNum, int pageSize) {
        if (StringUtils.isBlank(time)) {
            time = DateTime.now().toString("yyyy-MM-dd") + " 00:00:00.000";
        }else{
            time = time + " 00:00:00.000";
        }
        if(StringUtils.isBlank(end)){
            end = DateTime.now().toString("yyyy-MM-dd" + " 23:59:59.999");
        }else{
            end = end + " 23:59:59.999";
        }
        if (StringUtils.isBlank(name)) {
            name = "";
        }
        if (StringUtils.isBlank(orderBy)) {
            orderBy = "count desc ";
        }
        String finalTime = time;
        String finalName = name;
        String finalOrderBy = orderBy;
        String finalEnd = end;
        PageInfo<User> info =
                PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> mapper.yhgm(finalTime, finalEnd, finalName,
                        finalOrderBy));

        ApiResponse<Object> res = new ApiResponse<>();
        res.setPage(info);
        return res;
    }

}
