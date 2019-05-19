/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Main18
 * Author:   郭新晔
 * Date:     2018/10/8 0008 19:10
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package test.main;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author 郭新晔
 * @create 2018/10/8 0008
 * @since 1.0.0
 */
public class Main18 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int a, b, k;
        while (input.hasNext()) {
            a = input.nextInt();
            b = input.nextInt();
            k = input.nextInt();
            System.out.println(getResult(a, b, k));
        }
    }

    static int getResult(int a, int b, int k) {
        if (a < 1 || a > 9 || b < 1 || b > 9 || k > 1000000) {
            return 0;
        }
        int res = 0, mod;
        mod = k <= 10 ? 10 : k <= 1000 ? 20 : 100;

        ArrayList<String> strs = new ArrayList<>();
        help(k, a, b, "", strs);
        for (String item : strs) {
            String[] xs = item.split("");
            int sum = 0;
            for (int i = 0; i < xs.length; i++) {
                sum += Integer.parseInt(xs[i]);
            }
            if (judge(sum + "", a + "", b + "")) {
                res++;
            }
        }

        return res % mod;
    }

    static void help(int n, int a, int b, String str, ArrayList<String> strs) {
        if (n == 0) {
            strs.add(str);
            return;
        }
        help(n - 1, a, b, str + a, strs);
        help(n - 1, a, b, str + b, strs);
    }

    static boolean judge(String sum, String a, String b) {
        for (String item : sum.split("")) {
            if (!(item.equals(a) || item.equals(b))) {
                return false;
            }
        }
        return true;
    }

}