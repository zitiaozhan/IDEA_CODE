/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Sort913
 * Author:   郭新晔
 * Date:     2018/9/13 0013 9:40
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
 * @create 2018/9/13 0013
 * @since 1.0.0
 */
public class Sort913 {
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
                swap(nums, x, i);
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
        for (int i = nums.length - 1; i > 0; i--) {
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
     * 随机快排
     *
     * @param nums
     */
    static void randomQuickSort(int... nums) {
        randomQuickSortBody(nums, 0, nums.length - 1);
    }

    static void randomQuickSortBody(int[] nums, int l, int r) {
        if (nums == null || nums.length < 2 || r - l < 1) {
            return;
        }
        int index = l + (int) ((r - l + 1) * Math.random());
        swap(nums, index, r);
        int[] cur = partition(nums, l, r);
        randomQuickSortBody(nums, l, cur[0] - 1);
        randomQuickSortBody(nums, cur[1] + 1, r);
    }

    /**
     * 经典快排
     *
     * @param nums
     * @param l
     * @param r
     */
    static void tranditionalQuickSort(int[] nums, int l, int r) {
        if (nums == null || nums.length < 2 || r - l < 1) {
            return;
        }
        int[] cur = partition(nums, l, r);
        tranditionalQuickSort(nums, l, cur[0] - 1);
        tranditionalQuickSort(nums, cur[1] + 1, r);
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

        return new int[]{less + 1, more};
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
        mergeSortBody(nums, 0, nums.length - 1);
    }

    static void mergeSortBody(int[] nums, int l, int r) {
        if (r - l < 1) {
            return;
        }
        int mid = l + ((r - l) >> 1);
        mergeSortBody(nums, l, mid);
        mergeSortBody(nums, mid + 1, r);
        merge(nums, l, mid, r);
    }

    static void merge(int[] nums, int l, int mid, int r) {
        int[] help = new int[r - l + 1];
        int i = 0, pl = l, pr = mid + 1;
        while (pl <= mid && pr <= r) {
            help[i++] = nums[pl] < nums[pr] ? nums[pl++] : nums[pr++];
        }
        while (pl <= mid) {
            help[i++] = nums[pl++];
        }
        while (pr <= r) {
            help[i++] = nums[pr++];
        }
        for (i = 0; i < help.length; i++) {
            nums[l + i] = help[i];
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
        heapSortBody(nums, 0, nums.length - 1);
    }

    static void heapSortBody(int[] nums, int l, int r) {
        //调整最大堆
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

    static void heapInsert(int[] nums, int x) {
        while (nums[x] > nums[(x - 1) / 2]) {
            swap(nums, x, (x - 1) / 2);
            x = (x - 1) / 2;
        }
    }

    static void heapify(int[] nums, int i, int size) {
        int left = 2 * i + 1;
        while (left < size) {
            int largest = left + 1 < size && nums[left + 1] > nums[left] ? left + 1 : left;
            largest = nums[largest] > nums[i] ? largest : i;
            if (largest == i) {
                break;
            }
            swap(nums, largest, i);
            i = largest;
            left = 2 * i + 1;
        }

    }

    static void swap(int[] nums, int a, int b) {
        if (nums == null || nums.length < 2 || a == b || nums[a] == nums[b]) {
            return;
        }
        nums[a] = nums[a] ^ nums[b];
        nums[b] = nums[a] ^ nums[b];
        nums[a] = nums[a] ^ nums[b];
    }

    public static void main(String[] args) {
        int[] nums = new int[]{
                15, 46, 20, 5, 1, 454, 51651, 11, 15, 502, 5, 52, 5, 02, 563, 11, 3, 15, 115,
        };

//        insertSort(nums);
//        selectSort(nums);
//        bubbleSort(nums);
//        randomQuickSort(nums);
//        tranditionalQuickSort(nums, 0, nums.length - 1);
//        mergeSort(nums);
//        heapSort(nums);

        for (int item : nums) {
            System.out.print(item + "   ");
        }
        System.out.println();
    }
}