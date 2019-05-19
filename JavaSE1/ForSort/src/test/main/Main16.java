/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Main16
 * Author:   郭新晔
 * Date:     2018/9/29 0029 21:04
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
 * @create 2018/9/29 0029
 * @since 1.0.0
 */
public class Main16 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String xStr;
        while (input.hasNext()) {
            xStr = input.nextLine();
            System.out.println(getResult(xStr));
        }
    }

    static boolean getResult(String str) {
        String[] tt = str.split(";");
        if (tt[0] == null || tt[1] == null || tt[0].length() != tt[1].length()) {
            return false;
        }
        String temp = tt[0] + tt[0];
        return temp.contains(tt[1]);
    }
}