/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Main12
 * Author:   郭新晔
 * Date:     2018/9/27 0027 19:33
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
 * @create 2018/9/27 0027
 * @since 1.0.0
 */
public class Main12 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int r, c;
        while (input.hasNext()) {
            r = input.nextInt();
            c = input.nextInt();
            System.out.println(getResult(r, c));
        }
    }

    static int getResult(int r, int c) {
        if (r == 0 && c == 0) {
            return 1;
        }
        if (r < 0 || c < 0) {
            return 0;
        }
        int x = 0;
        if (c == 0) {
            x += getResult(r - 1, c);
        } else if (r == 0) {
            x += getResult(r, c - 1);
        } else {
            x += getResult(r - 1, c);
            x += getResult(r, c - 1);
        }
        return x;
    }
}