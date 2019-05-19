/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Stream517
 * Author:   郭新晔
 * Date:     2018/5/17 0017 19:41
 * Description: 测试JDK1.8的新特性Stream的功能
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package newFeature;

import java.util.ArrayList;
import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈测试JDK1.8的新特性Stream的功能〉
 *
 * @author 郭新晔
 * @create 2018/5/17 0017
 * @since 1.0.0
 */
public class Stream517 {
    /**
     * 集合元素去除重复元素
     * @param list
     */
    static void testDistinct(List list){
        list
            .stream()
                .distinct()
                .forEach(System.out::println);
        System.out.println("================================");
    }
    /**
     * <方法名称>:创建线程的简单写法
     * <方法功能描述>:
     * <方法参数>:
     * <方法创建时间>:
     */
    public static void myMethod(){
        new Thread(System.out::println,"haha");
    }
    /**
     * 集合元素匹配问题
     * @param list
     */
    static void testMatch(List<String> list){
        boolean any = list
            .stream()
                .anyMatch((s)->s.startsWith("t"));
        boolean all=list
                .stream()
                .allMatch((s)->s.startsWith("t"));
        boolean none=list
                .stream()
                .noneMatch((s)->s.startsWith("t"));
        System.out.println(any);
        System.out.println(all);
        System.out.println(none);
        System.out.println("================================");
    }

    /**
     * 查找集合元素
     * @param list
     */
    static void testFind(List<String> list){
        String first=list
                .stream()
                .findFirst()
                .get();
        String any=list
                .stream()
                .findAny()
                .get();
        System.out.println("first: "+first);
        System.out.println("any: "+any);
        System.out.println("================================");
    }

    /**
     * 获得集合元素个数
     * @param list
     */
    static void testCount(List list){
        Long size=list
                .stream()
                .count();
        System.out.println("size: "+size);
        System.out.println("================================");
    }

    /**
     * 集合元素排序
     * @param list
     */
    static void testSorted(List<String> list){
        list
            .stream()
                .sorted((a,b)->a.compareTo(b))
                .forEach(System.out::println);
    }

    /**
     * 集合元素按条件过滤
     * @param list
     */
    static void testFilter(List<String> list){
        list
            .stream()
                .filter((s)->s.endsWith("e"))
                .sorted()
                .forEach(System.out::println);
    }

    public static void main(String[] args){
        List<String>  stringList = new ArrayList<>();
        stringList.add("Fishermen");
        stringList.add("Mudflat");
        stringList.add("Attracted");
        stringList.add("Tourists");
        stringList.add("Revenue");
        stringList.add("Tourism");
        stringList.add("Province");
        stringList.add("League");
        stringList.add("Fishermen");

        //testDistinct(stringList);
        //testMatch(stringList);
        //testFind(stringList);
        //testCount(stringList);
        //testSorted(stringList);
        //testFilter(stringList);
    }
}