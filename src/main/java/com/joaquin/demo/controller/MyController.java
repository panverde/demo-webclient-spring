package com.joaquin.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.joaquin.demo.model.Post;
import com.joaquin.demo.service.ContentService;

import reactor.core.publisher.Mono;

@RestController
public class MyController {

	private ContentService contentService;
	
    MyController(ContentService contentService) {
        this.contentService = contentService;
    }	
	
    @GetMapping("/posts/{id}")
    public Mono<Post> getPost(@PathVariable int id) {
        return contentService.getPost(id);
    }

}
