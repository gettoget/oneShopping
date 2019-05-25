package com.ldz.biz;

import com.ldz.biz.service.UserService;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

public class ServletInitializer extends SpringBootServletInitializer {

	private UserService userService;

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(BizApplication.class);
	}

	// 全局缓存
	@Override
	public void onStartup(ServletContext servletContext) {
		this.logger = LogFactory.getLog(this.getClass());
		final WebApplicationContext rootAppContext = this.createRootApplicationContext(servletContext);
		if(rootAppContext != null) {
			servletContext.addListener(new ContextLoaderListener(rootAppContext) {
				public void contextInitialized(ServletContextEvent event) {
					userService = rootAppContext.getBean(UserService.class);
					userService.initRobot();
					System.out.println("缓存所有机器人");
				}
			});
		} else {
			this.logger.debug("No ContextLoaderListener registered, as createRootApplicationContext() did not return an application context");
		}
	}



}
