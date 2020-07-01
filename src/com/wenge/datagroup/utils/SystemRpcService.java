package com.wenge.datagroup.utils;

import com.wenge.datagroup.kafka.service.IKafkaService;

import io.jboot.Jboot;
import io.jboot.core.rpc.Jbootrpc;

/**
 * RPC服务注册类
 * @author haobao
 */
public class SystemRpcService {
	private static Jbootrpc jbootrpc;
	public static IKafkaService kafkaService;
	
	static{
		jbootrpc = Jboot.me().getRpc();
		kafkaService = jbootrpc.serviceObtain(IKafkaService.class, "datagroup", "1.0");
	}
	
	
}
