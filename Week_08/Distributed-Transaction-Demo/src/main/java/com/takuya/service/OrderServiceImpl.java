package com.takuya.service;

import org.dromara.hmily.annotation.Hmily;
import org.dromara.hmily.annotation.HmilyTCC;

public class OrderServiceImpl implements OrderService {

    @HmilyTCC(confirmMethod = "sayConfirm", cancelMethod = "sayCancel")
    @Override
    public void say() {
        System.out.println("hello world");
    }

    @Override
    public void sayConfirm() {
        System.out.println(" confirm hello world");
    }

    @Override
    public void sayCancel() {
        System.out.println(" cancel hello world");
    }
}
