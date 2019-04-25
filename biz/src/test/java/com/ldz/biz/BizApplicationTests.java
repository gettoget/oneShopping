package com.ldz.biz;

import com.ldz.biz.model.Order;
import com.ldz.biz.model.User;
import com.ldz.biz.service.OrderService;
import com.ldz.biz.service.UserService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.*;
import com.ldz.util.redis.RedisTemplateUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.CharEncoding;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.SortParameters;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private SnowflakeIdWorker idWorker;
    @Test
    public void contextLoads() {
    }

    @Test
    public void test() throws IOException, InterruptedException {





    }

}
