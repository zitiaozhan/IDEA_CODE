/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Sort906
 * Author:   郭新晔
 * Date:     2018/9/6 0006 9:26
 * Description: 排序算法学习
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package sort.practice;

/**
 * 〈一句话功能简述〉<br>
 * 〈排序算法学习〉
 *
 * @author 郭新晔
 * @create 2018/9/6 0006
 * @since 1.0.0
 */
public class Sort906 {
    public static void main(String[] args) {
        int[] nums = {
                23, 46, 98, 89, 6, 356, 45, 3426, 6, 765, 76, 86, 7, 6, 55, 354, 4326
        };

        //归并排序
        mergeSort(nums);
        //bubbleSort(nums);

        for (int item : nums) {
            System.out.print(item + "  ");
        }
    }

    /**
     * 归并排序算法主体
     *
     * @param nums
     */
    static void mergeSort(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }
        mergeSort(nums, 0, nums.length - 1);
    }

    /**
     * 归并排序算法递归部分
     *
     * @param nums
     * @param l
     * @param r
     */
    static void mergeSort(int[] nums, int l, int r) {
        if (l == r) {
            return;
        }
        int mid = l + ((r - l) >> 1);
        mergeSort(nums, l, mid);
        mergeSort(nums, mid + 1, r);
        merge(nums, l, mid, r);
    }

    /**
     * 数组的拷贝以及外排部分
     *
     * @param nums
     * @param l
     * @param mid
     * @param r
     */
    static void merge(int[] nums, int l, int mid, int r) {
        int[] help = new int[r - l + 1];
        int p1 = l;
        int p2 = mid + 1;
        int i = 0;
        while (p1 <= mid && p2 <= r) {
            help[i++] = nums[p1] < nums[p2] ? nums[p1++] : nums[p2++];
        }
        while (p1 <= mid) {
            help[i++] = nums[p1++];
        }
        while (p2 <= r) {
            help[i++] = nums[p2++];
        }
        //拷贝到原来的数组
        for (i = 0; i < help.length; i++) {
            nums[l + i] = help[i];
        }
    }

    /**
     * 冒泡排序
     * @param nums
     */
    static void bubbleSort(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }
        for (int i=nums.length-1;i>0;i--){
            for (int j=0;j<i;j++){
                if (nums[j]>nums[j+1]){
                    nums[j]=nums[j]^nums[j+1];
                    nums[j+1]=nums[j]^nums[j+1];
                    nums[j]=nums[j]^nums[j+1];
                }
            }
        }
    }
}