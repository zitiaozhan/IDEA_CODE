/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: TestBlockingQueue
 * Author:   郭新晔
 * Date:     2019/1/12 0012 21:13
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package sync;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 〈阻塞式队列〉
 *
 * @create 2019/1/12 0012
 */
public class TestBlockingQueue {
    public static void main(String[] args) {
        BlockingQueue queue = new ArrayBlockingQueue(2);
        Thread t1 = new Thread(new TaskProducer(queue), "Producer");
        Thread t2 = new Thread(new TaskConsumer(queue), "Consumer1");
        Thread t3 = new Thread(new TaskConsumer(queue), "Consumer2");
        t3.start();
        t1.start();
        t2.start();
    }
}

class TaskProducer implements Runnable {
    private BlockingQueue blockingQueue;

    public TaskProducer(BlockingQueue blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 10; i++) {
                double mill = Math.random();
                Thread.sleep((long) mill * 1000);
                double e = mill * 100;
                System.out.println(Thread.currentThread().getName() + "-Thread: " + e);
                this.blockingQueue.put(e);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class TaskConsumer implements Runnable {
    private BlockingQueue blockingQueue;

    public TaskConsumer(BlockingQueue blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println(Thread.currentThread().getName() + "-Thread: " + this.blockingQueue.take());
                Thread.sleep((long) (Math.random() * 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}