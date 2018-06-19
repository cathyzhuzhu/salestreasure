package com.docetor.domain;

import lombok.Data;

/**
 * 日志输入对象
 */
@Data
public class LogInfoInDto {
	private String ipAddress;//ip地址
	private String requestMethod;//请求方法
	private String requestMethodDesc;//请求方法描述
	private String requestBusiness;//请求业务
	private String channel;//渠道
	private String operator;//操作人
	private  String serviceId;//服务ID
}
