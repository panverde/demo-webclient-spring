package com.joaquin.demo.service.impl;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import com.joaquin.demo.model.Post;
import com.joaquin.demo.service.ContentService;

import reactor.core.publisher.Mono;

@Service
public class ContentServiceImpl implements ContentService {

	private static final Logger LOGGER = Logger.getLogger(ContentServiceImpl.class.getName());

	private final WebClient webClient;
	
	public ContentServiceImpl(@Value("${content-service}") String baseURL) {
		this.webClient = WebClient.builder().baseUrl(baseURL)
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
				.filter(logRequest())
				.build();
	}

	@Override
	public Mono<Post> getPost(int id) {
		return webClient.get()
						.uri("posts/{id}", id)
						.retrieve()
						.bodyToMono(Post.class);
	}

	private ExchangeFilterFunction logRequest() {
		return (clientRequest, next) -> {
			LOGGER.info("Request: {} {}" + clientRequest.method() + clientRequest.url());
			clientRequest.headers()
					.forEach((name, values) -> values.forEach(value -> LOGGER.info("{}={}" + name + value)));
			return next.exchange(clientRequest);
		};
	}

}
