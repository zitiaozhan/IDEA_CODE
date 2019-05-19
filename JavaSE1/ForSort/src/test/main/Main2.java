/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Main2
 * Author:   郭新晔
 * Date:     2018/9/16 0016 19:59
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
public class Main2 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int x;
        while (input.hasNext()) {
            x = input.nextInt();
            System.out.println(getResult(x));
        }
    }

    static int getResult(int x) {
        if (x == 1) {
            return 1;
        }
        if (x == 2) {
            return 2;
        }
        int x1 = 1, x2 = 2, xx = 3, result = 3;
        while (xx < x) {
            x1 = x2;
            x2 = result;
            result = x1 + x2;
            xx++;
        }
        return result;
    }
}