package com.troila.cloud.zuul.config;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;

@Component
public class FileServiceFallbackProvider implements FallbackProvider{

	@Override
	public String getRoute() {
		return "fileservice-v1";
	}

	@Override
	public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
		ClientHttpResponse resp = new ClientHttpResponse() {
			@Override
			public HttpHeaders getHeaders() {
				HttpHeaders headers = new HttpHeaders();
				//和body中的内容编码一致，否则容易乱码
		        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		        return headers;
			}
			
			@Override
			public InputStream getBody() throws IOException {
				JsonObject json = new JsonObject();
				json.addProperty("code", 908);
				json.addProperty("status", "server is busy");
				json.addProperty("reasean", "服务器正忙，请稍后再试！");
				return new ByteArrayInputStream(json.toString().getBytes("UTF-8"));
			
			}
			
			@Override
			public String getStatusText() throws IOException {
				return HttpStatus.OK.getReasonPhrase();
			}
			
			@Override
			public HttpStatus getStatusCode() throws IOException {
				return HttpStatus.OK;
			}
			
			@Override
			public int getRawStatusCode() throws IOException {
				return HttpStatus.OK.value();
			}
			
			@Override
			public void close() {
				
			}
		};
		return resp;
	}

}
