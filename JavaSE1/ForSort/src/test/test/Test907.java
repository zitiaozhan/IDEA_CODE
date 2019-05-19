/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Test907
 * Author:   郭新晔
 * Date:     2018/9/7 0007 14:54
 * Description: Test
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package test.test;

/**
 * 〈一句话功能简述〉<br> 
 * 〈Test〉
 *
 * @author 郭新晔
 * @create 2018/9/7 0007
 * @since 1.0.0
 */
public class Test907 {
    interface ForTest{
        int forRun();
    }

    public static void main(String[] args) {
        int a=5;
        int b=6;
        String c="7";
        int d=8;
        System.out.println(a+b+c+d);

        new Test907().test1();

        int x="byuvushu".indexOf("v");
        System.out.println(x);
    }

    class A implements ForTest{
        @Override
        public int forRun(){
            return 1;
        }
    }

    void test1(){
        class A implements ForTest{
            @Override
            public int forRun(){
                return 2;
            }
        }

        test2(new A());
    }
    void test2(ForTest ft){
        System.out.println(ft.forRun());
    }
}