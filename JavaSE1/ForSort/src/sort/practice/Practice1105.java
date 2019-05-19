/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Practice1105
 * Author:   郭新晔
 * Date:     2018/11/5 0005 17:54
 * Description: 练习
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package sort.practice;

/**
 * 〈一句话功能简述〉<br>
 * 〈练习〉
 *
 * @author 郭新晔
 * @create 2018/11/5 0005
 * @since 1.0.0
 */
public class Practice1105 {
    public static void swap(int[] nums, int a, int b) {
        if (a == b || nums[a] == nums[b]) {
            return;
        }
        nums[a] = nums[a] ^ nums[b];
        nums[b] = nums[a] ^ nums[b];
        nums[a] = nums[a] ^ nums[b];
    }

    /**
     * 选择排序
     * 时间复杂度O(N2),不稳定
     *
     * @param nums
     */
    public static void selectSort(int... nums) {
        if (nums != null && nums.length > 1) {
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
    }

    /**
     * 直接插入排序算法
     * 时间复杂度O(N2),稳定
     *
     * @param nums
     */
    public static void insertSort(int... nums) {
        if (nums != null && nums.length > 1) {
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
    }

    /**
     * 冒泡排序算法
     * 时间复杂度O(N2),稳定
     *
     * @param nums
     */
    public static void bubbleSort(int... nums) {
        if (nums != null && nums.length > 1) {
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
    }

    /**
     * 希尔排序算法（基于插入排序）
     * 时间复杂度O(N2),不稳定
     *
     * @param nums
     */
    public static void hillSort(int... nums) {
        if (nums != null && nums.length > 1) {
            int incrument = nums.length / 2;
            while (incrument > 0) {
                for (int i = 0; i < nums.length - 1; i++) {
                    for (int j = 0; j < nums.length - incrument; j += incrument) {
                        if (nums[j] > nums[j + incrument]) {
                            swap(nums, j, j + incrument);
                        }
                    }
                }
                incrument /= 2;
            }
        }
    }

    /**
     * 随机快排
     *
     * @param nums
     */
    public static void randomQuickSort(int... nums) {
        if (nums != null && nums.length > 1) {
            randomQuickSortBody(nums, 0, nums.length - 1);
        }
    }

    /**
     * 随机快排实现体
     *
     * @param nums
     * @param l
     * @param r
     */
    public static void randomQuickSortBody(int[] nums, int l, int r) {
        if (l < r) {
            int index = l + (int) (Math.random() * (r - l + 1));
            swap(nums, index, r);
            int[] cur = partition(nums, l, r);
            randomQuickSortBody(nums, l, cur[0] - 1);
            randomQuickSortBody(nums, cur[1] + 1, r);
        }
    }

    /**
     * 经典快排
     *
     * @param nums
     */
    public static void tranditionalQuickSort(int... nums) {
        if (nums != null && nums.length > 1) {
            tranditionalQuickSortBody(nums, 0, nums.length - 1);
        }
    }

    /**
     * 经典快排实现体
     *
     * @param nums
     * @param l
     * @param r
     */
    public static void tranditionalQuickSortBody(int[] nums, int l, int r) {
        if (l < r) {
            int[] cur = partition(nums, l, r);
            tranditionalQuickSortBody(nums, l, cur[0] - 1);
            tranditionalQuickSortBody(nums, cur[1] + 1, r);
        }
    }

    /**
     * 分流
     *
     * @param nums
     * @param l
     * @param r
     * @return
     */
    public static int[] partition(int[] nums, int l, int r) {
        int less = l - 1, more = r;
        while (l < more) {
            if (nums[l] < nums[r]) {
                swap(nums, ++less, l++);
            } else if (nums[l] > nums[r]) {
                swap(nums, l, --more);
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
    public static void mergeSort(int... nums) {
        if (nums != null && nums.length > 1) {
            mergeSortBody(nums, 0, nums.length - 1);
        }
    }

    /**
     * 归并排序实现体
     *
     * @param nums
     * @param l
     * @param r
     */
    public static void mergeSortBody(int[] nums, int l, int r) {
        if (l < r) {
            int mid = l + ((r - l) >> 1);
            mergeSortBody(nums, l, mid);
            mergeSortBody(nums, mid + 1, r);
            merge(nums, l, r, mid);
        }
    }

    /**
     * 归并实现
     *
     * @param nums
     * @param l
     * @param r
     * @param mid
     */
    public static void merge(int[] nums, int l, int r, int mid) {
        int i = 0, left = l, right = mid + 1;
        int[] help = new int[r - l + 1];
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
    public static void heapSort(int... nums) {
        if (nums != null && nums.length > 1) {
            heapSortBody(nums, 0, nums.length - 1);
        }
    }

    /**
     * 堆排序实现体
     *
     * @param nums
     * @param l
     * @param r
     */
    public static void heapSortBody(int[] nums, int l, int r) {
        for (int i = l; i < r; i++) {
            heapInsert(nums, i);
        }
        int size = r - l + 1;
        swap(nums, 0, --size);
        while (size > 0) {
            heaptify(nums, size);
            swap(nums, 0, --size);
        }
    }

    /**
     * 保持大根堆阵型插入大根堆
     *
     * @param nums
     * @param index
     */
    public static void heapInsert(int[] nums, int index) {
        while (nums[index] > nums[(index - 1) / 2]) {
            swap(nums, index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    /**
     * 调整为大根堆
     *
     * @param nums
     * @param size
     */
    public static void heaptify(int[] nums, int size) {
        int index = 0, left = 2 * index + 1, more;
        while (left < size) {
            more = left + 1 < size && nums[left + 1] > nums[left] ? left + 1 : left;
            more = nums[more] > nums[index] ? more : index;
            if (more == index) {
                break;
            }
            swap(nums, index, more);
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
//        tranditionalQuickSort(nums);
//        mergeSort(nums);
//        heapSort(nums);

        for (int item : nums) {
            System.out.print(item + "   ");
        }
        System.out.println();
    }
}