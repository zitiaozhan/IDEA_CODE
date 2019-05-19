/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Jdk81
 * Author:   郭新晔
 * Date:     2018/5/16 0016 10:19
 * Description: JDK8的新特性1
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package newFeature;

import java.util.*;

/**
 * 〈一句话功能简述〉<br>
 * 〈JDK8的新特性1---lambda表达式〉
 * JDK8的新特性: 接口之中可以含有default默认方法以及static静态方法
 * Lambda 表达式中是无法访问到接口的默认方法的
 *
 * @author 郭新晔
 * @create 2018/5/16 0016
 * @since 1.0.0
 */
public class Jdk81 {
    public static void main(String[] args) {
        List<String> strList = Arrays.asList("peter", "anna", "mike", "xenia");
        //为此集合排序
        Collections.sort(strList, String::compareTo);
        for (String str : strList) {
            System.out.print(str + "    ");
        }
        strList.stream().sorted(String::compareTo);
        System.out.println();

        System.out.println("=============================");

        //Converter<String,Integer> converter = (src)->Integer.parseInt(src);
        Converter<String, Integer> converter = (src) -> Integer.valueOf(src);
        Integer dest = converter.convert("6668");
        System.out.println(dest);
    }
}