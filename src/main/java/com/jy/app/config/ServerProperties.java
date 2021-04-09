package com.jy.app.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "server")
public class ServerProperties {
	private String port = "8081";

	private String contextPath = "";

}
