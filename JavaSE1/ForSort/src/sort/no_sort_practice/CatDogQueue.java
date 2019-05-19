/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: CatDogQueue
 * Author:   郭新晔
 * Date:     2018/9/9 0009 20:37
 * Description: 猫狗队列
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package sort.no_sort_practice;

/**
 * 〈一句话功能简述〉<br> 
 * 〈猫狗队列〉
 *
 * @author 郭新晔
 * @create 2018/9/9 0009
 * @since 1.0.0
 */
public class CatDogQueue {

}

class Pet{
    private String type;
    public Pet(String type){
        this.type=type;
    }
    public String getType(){
        return this.type;
    }
}

class Dog extends Pet{
    public Dog(){
        super("Dog");
    }
}
class Cat extends Pet{
    public Cat(){
        super("Cat");
    }
}