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
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author 郭新晔
 * @create 2018/9/28 0028
 * @since 1.0.0
 */
public class Main14 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int num;
        while (input.hasNext()) {
            num = input.nextInt();
            System.out.println(getResult(num));
        }
    }

    static int getResult(int num) {
        int res = compute(num);
        String str = res + "";
        System.out.println(str + "############");
        res = 0;
        for (int i = str.length() - 1; i > -1; i--) {
            char cc = str.charAt(i);
            if (cc != '0') {
                return res;
            } else {
                res++;
            }
        }
        return res;
    }

    static int compute(int num) {
        if (num == 1) {
            return 1;
        }
        return num * compute(num - 1);
    }
}