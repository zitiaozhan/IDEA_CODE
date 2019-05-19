/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: AllArray904
 * Author:   郭新晔
 * Date:     2018/9/4 0004 10:18
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
 * @create 2018/9/4 0004
 * @since 1.0.0
 */
public class AllArray904 {
    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 2};
        allArray(nums, 0, nums.length);
    }

    /**
     * 数组中元素的交换
     *
     * @param nums
     * @param p1
     * @param p2
     */
    static void swap(int[] nums, int p1, int p2) {
        int temp = nums[p1];
        nums[p1] = nums[p2];
        nums[p2] = temp;
    }

    /**
     * 本次排列元素是否能够接收
     *
     * @param nums
     * @param low
     * @param high
     * @return
     */
    static boolean isAccept(int[] nums, int low, int high) {
        for (int i = low; i < high; i++) {
            if (nums[i] == nums[high]) {
                return false;
            }
        }
        return true;
    }

    /**
     * 数组的全排列
     * @param nums
     * @param low
     * @param high
     */
    static void allArray(int[] nums, int low, int high) {
        if (low == high) {
            for (int item : nums) {
                System.out.print(item + "  ");
            }
            System.out.println();
        }
        for (int i = low; i < high; i++) {
            if (!isAccept(nums, low, i)) {
                continue;
            }
            swap(nums, low, i);
            allArray(nums, low + 1, high);
            swap(nums, low, i);
        }
    }
}