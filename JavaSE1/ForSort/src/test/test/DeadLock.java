/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: DeadLock
 * Author:   郭新晔
 * Date:     2018/11/26 0026 20:52
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package test.test;

/**
 * 〈〉
 * @create 2018/11/26 0026
 */
public class DeadLock {
    private Byte a = 0;
    private Byte b = 0;

    public void funA(){
        synchronized (a){
            try {
                Thread.sleep(10);
                synchronized (b){
                    System.out.println("funA()获得a锁，正在申请b锁...");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public void funB(){
        synchronized (b){
            try {
                Thread.sleep(10);
                synchronized (a){
                    System.out.println("funB()获得b锁，正在申请a锁...");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}