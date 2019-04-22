package com.ldz.biz.config;


import com.ldz.biz.listener.ExpiredListener;
import com.ldz.biz.listener.GroundingListener;
import com.ldz.biz.service.OrderService;
import com.ldz.biz.service.ProInfoService;
import com.ldz.util.redis.RedisTemplateUtil;
import com.ldz.util.spring.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * redis配置
 * @author 李彬彬
 *
 */
@Configuration
@Order(1)
public class RedisConfig {

	@Autowired
	private RedisConnectionFactory redisConnectionFactory;

	@Autowired
	private JedisConfig jedisConfig;

	private RedisTemplateUtil redisTemplateUtil;

	/**
	 * 从外部提取数据的Redis对象
	 * @return
	 */
	@Bean(name="redisOtherDB")
	public RedisTemplateUtil redisTemplateOtherDB(){
		RedisStandaloneConfiguration rf=new RedisStandaloneConfiguration();
		rf.setDatabase(jedisConfig.database);
		rf.setHostName(jedisConfig.host);
		rf.setPort(jedisConfig.port);
		JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jpb=
				(JedisClientConfiguration.JedisPoolingClientConfigurationBuilder)JedisClientConfiguration.builder();
		JedisPoolConfig jedisPoolConfig=new JedisPoolConfig();
		jedisPoolConfig.setMaxIdle(jedisConfig.maxIdle);
		jedisPoolConfig.setMinIdle(jedisConfig.minIdle);
		jedisPoolConfig.setMaxTotal(jedisConfig.maxActive);
		int l=Integer.parseInt(jedisConfig.maxWait.substring(0,jedisConfig.maxWait.length()-2));
		jedisPoolConfig.setMaxWaitMillis(l);
		jpb.poolConfig(jedisPoolConfig);
		JedisConnectionFactory jedisConnectionFactory=new JedisConnectionFactory(rf,jpb.build());


		RedisTemplateUtil bean = new RedisTemplateUtil(jedisConnectionFactory);
		return bean;
	}



	/**
	 * 本项目缓存Redis对象
	 * @return
	 */
	@Bean
	@Primary
	public RedisTemplateUtil redisTemplateDefault(){
		redisTemplateUtil = new RedisTemplateUtil(redisConnectionFactory);
		return redisTemplateUtil;
	}
	/**
	 * redis消息监听器容器
	 * 可以添加多个监听不同话题的redis监听器，只需要把消息监听器和相应的消息订阅处理器绑定，该消息监听器
	 * 通过反射技术调用消息订阅处理器的相关方法进行一些业务处理
	 * @param
	 * @return
	 */
	@Bean //相当于xml中的bean
	public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);

		// 订单支付超时监听
		PatternTopic topic = new PatternTopic("__keyevent@*__:expired");
		OrderService orderService = SpringContextUtil.getBean(OrderService.class);
		ExpiredListener expiredListener = new ExpiredListener(redisTemplateUtil,orderService);

		List<PatternTopic> topicList = new ArrayList<>();
		topicList.add(new PatternTopic("grounding"));
		ProInfoService proInfoService = SpringContextUtil.getBean(ProInfoService.class);
		GroundingListener groundingListener = new GroundingListener(redisTemplateUtil, proInfoService);


		container.addMessageListener(expiredListener,topic);
		container.addMessageListener(groundingListener, topicList);
		//这个container 可以添加多个 messageListener
		return container;
	}

	/**
	 * 可读取yml 配置
	 * @return
	 */
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
