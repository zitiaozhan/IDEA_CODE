/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Main22
 * Author:   郭新晔
 * Date:     2018/10/10 0010 22:17
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package test.main;

import java.util.Arrays;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author 郭新晔
 * @create 2018/10/10 0010
 * @since 1.0.0
 */
public class Main22 {
    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 5, 4};
        boolean res = isContinue(nums);
        System.out.println(res);
    }

    static boolean isContinue(int[] nums) {
        Arrays.sort(nums);
        int zero = 0, sum = 0, temp = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                zero++;
                continue;
            }
            if (i != nums.length - 1) {
                temp = nums[i + 1] - nums[i];
                if (temp == 0) {
                    return false;
                }
                sum += temp - 1;
            }
        }
        return zero == sum;
    }
}