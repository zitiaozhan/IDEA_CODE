/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Main20
 * Author:   郭新晔
 * Date:     2018/10/9 0009 18:59
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package test.main;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author 郭新晔
 * @create 2018/10/9 0009
 * @since 1.0.0
 */
public class Main20 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int n, x;
        int[] prices;
        while (input.hasNext()) {
            n = input.nextInt();
            x = input.nextInt();
            prices = new int[n + 1];
            for (int i = 1; i <= n; i++) {
                prices[i] = input.nextInt();
            }
            Arrays.sort(prices);
            System.out.println(getResult(x, prices, 0, 1));
        }
    }

    static int getResult(int x, int[] prices, int money, int i) {
        if (i >= prices.length) {
            return Integer.MAX_VALUE;
        }
        if (x <= 0) {
            return money;
        }
        return Math.min(getResult(x - prices[i], prices, money + prices[i], i + 1), getResult(x, prices, money, i + 1));
    }
}