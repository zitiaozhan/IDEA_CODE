/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: LifeExpense
 * Author:   郭新晔
 * Date:     2018/5/23 0023 18:55
 * Description: 牛客网模拟笔试
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package test.main;

import java.util.Scanner;

/**
 * 〈一句话功能简述〉<br> 
 * 〈牛客网模拟笔试〉
 *小牛牛为了向他的父母表现他已经长大独立了,他决定搬出去自己居住一段时间。
 * 一个人生活增加了许多花费: 牛牛每天必须吃一个水果并且需要每天支付x元的房屋租金。
 * 当前牛牛手中已经有f个水果和d元钱,牛牛也能去商店购买一些水果,商店每个水果售卖p元。
 * 牛牛为了表现他独立生活的能力,希望能独立生活的时间越长越好,
 * 牛牛希望你来帮他计算一下他最多能独立生活多少天。
 * @author 郭新晔
 * @create 2018/5/23 0023
 * @since 1.0.0
 */
public class LifeExpense {

    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        long x,f,d,p;
        while(input.hasNext()){
            x = input.nextLong();
            f = input.nextLong();
            d = input.nextLong();
            p = input.nextLong();
            getDay(x,f,d,p);
        }
    }
    static void getDay(long rent,long fruitNum,long money,long fruitPrice){
        int day = 0;
        while(money >= 0){
            money -= rent;
            money = fruitNum == 0 ? money - fruitPrice : money;
            fruitNum = fruitNum > 0 ? fruitNum-1 : 0;
            day = money >= 0 ? day+1 : day;
        }
        System.out.println(day);
    }

}