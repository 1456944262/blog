package com.personal.blog;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class BlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}


	// 后台监控
	@Bean
	public ServletRegistrationBean statViewServlet(){
		ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");

		Map<String, String> initParameters = new HashMap<>();
		// 增加配置项
		initParameters.put("loginUsername","admin");
		initParameters.put("loginPassword","123456");
		initParameters.put("allow","127.0.0.1");     //如果为空所有人都可以访问，如果localhost本机可以访问，如果具体的ip值则具体的值
		// 关于其他配置可以在类ResourceServlet下查看

		// 禁止xu访问       initParameters.put("xu","192.168.1.**");

		// 后台需要有人登陆
		bean.setInitParameters(initParameters);
		return bean;
	}

	@Bean
	public FilterRegistrationBean druidStatFilter() {

		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(
				new WebStatFilter());

		// 添加过滤规则.
		filterRegistrationBean.addUrlPatterns("/*");

		// 添加不需要忽略的格式信息.
		filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
		return filterRegistrationBean;
	}
}
