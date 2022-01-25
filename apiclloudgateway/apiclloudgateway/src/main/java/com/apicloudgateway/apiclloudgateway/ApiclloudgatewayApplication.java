package com.apicloudgateway.apiclloudgateway;

import io.netty.resolver.DefaultAddressResolverGroup;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import reactor.netty.http.client.HttpClient;

@SpringBootApplication
public class ApiclloudgatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiclloudgatewayApplication.class, args);
	}
//	@Bean
//	public HttpClient httpClient(){
//		return HttpClient.create().resolver(DefaultAddressResolverGroup.INSTANCE);
//	}
}
