/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Main
 * Author:   郭新晔
 * Date:     2018/11/26 0026 20:51
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package other;

/**
 * 〈〉
 *
 * @create 2018/11/26 0026
 */
public class Main {
    public static int[] movieZero2(int[] nums) {
        int last = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i != 0 && nums[i] != 0) {
                nums[last] = nums[i];
                nums[i] = 0;
                last = nums[last] == 0 ? last : i;
            } else if (i != 0 && nums[i] == 0) {
                last = nums[last] == 0 ? last : i;
            }
        }
        return nums;
    }

    /**
     * 之前的想法不能处理好边界问题
     * @param nums
     * @return
     */
    public static int[] moveZero3(int[] nums) {
        int x = 0, t = 0;
        for (int item : nums) {
            x = item != 0 ? ++x : x;
        }
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0 && t < x) {
                nums[t++] = nums[i];
                nums[i] = t - 1 == i ? nums[i] : 0;
            }
        }
        return nums;
    }

    public static void main(String[] args) {
        int[] nums = {0, 1, 0, 5, 5, 0, 0, 3, 0};
        movieZero2(nums);
        for (int item : nums) {
            System.out.print(item + "  ");
        }
    }
}