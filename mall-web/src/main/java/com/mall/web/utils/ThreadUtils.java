package com.mall.web.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程工具类
 * @author pan
 * @create 2020-04-29-17:02
 */
public class ThreadUtils {

    private static final ExecutorService ES = Executors.newFixedThreadPool(10);

    public static void execute(Runnable runnable) {
        ES.submit(runnable);
    }
}
