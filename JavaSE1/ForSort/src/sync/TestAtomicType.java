/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: TestAtomicType
 * Author:   郭新晔
 * Date:     2019/1/12 0012 22:23
 * Description: 测试原子类型
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package sync;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 〈测试原子类型〉
 *
 * @create 2019/1/12 0012
 */
public class TestAtomicType {
    static int counter = 0;
    static AtomicInteger atomicInteger = new AtomicInteger(0);

    public static void main(String[] args) {
//        testWithAtomic();
        testWithoutAtomic();
    }

    public static void testWithAtomic() {
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 10; j++) {
                        sleep(1000);
                        System.out.println(atomicInteger.incrementAndGet());
                    }
                }
            }).start();
        }
    }

    public static void testWithoutAtomic() {
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 10; j++) {
                        sleep(1000);
                        counter = counter + 1;
                        System.out.println(counter);
                    }
                }
            }).start();
        }
    }

    public static void sleep(int mill) {
        try {
            Thread.sleep(mill);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}