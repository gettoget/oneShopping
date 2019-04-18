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

        List<User> users = userService.findEq(User.InnerColumn.balance, "0");
        for (int i = 0 ; i<1;i++){
            User user = users.get(i);
            Map<String,String> m = new HashMap<>();
            m.put("userId",user.getId());
            m.put("imei","011472001978695");

            m.put("amount","500");
            String post = HttpUtil.post("http://localhost:8088/app/recharge/addBalance", m);
            ApiResponse apiResponse = JsonUtil.toBean(post, ApiResponse.class);
            if (apiResponse.getCode() == 200) {
                m.put("orderType","2");
                m.put("proId","568454951718617088");
                m.put("zfje","19");
                m.put("gmfs","19");
                post = HttpUtil.post("http://localhost:8088/app/order/save", m);
                apiResponse = JsonUtil.toBean(post, ApiResponse.class);
                if(apiResponse.getCode() == 200){
                    SimpleCondition condition = new SimpleCondition(Order.class);
                    condition.eq(Order.InnerColumn.userId,m.get("userId"));
                    condition.eq(Order.InnerColumn.proId, m.get("proId"));
                    condition.eq(Order.InnerColumn.ddzt, "3");
                    List<Order> orders = orderService.findByCondition(condition);
                    Order order = orders.get(0);
                    m.put("id",order.getId());
                    m.put("payPwd","123456");
                    post = HttpUtil.post("http://localhost:8088/app/order/payOrder", m);
                }

            }
        }





    }

}
