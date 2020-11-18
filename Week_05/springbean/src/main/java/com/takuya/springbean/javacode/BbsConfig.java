package com.takuya.springbean.javacode;

import com.takuya.springbean.autoannotation.PostDao;
import com.takuya.springbean.autoannotation.PostService;
import com.takuya.springbean.autoannotation.PostServiceImpl;
import com.takuya.springbean.autoannotation.UserDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

@Configuration
@ContextConfiguration(locations = {"classpath:spring/spring-dao.xml","classpath:scan.xml"})
public class BbsConfig {

    private PostDao postdao;
    private UserDao userdao;

    @Bean(name="postservice")
    public PostService getPost() {
        return new PostServiceImpl(postdao, userdao);
    }
}
