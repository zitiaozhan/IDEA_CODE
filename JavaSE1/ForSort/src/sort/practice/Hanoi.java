/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Hanoi
 * Author:   郭新晔
 * Date:     2018/9/8 0008 23:36
 * Description: 汉诺塔问题
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package sort.practice;

/**
 * 〈一句话功能简述〉<br> 
 * 〈汉诺塔问题〉
 *
 * @author 郭新晔
 * @create 2018/9/8 0008
 * @since 1.0.0
 */
public class Hanoi {
    public static void main(String[] args) {
        process(3,"左","右","中");
    }

    /**
     * 移动圆盘的步骤分解
     * @param n     需要移动的 1-n号圆盘
     * @param from  1-n号圆盘从哪里移动
     * @param to    圆盘移动目标
     * @param help  圆盘移动可以借助的空间
     */
    static void process(int n,String from,String to,String help){
        if (n==1){
            //只剩下一个圆盘,将此圆盘从from移动到to
            System.out.println("Move 1 "+from+" to "+to);
        }else{
            //将1--(n-1)号圆盘从from移动到help,可以借助to
            process(n-1,from,help,to);
            //将n号从from移动到to
            System.out.println("Move "+n+" "+from+" to "+to);
            //将1--(n-1)号圆盘从help移动到to,可以借助from
            process(n-1,help,to,from);
        }
    }
}