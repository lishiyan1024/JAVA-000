package com.takuya.springbean.autoannotation;

import org.springframework.stereotype.Component;

@Component
public interface UserDao {

    public int addPost(int userId);
}
