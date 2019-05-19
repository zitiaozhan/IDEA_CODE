/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Test906
 * Author:   郭新晔
 * Date:     2018/9/6 0006 22:03
 * Description: Test
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package test.test;

import java.util.Scanner;

/**
 * 〈一句话功能简述〉<br> 
 * 〈Test〉
 *
 * @author 郭新晔
 * @create 2018/9/6 0006
 * @since 1.0.0
 */
public class Test906 {
    //输入两个数,每一队至少有一名队员来自其中一个数,每一队至少三名队员,最多组成多少队伍?
    public static void main(String[] args) {
        x1();
    }
    static void x1(){
        Scanner input=new Scanner(System.in);
        int n,m,num,temp;
        while(input.hasNext()){
            num=0;
            n=input.nextInt();
            m=input.nextInt();
            while (n>0&&m>0&&(n+m)>=3){
                temp=n;
                n=n>=m?n-2:--n;
                m=m>temp?m-2:--m;
                num++;
            }
            System.out.println("num = "+num);
        }
    }
}