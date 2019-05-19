/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Sort904
 * Author:   郭新晔
 * Date:     2018/9/4 0004 9:13
 * Description: 排序算法温习
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package sort.practice;

import java.util.Arrays;
import java.util.Collection;

/**
 * 〈一句话功能简述〉<br>
 * 〈排序算法温习〉
 *
 * @author 郭新晔
 * @create 2018/9/4 0004
 * @since 1.0.0
 */
public class Sort904 {
    public static void main(String... args) {
        int[] nums = {
                456, 34, 446, 89, 8, 67, 6556, 3436, 567, 14, 54, 98, 9, 4, 1, 7
        };

//        insertSort(nums);
//        selectSort(nums);
//        quickSort(nums,0,nums.length-1);
//        bubbleSort(nums);
        streamSort();

        /*for (int item : nums) {
            System.out.print(item + "  ");
        }*/
    }

    /**
     * 直接插入排序
     *
     * @param nums
     */
    static void insertSort(int[] nums) {
        int i, j, x;
        for (i = 0; i < nums.length - 1; i++) {
            j = i;
            x = nums[i + 1];
            while (j > -1 && x < nums[j]) {
                nums[j + 1] = nums[j--];
            }
            nums[j + 1] = x;
        }
    }

    /**
     * 直接选择排序
     *
     * @param nums
     */
    static void selectSort(int[] nums) {
        int i, j, x, temp;
        for (i = 0; i < nums.length - 1; i++) {
            x = i;
            for (j = i + 1; j < nums.length; j++) {
                x = nums[j] < nums[x] ? j : x;
            }
            if (x != i) {
                temp = nums[x];
                nums[x] = nums[i];
                nums[i] = temp;
            }
        }
    }

    /**
     * 快速排序算法
     *
     * @param nums
     * @param low
     * @param high
     */
    static void quickSort(int[] nums, int low, int high) {
        if (low < high) {
            int i = low, j = high, x = nums[i];
            while (i < j) {
                while (i < j && x <= nums[j]) {
                    j--;
                }
                if (i < j) {
                    nums[i++] = nums[j];
                }
                while (i < j && x > nums[i]) {
                    i++;
                }
                if (i < j) {
                    nums[j--] = nums[i];
                }
            }
            nums[i] = x;
            quickSort(nums, low, i - 1);
            quickSort(nums, i + 1, high);
        }
    }

    /**
     * 冒泡排序算法
     *
     * @param nums
     */
    static void bubbleSort(int[] nums) {
        boolean flag = true;
        for (int i = 0; i < nums.length - 1 && flag; i++) {
            flag = false;
            for (int j = 0; j < nums.length - i - 1; j++) {
                if (nums[j] > nums[j + 1]) {
                    int temp = nums[j + 1];
                    nums[j + 1] = nums[j];
                    nums[j] = temp;
                    flag = true;
                }
            }
        }
    }

    static void streamSort() {
        Collection list = Arrays.asList(456, 34, 446, 89, 8, 67, 6556, 3436, 567, 14, 54, 98, 9, 4, 1, 7);
        list.stream().sorted().forEach(System.out::println);
    }
}