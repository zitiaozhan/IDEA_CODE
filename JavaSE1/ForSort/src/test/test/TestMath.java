/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: TestMath
 * Author:   郭新晔
 * Date:     2018/5/16 0016 15:59
 * Description: 测试Math的常用静态方法
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package test.test;

/**
 * 〈一句话功能简述〉<br> 
 * 〈测试Math的常用静态方法〉
 *
 * @author 郭新晔
 * @create 2018/5/16 0016
 * @since 1.0.0
 */
public class TestMath {
    public static void main(String[] args){
        System.out.println(Math.round(-11.5));  //-11
        System.out.println(Math.round(11.5));   //12
        System.out.println(Math.round(-11.4));  //-11
        System.out.println(Math.round(11.4));   //11
        System.out.println(Math.round(-11.6));  //-12
        System.out.println(Math.round(11.6));   //12
        System.out.println(Math.round(-0.4));   //0
        System.out.println(Math.round(-0.5));   //0
        System.out.println(Math.ceil(-0.5));    //-0.0

        long l = 012;
        float f=12;
        System.out.println(~10);            //-11, -n=~n+1/~n=-n-1
    }

    protected static class DD{
    }
}