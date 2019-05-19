/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Main14
 * Author:   郭新晔
 * Date:     2018/9/28 0028 19:34
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package test.main;

import java.util.Scanner;

/**
 * 〈末尾有多少个0〉<br>
 * 〈〉
 * 输入一个正整数n,
 * 求n!(即阶乘)末尾有多少个0？
 * 比如: n = 10; n! = 3628800,所以答案为2
 *
 * @author 郭新晔
 * @create 2018/9/28 0028
 * @since 1.0.0
 */
public class Main15 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int num;
        while (input.hasNext()) {
            num = input.nextInt();
            System.out.println(getResult2(num));
        }
    }

    static int getResult(int num) {
        int res = 0;
        for (int i = num; i >= 5; i--) {
            int tmp = i;
            while (tmp % 5 == 0) {
                res++;
                tmp /= 5;
            }
        }
        return res;
    }

    static int getResult2(int num) {
        int res = 0;
        while (num >= 5) {
            num /= 5;
            res += num;
        }
        return res;
    }
}