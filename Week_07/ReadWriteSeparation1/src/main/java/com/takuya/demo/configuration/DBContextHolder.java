package com.takuya.demo.configuration;

import com.takuya.demo.model.DBTypeEnum;

import java.util.concurrent.atomic.AtomicInteger;

public class DBContextHolder {

    private static final ThreadLocal<DBTypeEnum> contextHolder = new ThreadLocal<>();

    private static final AtomicInteger counter = new AtomicInteger(-1);

    public static void set(DBTypeEnum dbType) {
        contextHolder.set(dbType);
    }

    public static DBTypeEnum get() {
        return contextHolder.get();
    }

    public static void master() {
        set(DBTypeEnum.MASTER);
        System.out.println("Switch to master");
    }

    public static void slave() {
        set(DBTypeEnum.SLAVE);
        System.out.println("Switch to slave");
    }
}
