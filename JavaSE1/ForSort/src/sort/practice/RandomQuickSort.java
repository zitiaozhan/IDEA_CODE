/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: RandomQuickSort
 * Author:   郭新晔
 * Date:     2018/9/6 0006 9:41
 * Description: 随机快速排序
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package sort.practice;

/**
 * 〈一句话功能简述〉<br>
 * 〈随机快速排序〉
 *
 * @author 郭新晔
 * @create 2018/9/6 0006
 * @since 1.0.0
 */
public class RandomQuickSort {
    public static void main(String[] args) {
        int[] nums = {
                23, 46, 98, 89, 6, 356, 45, 3426, 6, 765, 76, 86, 7, 6, 55, 354, 4326
        };

        //随机快速排序
        quickSort(nums);

        for (int item : nums) {
            System.out.print(item + "  ");
        }
    }

    /**
     * 随机快速排序
     *
     * @param nums
     */
    static void quickSort(int... nums) {
        if (nums == null || nums.length < 2) {
            return;
        }
        quickSort(nums, 0, nums.length - 1);
    }

    /**
     * 快速排序递归部分
     *
     * @param nums
     * @param l
     * @param r
     */
    static void quickSort(int[] nums, int l, int r) {
        if (l < r) {
            //随机抽取和最后一个元素交换
            swap(nums, l + (int) (Math.random() * (r - l + 1)), r);
            int p[] = partition(nums, l, r);
            quickSort(nums, l, p[0] - 1);
            quickSort(nums, p[1] + 1, r);
        }
    }

    /**
     * 分治算法求等于某数的下标,左边全是小于此树,右边全是大于此树
     *
     * @param nums
     * @param l
     * @param r
     * @return
     */
    static int[] partition(int[] nums, int l, int r) {
        int less = l - 1;
        int more = r;
        while (l < more) {
            if (nums[l] < nums[r]) {
                swap(nums, ++less, l++);
            } else if (nums[l] > nums[r]) {
                swap(nums, --more, l);
            } else {
                l++;
            }
        }
        swap(nums, more, r);
        return new int[]{less + 1, more};
    }

    /**
     * 数组元素交换
     *
     * @param nums
     * @param a
     * @param b
     */
    static void swap(int[] nums, int a, int b) {
        int temp = nums[a];
        nums[a] = nums[b];
        nums[b] = temp;
    }
}