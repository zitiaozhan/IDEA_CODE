/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Jdk82
 * Author:   郭新晔
 * Date:     2018/5/16 0016 10:42
 * Description: JDK8新特性2
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package newFeature;

import java.util.function.Predicate;

/**
 * 〈一句话功能简述〉<br> 
 * 〈JDK8新特性2〉
 *  Java 8 允许你使用 :: 关键字来传递方法或者构造函数引用
 *  需要使用 Person::new 来获取 Person 类构造函数的引用
 * @author 郭新晔
 * @create 2018/5/16 0016
 * @since 1.0.0
 */
public class Jdk82 {
    public static void main(String[] args){
        Converter<String,Integer> converter = Integer::parseInt;
        Integer dest = converter.convert("6668");
        System.out.println(dest);

        PersonFactory<Person> factory = Person::new;
        Person person = factory.create("Peter","Parker");

        Predicate<String> p=(t)->t.length()>0;
        System.out.println(p.test(""));

    }
}

class Person {
    String firstName;
    String lastName;
    Person() {}
    Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;

        System.out.println(this.firstName+"  "+this.lastName);
    }
}

interface PersonFactory<P extends Person> {
    P create(String firstName, String lastName);
}