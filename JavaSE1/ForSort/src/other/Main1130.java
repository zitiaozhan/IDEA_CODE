/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Main1130
 * Author:   郭新晔
 * Date:     2018/11/30 0030 14:34
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package other;

/**
 * 〈〉
 * @create 2018/11/30 0030
 */
public class Main1130 {
    private static int num=0;
    public static void getCount(int n){
        if(n==0){
            num++;
            return;
        }else if(n<0){
            return;
        }
        getCount(n-1);
        getCount(n-2);
    }

    public static void main(String[] args) {
        getCount(5);
        System.out.println(num);
    }
}