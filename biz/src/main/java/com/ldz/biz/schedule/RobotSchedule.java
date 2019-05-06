package com.ldz.biz.schedule;

import com.ldz.biz.mapper.OrderMapper;
import com.ldz.biz.mapper.ProInfoMapper;
import com.ldz.biz.model.Order;
import com.ldz.biz.model.OrderList;
import com.ldz.biz.model.ProInfo;
import com.ldz.biz.model.User;
import com.ldz.biz.service.OrderListService;
import com.ldz.biz.service.OrderService;
import com.ldz.biz.service.ProInfoService;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.commonUtil.SnowflakeIdWorker;
import com.ldz.util.redis.RedisTemplateUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class RobotSchedule {

    @Autowired
    private ProInfoService infoService;
    @Autowired
    private ProInfoMapper proInfoMapper;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderListService orderListService;
    @Autowired
    private RedisTemplateUtil redis;
    @Autowired
    private OrderMapper orderMapper;
    @Value("${point}")
    private double point;
    @Value("${people}")
    private int people;

    @Autowired
    private SnowflakeIdWorker idWorker;

    public void saveRobot() {
        // 查询 所有未开奖商品且 商品为 机器人参与商品 的剩余名额
        List<ProInfo> allReprice = proInfoMapper.getAllReprice();

        if (CollectionUtils.isNotEmpty(allReprice)) {

            // key 为 id  value 为 剩余名额
            Map<String, ProInfo> map = allReprice.stream().collect(Collectors.toMap(ProInfo::getId, p -> p));
            for (Map.Entry<String, ProInfo> entry : map.entrySet()) {
                String id = entry.getKey();
                ProInfo info = entry.getValue();

                int rePrice = Integer.parseInt(info.getRePrice());

                //1.生成本次消费商品份数
                int randomMaxNum = Math.max(1, (int) (rePrice * point));
                int num = RandomUtils.nextInt(randomMaxNum);
                if (num == 0) {
                    num = 1;
                }
                //2.生成本次多少个用户参与
                int randomMaxUserNum = RandomUtils.nextInt(people);
                if (randomMaxUserNum == 0) {
                    continue;
                }
                // 随机 机器人用户
                List<User> users = orderMapper.ranUsers(randomMaxUserNum);
                int tmpNum = num;
                List<OrderList> orderLists = new ArrayList<>();
                List<Order> orders = new ArrayList<>();
                for (int i = 0; i < users.size(); i++) {
                    User user = users.get(i);

                    //3.参与的用户,每个用户随机分配商品份数.最终随机的份数不超过本次消费的商品份数
                    //获取单个用户消费份数
                    int pUserNum = RandomUtils.nextInt(tmpNum);
                    if (i == (randomMaxUserNum - 1)) {
                        pUserNum = tmpNum;
                    } else if (pUserNum == 0 && tmpNum > 0) {
                        pUserNum = 1;
                    }else{
                        break;
                    }
                    // 生成订单
                    Order order = new Order();
                    order.setUserName(user.getUserName());
                    order.setZfsj(DateUtils.getNowTime());
                    order.setId(idWorker.nextId()  + "");
                    order.setOrderType("2");
                    order.setProId(id);
                    order.setProName(info.getProName());
                    order.setUserId(user.getId());
                    order.setGmfs(pUserNum+"");
                    order.setZfje(pUserNum+"");
                    order.setCjsj(DateUtils.getNowTime());
                    order.setImei(user.getRegImei());
                    order.setDdzt("0");

                    //用户消费记录,获取中奖号码
                    for (int j = 0; j < pUserNum; j++) {
                        String luckNum = (String) redis.boundListOps(id + "_nums").rightPop();
                        OrderList orderList = new OrderList();
                        orderList.setId(idWorker.nextId() + "");
                        orderList.setOrderId(order.getId());
                        orderList.setProId(id);
                        orderList.setProName(info.getProName());
                        orderList.setUserid(user.getId());
                        orderList.setUserName(user.getUserName());
                        orderList.setYhlx(user.getSource());
                        orderList.setNum(luckNum);
                        orderList.setCjsj(DateUtils.getNowTime());
                        orderLists.add(orderList);
                    }
                    orders.add(order);
                    //扣减本次消费份数剩余额度
                    tmpNum -= pUserNum;
                    if (tmpNum <= 0) {
                        break;
                    }
                }
                // 产品分配完成 更新 添加 sql
                orderListService.saveList(orderLists);
                orderService.saveList(orders);
                orderMapper.minusRePrice(num, id);
            }
        }

    }

}
