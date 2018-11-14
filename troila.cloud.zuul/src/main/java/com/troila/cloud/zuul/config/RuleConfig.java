package com.troila.cloud.zuul.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.loadbalancer.IRule;
import com.troila.cloud.zuul.rule.UploadRule;

@Configuration
public class RuleConfig {

	@Bean
	public IRule ribbonRule() {
		return new UploadRule();
	}

}
