/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Main3
 * Author:   郭新晔
 * Date:     2018/9/16 0016 20:11
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
 * @create 2018/9/16 0016
 * @since 1.0.0
 */
public class Main3 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int x;
        while (input.hasNext()) {
            x = input.nextInt();
            System.out.println(getResult(x));
        }
    }

    static int getResult(int x) {
        int num = 0;
        int number = 1;
        String str;
        while (number <= x) {
            str = "" + number;
            if (str.contains("2") || str.contains("5") ||
                    str.contains("6") || str.contains("9")) {
                if (!str.contains("3") && !str.contains("4") && !str.contains("7")) {
                    num++;
                }
            }
            number++;
        }
        return num;
    }
}