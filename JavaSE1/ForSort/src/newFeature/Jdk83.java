/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Jdk83
 * Author:   郭新晔
 * Date:     2018/5/16 0016 11:32
 * Description: JDK8新特性
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package newFeature;

import java.util.ArrayList;
import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈JDK8新特性3〉
 *
 * @author 郭新晔
 * @create 2018/5/16 0016
 * @since 1.0.0
 */
public class Jdk83 {
    public static void main(String[] args){
        List<String> stringCollection = new ArrayList<>();
        stringCollection.add("ddd2");
        stringCollection.add("aaa2");
        stringCollection.add("bbb1");
        stringCollection.add("aaa1");
        stringCollection.add("bbb3");
        stringCollection.add("ccc");
        stringCollection.add("bbb2");
        stringCollection.add("ddd1");

        //集合过滤
        stringCollection
                .stream()
                .filter((s) -> s.startsWith("a"))
                .forEach(System.out::println);
        System.out.println("================================");
        //集合排序
        //需要注意的是，排序只创建了一个排列好后的 Stream，
        //而不会影响原有的数据源，排序之后原数据 stringCollection 是不会被修改的
        stringCollection
                .stream()
                .sorted()
                .forEach(System.out::println);
        System.out.println("================================");
        //Map映射
        stringCollection
                .stream()
                .map(String::toUpperCase)
                .sorted()
                .forEach(System.out::println);
        System.out.println("1================================");
        //Match匹配
        boolean sw = stringCollection
                .stream()
                .anyMatch((s)->s.startsWith("a"));
        System.out.println(sw);
        System.out.println("1================================");
        //count计数
        long size = stringCollection
                .stream()
                .filter((s)->s.startsWith("b"))
                .count();
        System.out.println(size);
        System.out.println("================================");
    }
}