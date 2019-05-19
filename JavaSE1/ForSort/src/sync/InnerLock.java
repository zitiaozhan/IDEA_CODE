/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: InnerLock
 * Author:   郭新晔
 * Date:     2019/1/12 0012 21:51
 * Description: 内置锁
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package sync;

/**
 * 〈内置锁〉
 *
 * @create 2019/1/12 0012
 */
public class InnerLock {
    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
//            new Thread(new SyncClass1(), "T" + (i + 1)).start();  //非静态方法中synchronized块
//            new Thread(new SyncClass2(), "T" + (i + 1)).start();  //静态同步方法
//            new Thread(new SyncClass3(), "T" + (i + 1)).start();  //成员同步方法
//            new Thread(new SyncClass4(), "T" + (i + 1)).start();  //静态方法中synchronized块
//            new Thread(new SyncClass5(), "T" + (i + 1)).start();  //普通执行
        }
    }
}

/**
 * 内置锁：非静态方法中synchronized块，对象锁
 */
class SyncClass1 implements Runnable {
    byte[] lock = new byte[0];

    @Override
    public void run() {
        handle();
    }

    public void handle() {
        System.out.println("This is SyncClass1 块外! Thread-" + Thread.currentThread().getName());
        synchronized (lock) {
            System.out.println("This is SyncClass1 块内! Thread-" + Thread.currentThread().getName());
            for (int i = 0; i < 6; i++) {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "-Thread: handle" + i);
            }
        }
    }
}
/**
 * 内置锁：静态同步方法
 */
class SyncClass2 implements Runnable {
    @Override
    public void run() {
        System.out.println("This is SyncClass2 块外! Thread-" + Thread.currentThread().getName());
        handle();
    }

    public synchronized static void handle() {
        System.out.println("This is SyncClass2 块内! Thread-" + Thread.currentThread().getName());
        for (int i = 0; i < 6; i++) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "-Thread: handle" + i);
        }
    }
}

/**
 * 内置锁：成员同步方法
 */
class SyncClass3 implements Runnable {
    @Override
    public void run() {
        System.out.println("This is SyncClass3 块外! Thread-" + Thread.currentThread().getName());
        handle();
    }

    public synchronized void handle() {
        System.out.println("This is SyncClass3 块内! Thread-" + Thread.currentThread().getName());
        for (int i = 0; i < 6; i++) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "-Thread: handle" + i);
        }
    }
}
/**
 * 内置锁：对象锁，静态方法中synchronized块
 */
class SyncClass4 implements Runnable {
    static byte[] lock = new byte[0];

    @Override
    public void run() {
        handle();
    }

    public static void handle() {
        System.out.println("This is SyncClass4 块外! Thread-" + Thread.currentThread().getName());
        synchronized (lock) {
            System.out.println("This is SyncClass4 块内! Thread-" + Thread.currentThread().getName());
            for (int i = 0; i < 6; i++) {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "-Thread: handle" + i);
            }
        }
    }
}
/**
 * 不加锁
 */
class SyncClass5 implements Runnable {
    @Override
    public void run() {
        handle();
    }

    public void handle() {
        for (int i = 0; i < 6; i++) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "-Thread: handle" + i);
        }
    }
}