/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Sort912
 * Author:   郭新晔
 * Date:     2018/9/12 0012 10:00
 * Description: 排序算法温习
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package sort.practice;

/**
 * 〈一句话功能简述〉<br>
 * 〈排序算法温习〉
 *
 * @author 郭新晔
 * @create 2018/9/12 0012
 * @since 1.0.0
 */
public class Sort912 {

    /**
     * 直接插入排序
     *
     * @param nums
     */
    static void insertSort(int... nums) {
        int i, j, x;
        for (i = 0; i < nums.length - 1; i++) {
            x = nums[i + 1];
            j = i;
            while (j > -1 && nums[j] > x) {
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
    static void selectSort(int... nums) {
        int i, j, x;
        for (i = 0; i < nums.length; i++) {
            x = i;
            for (j = i + 1; j < nums.length; j++) {
                x = nums[x] < nums[j] ? x : j;
            }
            if (x != i) {
                swap(nums, i, x);
            }
        }
    }

    /**
     * 冒泡排序算法
     *
     * @param nums
     */
    static void bubbleSort(int... nums) {
        boolean flag = true;
        for (int i = nums.length - 1; i > 0 && flag; i--) {
            flag = false;
            for (int j = 0; j < i; j++) {
                if (nums[j] > nums[j + 1]) {
                    swap(nums, j, j + 1);
                    flag = true;
                }
            }
        }
    }

    /**
     * 随机快速排序
     *
     * @param nums
     */
    static void randomQuickSort(int... nums) {
        quickSort(nums, 0, nums.length - 1);
    }

    static void quickSort(int[] nums, int left, int right) {
        if (nums == null || nums.length < 2 || right - left < 1) {
            return;
        }
        int index = left + (int) ((right - left + 1) * Math.random());
        swap(nums, index, right);
        int[] cur = partition(nums, left, right);
        quickSort(nums, left, cur[0] - 1);
        quickSort(nums, cur[1] + 1, right);
    }

    /**
     * 传统快速排序
     *
     * @param nums
     * @param l
     * @param r
     */
    static void trandtionalQuickSort(int[] nums, int l, int r) {
        if (nums == null || nums.length < 2 || r - l < 2) {
            return;
        }
        int[] cur = partition(nums, l, r);
        trandtionalQuickSort(nums, l, cur[0] - 1);
        trandtionalQuickSort(nums, cur[1] + 1, r);
    }

    static int[] partition(int[] nums, int l, int r) {
        int less = l - 1, more = r;
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
        return new int[]{
                less + 1, more
        };
    }

    /**
     * 归并排序
     *
     * @param nums
     * @param l
     * @param r
     */
    static void mergeSort(int[] nums, int l, int r) {
        if (nums == null || nums.length < 2 || r - l < 1) {
            return;
        }
        int mid = l + ((r - l) >> 1);
        mergeSort(nums, l, mid);
        mergeSort(nums, mid + 1, r);
        merge(nums, l, mid, r);
    }

    static void merge(int[] nums, int l, int mid, int r) {
        int less = l, more = mid + 1, index = 0;
        int[] help = new int[r - l + 1];
        while (less <= mid && more <= r) {
            help[index++] = nums[less] < nums[more] ? nums[less++] : nums[more++];
        }
        while (less <= mid) {
            help[index++] = nums[less++];
        }
        while (more <= r) {
            help[index++] = nums[more++];
        }
        for (index = 0; index < help.length; index++) {
            nums[l + index] = help[index];
        }
    }

    /**
     * 堆排序
     *
     * @param nums
     */
    static void heapSort(int[] nums) {
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

    static void heapInsert(int[] nums, int index) {
        while (nums[index] > nums[(index - 1) / 2]) {
            swap(nums, index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    static void heapify(int[] nums, int index, int size) {
        int left = (index * 2) + 1;
        while (left < size) {
            int largest = left + 1 < size && nums[left + 1] > nums[left] ? left + 1 : left;
            largest = nums[largest] > nums[index] ? largest : index;
            if (largest == index) {
                break;
            }
            swap(nums, largest, index);
            index = largest;
            left = (index * 2) + 1;
        }
    }


    public static void main(String[] args) {
        int[] nums = new int[]{
                15, 46, 20, 5, 1, 454, 51651, 11, 15, 502, 5, 52, 5, 02, 563, 11, 3, 15, 115,
        };

        //insertSort(nums);
        //selectSort(nums);
        //bubbleSort(nums);
        //randomQuickSort(nums);
        //trandtionalQuickSort(nums,0,nums.length-1);
        //mergeSort(nums, 0, nums.length - 1);
        heapSort(nums);

        for (int item : nums) {
            System.out.print(item + "   ");
        }
        System.out.println();
    }

    static void swap(int[] nums, int a, int b) {
        if (nums == null || a == b || nums[a] == nums[b]) {
            return;
        }
        nums[a] = nums[a] ^ nums[b];
        nums[b] = nums[a] ^ nums[b];
        nums[a] = nums[a] ^ nums[b];
    }
}