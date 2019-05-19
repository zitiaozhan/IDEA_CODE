/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: practice317
 * Author:   郭新晔
 * Date:     2019/3/17 0017 9:18
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package newFeature.practice;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 〈〉
 *
 * @create 2019/3/17 0017
 */
public class practice317 {
    private static List<TestBean> beans = null;

    static {
        beans = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            TestBean testBean = new TestBean();
            testBean.setAge((int) (Math.random() * 100));
            testBean.setChina(i % 2 == 0);
            testBean.setGender((int) (Math.random() * 3));
            testBean.setId(i);
            testBean.setMail(String.format("test-mail%d%d2mail.com", i, i));
            testBean.setName(UUID.randomUUID().toString().substring(0, 6).replace("-", ""));
            testBean.setPhone(String.format("13890%d23%d%d%d", (int) (Math.random() * i), i, (int) (i / 2), (int) (Math.random() * i)));
            testBean.setWeight(Math.random() * 120 + 30);
            beans.add(testBean);
        }
        beans.add(null);
    }

    public static void main(String[] args) {
        test3();
    }

    public static void test1() {
        beans.stream().forEach(item -> print(1, item.toString()));
        beans.stream().filter(Objects::nonNull).filter(item -> item.getAge() > 20 && item.getAge() < 60 || item.isChina())
                .sorted(Comparator.comparingInt(TestBean::getAge))
                .forEach(item -> print(2, item.toString()));
    }

    public static void test2() {
        Map<Integer, TestBean> beanMap = beans.stream().filter(Objects::nonNull).collect(Collectors.toMap(TestBean::getId, TestBean -> TestBean));
        beanMap.forEach((key, value) -> print(key, value.toString()));
        List<String> names = beans.stream().filter(Objects::nonNull).distinct()
                .map(TestBean::getName).collect(Collectors.toList());
        names.forEach(item -> print(3, item));
    }

    public static void test3() {
        beans.stream().filter(Objects::nonNull)
                .filter(item -> item.isChina() && item.getAge() >= 20)
                .forEach(item -> {
                    item.setMail("chinese@china.com");
                });
        beans.stream().filter(Objects::nonNull)
                .forEach(item -> print(4, item.toString()));
    }

    public static void print(int x, String str) {
        System.out.println(String.format("%d, %s", x, str));
    }
}