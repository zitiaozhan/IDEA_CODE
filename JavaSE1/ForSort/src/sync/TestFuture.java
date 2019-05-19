/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: TestFuture
 * Author:   郭新晔
 * Date:     2019/1/12 0012 23:24
 * Description: 测试Future
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package sync;

import java.util.concurrent.*;

/**
 * 〈测试Future〉
 * 1. 返回异步结果
 * 2. 阻塞等待返回结果
 * 3. timeout
 * 4. 获取线程中的Exception
 *
 * @create 2019/1/12 0012
 */
public class TestFuture {

    public static void main(String[] args) {
        testFutrue();
    }

    static void testFutrue() {
        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<Integer> futureValue = service.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                sleep(1000);
                return 1;
            }
        });
        service.shutdown();

        try {
            System.out.println("futureValue=" + futureValue.get());
//            System.out.println("futureValue=" + futureValue.get(500, TimeUnit.MILLISECONDS));
        } catch (Exception e) {
            e.printStackTrace();
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