package com.docetor.domain;

import lombok.Data;

import java.util.Date;

/**
 * 日志输出对象
 */
@Data
public class LogInfoOutDto {
	private String id;//pk
	private String ipAddress;//ip地址
	private String requestMethod;//请求方法
	private String requestMethodDesc;//请求方法描述
	private String requestBusiness;//请求业务
	private String channel;//渠道
	private String operator;//操作人 
	private Date operateTime;//操作时间
	private  String serviceId;//服务ID
}
