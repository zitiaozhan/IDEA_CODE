/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: TestExecutor
 * Author:   郭新晔
 * Date:     2019/1/12 0012 23:00
 * Description: 测试Executor
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package sync;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 〈测试Executor〉
 * 1. 提供一个运行任务的框架。
 * 2. 将任务和如何运行任务解耦。
 * 3. 常用于提供线程池或定时任务服务
 *
 * @create 2019/1/12 0012
 */
public class TestExecutor {
    public static void main(String[] args) {
        testExecutor();
    }

    static void testExecutor() {
        //创建一个可重用固定线程数的线程池，以共享的无界队列方式来运行这些线程
        ExecutorService service = Executors.newFixedThreadPool(2);
        service.submit(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; ++i) {
                    sleep(1000);
                    System.out.println("Executor1 " + i);
                }
            }
        });
        service.submit(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; ++i) {
                    sleep(1000);
                    System.out.println("Executor2 " + i);
                }
            }
        });
        //启动一次顺序关闭，执行以前提交的任务，不再接受新任务
        service.shutdown();

        //isTerminated:如果关闭后所有任务都已完成，则返回 true
        while (!service.isTerminated()){
            sleep(1000);
            System.out.println("Wait for termination!");
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