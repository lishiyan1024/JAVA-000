package com.takuya.springbean.autoannotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostServiceImpl implements PostService {

    private final PostDao postdao;
    private final UserDao userdao;

    @Autowired
    public PostServiceImpl(PostDao postDao, UserDao userDao) {
        this.postdao = postDao;
        this.userdao = userDao;
    }

    @Override
    public void addpost(String title, String content, int userid) {
        int i = postdao.addPost(title, content, userid);
        int j = userdao.addPost(userid);
        if (i == 1 & j == 1)
            System. out. println ("Posting Successful");
        else
            System. out. println ("posting failure");
    }
}
