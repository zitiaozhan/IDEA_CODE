/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Sort924
 * Author:   郭新晔
 * Date:     2018/9/24 0024 10:21
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
 * @create 2018/9/24 0024
 * @since 1.0.0
 */
public class Sort924 {
    /**
     * 数组内元素的交换
     *
     * @param nums
     * @param a
     * @param b
     */
    static void swap(int[] nums, int a, int b) {
        if (nums == null || nums.length < 2 || a == b || nums[a] == nums[b]) {
            return;
        }
        nums[a] = nums[a] ^ nums[b];
        nums[b] = nums[a] ^ nums[b];
        nums[a] = nums[a] ^ nums[b];
    }

    /**
     * 插入排序
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
     * 选择排序
     *
     * @param nums
     */
    static void selectSort(int... nums) {
        int i, j, x;
        for (i = 0; i < nums.length; i++) {
            x = i;
            for (j = i + 1; j < nums.length; j++) {
                x = nums[j] < nums[x] ? j : x;
            }
            if (x != i) {
                swap(nums, x, i);
            }
        }
    }

    /**
     * 冒泡排序
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
     * 希尔排序
     *
     * @param nums 需要排序的序列
     */
    public static void hillSort(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return;
        }
        //增量
        int incrementNum = nums.length / 2;
        while (incrementNum >= 1) {
            for (int i = 0; i < nums.length; i++) {
                //进行插入排序
                for (int j = i; j < nums.length - incrementNum; j += incrementNum) {
                    if (nums[j] > nums[j + incrementNum]) {
                        swap(nums, j, j + incrementNum);
                    }
                }
            }
            //设置新的增量
            incrementNum = incrementNum >> 1;
        }
    }

    /**
     * 随机快速排序
     *
     * @param nums
     */
    static void randomQuickSort(int... nums) {
        if (nums != null && nums.length > 1) {
            randomQuickSortBody(nums, 0, nums.length - 1);
        }
    }

    /**
     * 经典快速排序
     *
     * @param nums
     */
    static void tranditionalQuickSort(int... nums) {
        if (nums != null && nums.length > 1) {
            tranditionalQuickSortBody(nums, 0, nums.length - 1);
        }
    }

    static void randomQuickSortBody(int[] nums, int l, int r) {
        if (r - l > 0) {
            int index = l + (int) ((r - l + 1) * Math.random());
            swap(nums, index, r);
            int[] cur = partition(nums, l, r);
            randomQuickSortBody(nums, l, cur[0] - 1);
            randomQuickSortBody(nums, cur[1] + 1, r);
        }
    }

    static void tranditionalQuickSortBody(int[] nums, int l, int r) {
        if (r - l > 0) {
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
        if (r - l > 0) {
            int mid = l + ((r - l) >> 1);
            mergeSortBody(nums, l, mid);
            mergeSortBody(nums, mid + 1, r);
            merge(nums, l, mid, r);
        }
    }

    static void merge(int[] nums, int l, int mid, int r) {
        int left = l, right = mid + 1, i = 0;
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
    static void heapSort(int[] nums) {
        if (nums != null && nums.length > 1) {
            for (int i = 0; i < nums.length; i++) {
                heapInsert(nums, i);
            }
            int size = nums.length;
            swap(nums, 0, --size);
            while (size > 0) {
                heapify(nums, size);
                swap(nums, 0, --size);
            }
        }
    }

    /**
     * 插入到堆
     *
     * @param nums
     * @param i
     */
    static void heapInsert(int[] nums, int i) {
        while (nums[i] > nums[(i - 1) / 2]) {
            swap(nums, i, (i - 1) / 2);
            i = (i - 1) / 2;
        }
    }

    /**
     * 重新调整为大根堆
     *
     * @param nums
     * @param size
     */
    static void heapify(int[] nums, int size) {
        int index = 0, largest;
        int left = index * 2 + 1;
        while (left < size) {
            largest = left + 1 < size && nums[left + 1] > nums[left] ? left + 1 : left;
            largest = nums[index] > nums[largest] ? index : largest;
            if (largest == index) {
                break;
            }
            swap(nums, index, largest);
            index = largest;
            left = index * 2 + 1;
        }
    }

    /**
     * 桶排序之-计数排序
     *
     * @param nums
     */
    static void barrelSort1(int[] nums) {
        if (nums != null && nums.length > 1) {
            int max = nums[0];
            for (int i = 1; i < nums.length; i++) {
                max = nums[i] > max ? nums[i] : max;
            }
            int[] buckets = new int[max + 1];
            for (int i = 0; i < nums.length; i++) {
                buckets[nums[i]]++;
            }
            int j = 0;
            for (int i = 0; i < buckets.length; i++) {
                while (buckets[i]-- > 0) {
                    nums[j++] = i;
                }
            }
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
//        barrelSort1(nums);

        for (int item : nums) {
            System.out.print(item + "   ");
        }
        System.out.println();
    }
}