/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Main11
 * Author:   郭新晔
 * Date:     2018/9/25 0025 11:31
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
 * @create 2018/9/25 0025
 * @since 1.0.0
 */
public class Main11 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        while (input.hasNext()) {
            String inText = input.nextLine();
            String[] xx = inText.split(" ");
            getResult(xx);
        }
    }

    static void getResult(String[] xx) {
        if (xx.length == 2) {
            String str1 = xx[0];
            String str2 = xx[1];
            if (str2.length() <= str1.length()) {
                int cur1 = 0, cu1 = 0, cur2 = 0, cu2 = str2.length() - 1;
                while (cur1 < str1.length() && cu1 < str1.length() && cur2 < str2.length() && cu2 > -1) {
                    if (str1.charAt(cur1) == str2.charAt(cur2)) {
                        cur1++;
                        cur2++;
                    }
                    if (str1.charAt(cu1) == str2.charAt(cu2)) {
                        cu1++;
                        cu2--;
                    }
                    cur1++;
                    cu1++;
                }
                if (cur2 == str2.length() || cu2 == -1) {
                    System.out.println("TRUE");
                    return;
                }
            }
        }
        System.out.println("FALSE");
    }
}