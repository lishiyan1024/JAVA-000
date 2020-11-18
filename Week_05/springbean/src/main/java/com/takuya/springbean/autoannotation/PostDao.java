package com.takuya.springbean.autoannotation;

import org.springframework.stereotype.Component;

@Component
public interface PostDao {

    public int addPost(String title, String content, int userId);
}
