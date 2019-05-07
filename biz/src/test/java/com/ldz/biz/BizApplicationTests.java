package com.ldz.biz;

import com.ldz.biz.mapper.ProInfoMapper;
import com.ldz.biz.model.ProBaseinfo;
import com.ldz.biz.model.ProInfo;
import com.ldz.biz.service.OrderService;
import com.ldz.biz.service.ProBaseinfoService;
import com.ldz.biz.service.ProInfoService;
import com.ldz.biz.service.UserService;
import com.ldz.util.commonUtil.*;
import com.ldz.util.redis.RedisTemplateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BizApplicationTests {


    @Autowired
    private UserService userService;
    @Autowired
    private JdbcTemplate template;
    @Autowired
    private RedisTemplateUtil redis;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProInfoMapper proInfoMapper;
    @Autowired
    private ProInfoService proInfoService;
    @Autowired
    private ProBaseinfoService baseinfoService;
    @Autowired
    private SnowflakeIdWorker idWorker;
    @Test
    public void contextLoads() {
    }

    @Test
    public void test() throws IOException, InterruptedException {
        List<ProBaseinfo> all = baseinfoService.findAll();
        List<String> collect = all.stream().map(ProBaseinfo::getId).collect(Collectors.toList());
        for (String s : collect) {
            proInfoService.saveOne(s);
        }
    }

}
