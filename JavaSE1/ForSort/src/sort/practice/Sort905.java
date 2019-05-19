/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Sort905
 * Author:   郭新晔
 * Date:     2018/9/5 0005 9:14
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
 * @create 2018/9/5 0005
 * @since 1.0.0
 */
public class Sort905 {
    public static void main(String[] args) {
        int[] nums = {
                15, 56, 5, 9, 83, 2, 595, 9, 78, 203, 48, 62, 48, 56
        };

//        insertSort(nums);
//        selectSort(nums);
//        quickSort(nums,0,nums.length-1);
//        bubbleSort(nums);
//        mergeSort(nums);

        for (int item : nums) {
            System.out.print(item + " ");
        }
    }

    /**
     * 直接插入排序
     * 将后面的元素判断之后往前插
     * @param nums
     */
    static void insertSort(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }
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
     * 直接选择排序算法
     * 每次取最小值放在最左边
     *
     * @param nums
     */
    static void selectSort(int... nums) {
        if (nums == null || nums.length < 2) {
            return;
        }
        int i, j, x;
        for (i = 0; i < nums.length - 1; i++) {
            x = i;//假设下标为i时数值最小
            for (j = i + 1; j < nums.length; j++) {
                x = nums[x] > nums[j] ? j : x;
            }
            if (x != i) {
                swap(nums, x, i);
            }
        }
    }

    /**
     * 快速排序算法
     * 递归,分治算法求等于某数的下标,左边全是小于此树,右边全是大于此树
     * @param nums
     * @param low
     * @param high
     */
    static void quickSort(int[] nums, int low, int high) {
        if (nums == null || nums.length < 2) {
            return;
        }
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
    static void bubbleSort(int... nums) {
        if (nums == null || nums.length < 2) {
            return;
        }
        for (int i = nums.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (nums[j] > nums[j + 1]) {
                    swap(nums, j, j + 1);
                }
            }
        }
    }

    /**
     * 归并排序
     *
     * @param nums
     */
    static void mergeSort(int... nums) {
        if (nums == null || nums.length < 2) {
            return;
        }
        mergeSort(nums, 0, nums.length - 1);
    }

    /**
     * 递归(压栈)主体
     *
     * @param nums
     * @param l
     * @param r
     */
    static void mergeSort(int[] nums, int l, int r) {
        if (l == r) {
            return;
        }
        int mid = l + ((r - l) >> 1);   //可以防止溢出,(l<r且(r-l)<r)
        mergeSort(nums, l, mid);
        mergeSort(nums, mid + 1, r);
        merge(nums, l, mid, r);
    }

    /**
     * 数组的拷贝
     *
     * @param nums
     * @param l
     * @param mid
     * @param r
     */
    static void merge(int[] nums, int l, int mid, int r) {
        int help[] = new int[r - l + 1];      //声明一个辅助数组
        int i = 0;
        int p1 = l;
        int p2 = mid + 1;
        //外排算法
        while (p1 <= mid && p2 <= r) {
            help[i++] = nums[p1] < nums[p2] ? nums[p1++] : nums[p2++];
        }
        while (p1 <= mid) {
            help[i++] = nums[p1++];
        }
        while (p2 <= r) {
            help[i++] = nums[p2++];
        }
        //数组拷贝回去
        for (i = 0; i < help.length; i++) {
            nums[l + i] = help[i];
        }
    }

    /**
     * 数组元素的交换
     *
     * @param nums
     * @param a
     * @param b
     */
    static void swap(int[] nums, int a, int b) {
        //数组元素的交换(异或)
        nums[a] = nums[a] ^ nums[b];
        nums[b] = nums[a] ^ nums[b];
        nums[a] = nums[a] ^ nums[b];
    }
}