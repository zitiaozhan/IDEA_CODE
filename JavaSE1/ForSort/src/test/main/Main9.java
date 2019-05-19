/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Main9
 * Author:   郭新晔
 * Date:     2018/9/21 0021 20:56
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package test.main;

import java.util.Scanner;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author 郭新晔
 * @create 2018/9/21 0021
 * @since 1.0.0
 */
public class Main9 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int x;
        while (input.hasNext()) {
            x = input.nextInt();
            System.out.println(getResult(x));
        }
    }
    static int getResult(int x) {
        if (x==1){
            return 1;
        }
        return x*getResult(x-1);
    }
}