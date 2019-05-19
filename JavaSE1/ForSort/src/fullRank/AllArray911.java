/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: AllArray911
 * Author:   郭新晔
 * Date:     2018/9/11 0011 22:47
 * Description: 数组的全排列
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package fullRank;

/**
 * 〈一句话功能简述〉<br>
 * 〈数组的全排列〉
 *
 * @author 郭新晔
 * @create 2018/9/11 0011
 * @since 1.0.0
 */
public class AllArray911 {
    public static void main(String[] args) {
        int[] nums = {1, 2, 3};
        allArray(nums, 0, nums.length);
    }

    static void allArray(int[] nums, int begin, int end) {
        if (begin == end) {
            for (int item : nums) {
                System.out.print(item + "  ");
            }
            System.out.println();
        }
        for (int i = begin; i < end; i++) {
            if (!isAccept(nums, begin, i)) {
                continue;
            }
            swap(nums, begin, i);
            allArray(nums, begin + 1, end);
            swap(nums, begin, i);
        }
    }

    static void swap(int[] nums, int a, int b) {
        if (nums == null || a == b || nums[a] == nums[b]) {
            return;
        }
        nums[a] = nums[a] ^ nums[b];
        nums[b] = nums[a] ^ nums[b];
        nums[a] = nums[a] ^ nums[b];
    }

    static boolean isAccept(int[] nums, int begin, int end) {
        for (int i = begin; i < end; i++) {
            if (nums[i] == nums[end]) {
                return false;
            }
        }
        return true;
    }
}