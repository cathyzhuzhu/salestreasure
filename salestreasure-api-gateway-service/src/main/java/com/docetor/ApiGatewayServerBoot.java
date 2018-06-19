package com.docetor;


import com.docetor.filter.AccessFilter;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import javax.servlet.MultipartConfigElement;

@EnableZuulProxy
@SpringCloudApplication
@Configuration
public class ApiGatewayServerBoot {

	public static void main(String[] args) {
		new SpringApplicationBuilder(ApiGatewayServerBoot.class).web(true).run(args);
	}

	@Bean
	public AccessFilter accessFilter() {
		return new AccessFilter();
	}

	@Bean
	@LoadBalanced
	 RestTemplate restTemplate() {
		return new RestTemplate();
	}

	/**
	 * 文件上传配置
	 *
	 * @return
	 */
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		//  单个数据大小
		factory.setMaxFileSize("204840KB"); // KB,MB
		/// 总上传数据大小
		factory.setMaxRequestSize("102400KB");
		return factory.createMultipartConfig();
	}
}
