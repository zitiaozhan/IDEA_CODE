/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Main7
 * Author:   郭新晔
 * Date:     2018/9/21 0021 20:24
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
public class Main7 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String xStr;
        while (input.hasNext()) {
            xStr = input.next();
            System.out.println(getResult(xStr));
        }
    }

    static String getResult(String xStr) {
        if (xStr == null || "".equals(xStr)) {
            return "";
        }
        String res = "";
        char cc;
        if (xStr.contains("_")) {
            for (int i = 0; i < xStr.length(); i++) {
                cc = xStr.charAt(i);
                if (cc == '_') {
                    if (i + 1 < xStr.length()) {
                        res += (xStr.charAt(++i) + "").toUpperCase();
                    }
                } else {
                    res += cc;
                }
            }
        } else {
            for (int i = 0; i < xStr.length(); i++) {
                cc = xStr.charAt(i);
                if (cc < 97) {
                    res += ("_" + cc).toLowerCase();
                } else {
                    res += cc;
                }
            }
        }
        return res;
    }
}