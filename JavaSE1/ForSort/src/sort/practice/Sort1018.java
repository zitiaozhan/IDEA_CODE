/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Sort1018
 * Author:   郭新晔
 * Date:     2018/10/18 0018 19:50
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
 * @create 2018/10/18 0018
 * @since 1.0.0
 */
public class Sort1018 {

    static void swap(int[] nums, int a, int b) {
        if (a == b || nums[a] == nums[b]) {
            return;
        }
        nums[a] = nums[a] ^ nums[b];
        nums[b] = nums[a] ^ nums[b];
        nums[a] = nums[a] ^ nums[b];
    }

    /**
     * 选择排序算法
     *
     * @param nums
     */
    static void selectSort(int... nums) {
        if (nums != null && nums.length > 1) {
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
    }

    /**
     * 直接插入排序算法
     *
     * @param nums
     */
    static void insertSort(int... nums) {
        if (nums != null && nums.length > 1) {
            int i, j, x;
            for (i = 0; i < nums.length - 1; i++) {
                j = i;
                x = nums[i + 1];
                while (j > -1 && nums[j] > x) {
                    nums[j + 1] = nums[j--];
                }
                nums[j + 1] = x;
            }
        }
    }

    /**
     * 冒泡排序算法
     *
     * @param nums
     */
    static void bubbleSort(int... nums) {
        if (nums != null && nums.length > 1) {
            boolean flag = true;
            for (int i = nums.length - 1; i > -1 && flag; i--) {
                flag = false;
                for (int j = 0; j < i; j++) {
                    if (nums[j] > nums[j + 1]) {
                        swap(nums, j, j + 1);
                        flag = true;
                    }
                }
            }
        }
    }

    /**
     * 希尔排序算法
     *
     * @param nums
     */
    static void hillSort(int... nums) {
        if (nums != null && nums.length > 1) {
            int incurment = nums.length / 2;
            while (incurment > 0) {
                for (int i = 0; i < nums.length; i++) {
                    for (int j = 0; j < nums.length - incurment; j += incurment) {
                        if (nums[j] > nums[j + incurment]) {
                            swap(nums, j, j + incurment);
                        }
                    }
                }
                incurment /= 2;
            }
        }
    }

    /**
     * 随机快排
     *
     * @param nums
     */
    static void randomQuickSort(int... nums) {
        if (nums != null && nums.length > 1) {
            randomQuickSortBody(nums, 0, nums.length - 1);
        }
    }

    /**
     * 传统快排
     *
     * @param nums
     */
    static void traditionalQuickSort(int... nums) {
        if (nums != null && nums.length > 1) {
            traditionalQuickSortBody(nums, 0, nums.length - 1);
        }
    }

    static void randomQuickSortBody(int[] nums, int l, int r) {
        if (r > l) {
            int index = l + (int) ((r - l + 1) * Math.random());
            swap(nums, index, r);
            int[] cur = partition(nums, l, r);
            randomQuickSortBody(nums, l, cur[0] - 1);
            randomQuickSortBody(nums, cur[1] + 1, r);
        }
    }

    static void traditionalQuickSortBody(int[] nums, int l, int r) {
        if (r > l) {
            int[] cur = partition(nums, l, r);
            randomQuickSortBody(nums, l, cur[0] - 1);
            randomQuickSortBody(nums, cur[1] + 1, r);
        }
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
        if (nums != null && nums.length > 1) {
            mergeSortBody(nums, 0, nums.length - 1);
        }
    }

    static void mergeSortBody(int[] nums, int l, int r) {
        if (l < r) {
            int mid = l + (r - l) / 2;
            mergeSortBody(nums, l, mid);
            mergeSortBody(nums, mid + 1, r);
            merge(nums, l, mid, r);
        }
    }

    static void merge(int[] nums, int l, int mid, int r) {
        int[] help = new int[r - l + 1];
        int i = 0, left = l, right = mid + 1;
        while (left <= mid && right <= r) {
            help[i++] = nums[left] < nums[right] ? nums[left++] : nums[right++];
        }
        while (left <= mid) {
            help[i++] = nums[left++];
        }
        while (right <= r) {
            help[i++] = nums[right++];
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
    static void heapSort(int... nums) {
        if (nums != null && nums.length > 1) {
            for (int i = 0; i < nums.length; i++) {
                heapInsert(nums, i);
            }
            int size = nums.length;
            swap(nums, 0, --size);
            while (size > 1) {
                heapify(nums, size);
                swap(nums, 0, --size);
            }
        }
    }

    static void heapInsert(int[] nums, int index) {
        while (nums[index] > nums[(index - 1) / 2]) {
            swap(nums, index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    static void heapify(int[] nums, int size) {
        int index = 0, left = 2 * index + 1, more;
        while (left < size) {
            more = left + 1 < size && nums[left + 1] > nums[left] ? left + 1 : left;
            more = nums[more] > nums[index] ? more : index;
            if (more == index) {
                break;
            }
            swap(nums, more, index);
            index = more;
            left = 2 * index + 1;
        }
    }

    public static void main(String[] args) {
        int[] nums = new int[]{
                15, 46, 20, 5, 1, 454, 551, 11, 15, 502, 5, 52, 5, 02, 563, 11, 3, 15, 115,
        };

//        insertSort(nums);
//        selectSort(nums);
//        bubbleSort(nums);
//        hillSort(nums);
//        randomQuickSort(nums);
//        traditionalQuickSort(nums);
//        mergeSort(nums);
        heapSort(nums);

        for (int item : nums) {
            System.out.print(item + "   ");
        }
        System.out.println();
    }

}