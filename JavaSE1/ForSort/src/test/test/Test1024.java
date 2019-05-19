/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Test1024
 * Author:   郭新晔
 * Date:     2018/10/24 0024 21:18
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package test.test;

import java.util.HashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author 郭新晔
 * @create 2018/10/24 0024
 * @since 1.0.0
 */
public class Test1024 {
    public static boolean isValid(String str) {
        if (str == null || "".equals(str)) {
            return false;
        }
        char[] ccs = str.toCharArray();
        int status = 0;
        for (int i = 0; i < ccs.length; i++) {
            if (ccs[i] != '(' && ccs[i] != ')') {
                return false;
            }
            if (ccs[i] == ')' && --status < 0) {
                return false;
            }
            if (ccs[i] == '(') {
                status++;
            }
        }
        return status == 0;
    }

    public static int maxLength(String str) {
        if (str == null || "".equals(str)) {
            return 0;
        }
        char[] ccs = str.toCharArray();
        int[] dp = new int[ccs.length];
        int pre = 0, res = 0;
        for (int i = 1; i < ccs.length; i++) {
            if (ccs[i] == ')') {
                pre = i - dp[i - 1] - 1;
                if (pre >= 0 && ccs[pre] == '(') {
                    dp[i] = dp[i - 1] + 2 + (pre > 0 ? dp[pre - 1] : 0);
                }
            }
            res = Math.max(res, dp[i]);
        }
        return res;
    }

    public static int getSubArrayLength1(int[] nums, int k) {
        int res = 0;
        if (nums != null && nums.length > 0) {
            int l = 0, r = 0, sum = 0, len;
            while (r <= nums.length - 1) {
                sum = getSum(nums, l, r);
                if (sum < k) {
                    r++;
                } else if (sum == k) {
                    len = r++ - l++ + 1;
                    res = res > len ? res : len;
                } else {
                    l++;
                }
            }
        }
        return res;
    }

    public static int getSum(int[] nums, int begin, int end) {
        int res = 0;
        if (nums != null && nums.length > 0 && begin > -1 && end >= begin) {
            for (int i = begin; i <= end; i++) {
                res += nums[i];
            }
        }
        return res;
    }

    public static int getSubArrayLength2(int[] nums, int k) {
        int res = 0, sum = 0, len = 0, x;
        if (nums != null && nums.length > 0) {
            Map<Integer, Integer> sums = new HashMap<>();
            for (int i = 0; i < nums.length; i++) {
                sum += nums[i];
                if (sum == k) {
                    len = i + 1;
                    res = res > len ? res : len;
                } else {
                    if (sums.containsKey(sum - k)) {
                        x = sums.get(sum - k);
                        len = i - x;
                        res = res > len ? res : len;
                    }
                }
                if (!sums.containsKey(sum)) {
                    sums.put(sum, i);
                }
            }
        }
        return res;
    }

    public static int getMaxLength(int[] nums, int k) {
        int res = 0, x = 0, pos = 0, sum = Integer.MIN_VALUE;
        if (nums != null && nums.length > 0) {
            int[] min_value = new int[nums.length];
            min_value[nums.length - 1] = nums[nums.length - 1];
            int[] min_index = new int[nums.length];
            min_index[nums.length - 1] = nums.length - 1;
            for (int i = nums.length - 2; i > -1; i--) {
                min_value[i] = min_value[i + 1] <= 0 ? nums[i] + min_value[i + 1] : nums[i];
                min_index[i] = min_value[i + 1] <= 0 ? min_index[i + 1] : i;
            }
            for (int i = 0; i < nums.length; i++) {
                if (min_value[i] <= k) {
                    x = min_index[i] - i + 1;
                    res = res > x ? res : x;
                    pos = min_index[i] + 1;
                    if (pos >= nums.length) {
                        return res;
                    }
                    sum = min_value[i] + min_value[pos];
                } else {
                    if (sum != Integer.MIN_VALUE) {
                        sum -= nums[i - 1];
                    }
                }
                if (sum != Integer.MIN_VALUE) {
                    while (sum <= k) {
                        x = min_index[pos] - i + 1;
                        res = res > x ? res : x;

                        pos = min_index[pos] + 1;
                        if (pos >= nums.length) {
                            return res;
                        }
                        sum += min_value[pos];
                    }
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        //int[] nums = {3, 2, 1, 1, 4, 6, -10, 0, 0};
        int[] nums = {8, 0, -3, 1, 2, 0, -2, 1, 1, 7, 6};
        System.out.println(getMaxLength(nums, 7));
    }
}