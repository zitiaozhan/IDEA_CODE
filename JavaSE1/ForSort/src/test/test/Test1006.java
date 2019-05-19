/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Test1006
 * Author:   郭新晔
 * Date:     2018/10/6 0006 15:35
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package test.test;

import java.util.Hashtable;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author 郭新晔
 * @create 2018/10/6 0006
 * @since 1.0.0
 */
public class Test1006 {
    public static void main(String[] args) {
        Map m=new Hashtable<>();
        int x=200;
        Integer xx=new Integer(200);
        Integer xxx=Integer.valueOf(200);
        Integer xxxx=200;
        System.out.println(x==xx);
        System.out.println(x==xxx);
        System.out.println(x==xxxx);
        StringBuilder ss=new StringBuilder();
    }
}