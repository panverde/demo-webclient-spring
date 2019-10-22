package com.joaquin.demo.service;

import com.joaquin.demo.model.Post;

import reactor.core.publisher.Mono;

public interface ContentService {
	 public Mono<Post> getPost(int id);
}
