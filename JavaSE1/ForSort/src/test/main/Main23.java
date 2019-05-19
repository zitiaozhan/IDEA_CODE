/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Main23
 * Author:   郭新晔
 * Date:     2018/10/16 0016 19:29
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
 * @create 2018/10/16 0016
 * @since 1.0.0
 */
public class Main23 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String xStr;
        while (input.hasNext()) {
            xStr = input.nextLine();
            getResult(xStr);
        }
    }

    static void getResult(String xStr) {
        String[] strs = xStr.split(" ");
        int[] nums = new int[strs.length];
        for (int i = 0; i < strs.length; i++) {
            nums[i] = Integer.parseInt(strs[i]);
            if (i > 0 && nums[i] - nums[i - 1] < 1) {
                return;
            }
        }
        String res = "";
        int left = nums[0], right, incrument = 1;
        for (int i = 1; i < nums.length; i++) {
            right = nums[i];
            if (right - left != incrument) {
                if (left != nums[i - 1]) {
                    res += left + "->" + nums[i - 1] + ",";
                }
                left = nums[i];
                incrument = 1;
                continue;
            } else if (i == nums.length - 1) {
                res += left + "->" + right + ",";
            }
            incrument++;
        }
        if (res.length() > 1) {
            res = res.substring(0, res.length() - 1);
        }
        System.out.println("[" + res + "]");
    }
}