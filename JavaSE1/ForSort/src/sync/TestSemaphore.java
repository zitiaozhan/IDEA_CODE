/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: TestSemaphore
 * Author:   郭新晔
 * Date:     2019/1/13 0013 14:22
 * Description: 信号量测试
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package sync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 〈信号量测试〉
 * 信号量是对锁的一种扩展。
 * 不管是对于synchronized还是对于ReentrantLock锁来说，
 * 一次都只允许一个线程获取锁资源，
 * 其余的线程都只能处于等待状态。
 * 而信号量可以指定多个线程同时访问某一个资源，包括锁资源
 *
 * 线程池中的线程在执行完毕之后不会被销毁，而是重回线程池，等待被使用，期间一直处于等待状态，
 * 所以程序不会被结束
 * @create 2019/1/13 0013
 */
public class TestSemaphore {
    public static void main(String[] args) {
        //信号量测试
        //创建能容纳20个线程的线程池
        ExecutorService exec = Executors.newFixedThreadPool(20);
        Semthread sem = new Semthread();
        for (int i = 0; i < 20; i++) {
            exec.submit(sem);
        }

        //启动一次顺序关闭，执行以前提交的任务，但不接受新任务
        exec.shutdown();
    }

    //初始化信号量并指定能同时访问的线程数量，并指定是否使用公平锁
    public static Semaphore sema = new Semaphore(5, true);
    //public static Semaphore sema = new Semaphore(5, false);
    // 当设置为 false 时，此类不对线程获取许可的顺序做任何保证。
    // 特别地，闯入是允许的，
    // 也就是说可以在已经等待的线程前为调用 acquire() 的线程分配一个许可，
    // 从逻辑上说，就是新线程将自己置于等待线程队列的头部。
    // 当公平设置为 true 时，信号量保证对于任何调用获取方法的线程而言，
    // 都按照处理它们调用这些方法的顺序（即先进先出；FIFO）来选择线程、获得许可。

    public static class Semthread implements Runnable {

        @Override
        public void run() {
            try {
                // 尝试获得一个准入的许可，如果无法获得，
                // 线程就会一直等待，直到获得了一个许可
                // 或者当前线程被中断，后者会抛出中断异常
                sema.acquire();
                Thread.sleep(200);
                System.out.println("Hello " + Thread.currentThread().getName());
                // 用于在线程访问资源结束之后释放一个许可
                sema.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}