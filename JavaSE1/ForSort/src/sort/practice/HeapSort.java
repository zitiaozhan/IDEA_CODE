/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: HeapSort
 * Author:   郭新晔
 * Date:     2018/9/6 0006 13:00
 * Description: 堆排序
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package sort.practice;

/**
 * 〈一句话功能简述〉<br>
 * 〈堆排序〉
 *
 * @author 郭新晔
 * @create 2018/9/6 0006
 * @since 1.0.0
 */
public class HeapSort {
    static void heapSort(int... nums) {
        if (nums == null || nums.length < 2) {
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            heapInsert(nums, i);
        }
        int size = nums.length;
        swap(nums, 0, --size);
        while (size > 0) {
            heapify(nums, 0, size);
            swap(nums, 0, --size);
        }
    }

    /**
     * 排出最大堆
     * @param nums
     * @param i
     */
    static void heapInsert(int[] nums, int i) {
        while (nums[i] > nums[(i - 1) >> 1]) {
            swap(nums, i, (i - 1) >> 1);
            i = (i - 1) >> 1;
        }
    }

    /**
     * 堆改变后重新调整为最大堆
     * @param nums
     * @param index
     * @param size
     */
    static void heapify(int[] nums, int index, int size) {
        int left = (index << 1) + 1;
        while (left < size) {
            int largest = left + 1 < size && nums[left + 1] > nums[left] ? left + 1 : left;
            largest = largest > index ? largest : index;
            if (largest == index) {
                break;
            }
            swap(nums, largest, index);
            index = largest;
            left = (index << 1) + 1;
        }
    }

    public static void main(String[] args) {
        int[] nums = new int[]{
                15, 46, 20, 5, 1, 454, 51651, 11, 15, 502, 5, 52, 5, 02, 563, 11, 3, 15, 115,
        };

        heapSort(nums);

        for (int item : nums) {
            System.out.print(item + "   ");
        }
        System.out.println();
    }

    static void swap(int[] nums, int a, int b) {
        int temp = nums[a];
        nums[a] = nums[b];
        nums[b] = temp;
    }
}