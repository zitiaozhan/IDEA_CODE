/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: TestThreadLocal
 * Author:   郭新晔
 * Date:     2019/1/12 0012 22:39
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package sync;

import java.util.Random;

/**
 * 〈ThreadLocal测试〉
 * 1. 线程局部变量。即使是一个static成员，每个线程访
 * 问的变量是不同的。
 * 2. 常见于web中存储当前用户到一个静态工具类中，在
 * 线程的任何地方都可以访问到当前线程的用户。
 *
 * @create 2019/1/12 0012
 */
public class TestThreadLocal {
    private static ThreadLocal<Integer> local = new ThreadLocal<>();
    private static int userId;

    public static void main(String[] args) {
        testThreadLocal();
    }

    static void testThreadLocal() {
        for (int i = 0; i < 10; i++) {
            final int tt = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    local.set(tt);
                    sleep(1000);
                    System.out.println(Thread.currentThread().getName() + "-ThreadLocal: " + local.get());
                }
            }).start();
        }

        for (int i = 0; i < 10; i++) {
            final int tt = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    userId = tt;
                    System.out.println("***********NoThreadLocal userId=" + userId);
                    //因为设置了暂停，所以当第一个线程启动时，tt = 9
                    sleep(1000);
                    System.out.println(Thread.currentThread().getName() + "-NoThreadLocal: " + userId);
                }
            }).start();
        }
    }

    public static void sleep(int mill) {
        try {
            Thread.sleep(new Random().nextInt(mill));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}