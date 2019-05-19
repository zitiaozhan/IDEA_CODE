/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Main21
 * Author:   郭新晔
 * Date:     2018/10/9 0009 20:53
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
 * @create 2018/10/9 0009
 * @since 1.0.0
 */
public class Main21 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int t, n;
        int[] points;
        String res;
        while (input.hasNext()) {
            t = input.nextInt();
            while (--t > -1) {
                n = input.nextInt();
                points = new int[n];
                for (int i = 0; i < n; i++) {
                    points[i] = input.nextInt();
                }
                res = isTree(points) ? "Yes" : "No";
                System.out.println(res);
            }
        }
    }

    static boolean isTree(int[] points) {
        boolean flag = true;
        int count = 0, sum = 0, n = (points.length - 1) * 2;
        for (int item : points) {
            if (item % 2 != 0) {
                count++;
            }
            sum += item;
        }
        flag = count % 2 == 0 && sum == n ? true : false;
        return flag;
    }
}