package io.kimmking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SchoolService {

    @Autowired
    School school;

    public void show() {
        school.ding();
    }
}
