/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: TestOne
 * Author:   郭新晔
 * Date:     2018/5/19 0019 14:10
 * Description: 测试类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package test.test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 〈一句话功能简述〉<br> 
 * 〈测试类〉
 *
 * @author 郭新晔
 * @create 2018/5/19 0019
 * @since 1.0.0
 */
public class TestOne implements B{
    public static void main(String args[]){
        int i;
        TestOne a1=new TestOne();
        i =TestOne.k;
        i=B.k;
        System.out.println("i= "+i);
        //i=new TestStatic().temp;  //使用实例对象可以访问静态成员
        short t=1;
        t+=t;
        System.out.println(t);

        Integer t1;

        //int t2=t1;
        //int t3=1+t1;

        Map<String,String> tt=new ConcurrentHashMap<>();
    }
}
interface B
{
    int k=10;
}

class TestStatic{
    static int temp=100;
}