/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Sort916
 * Author:   郭新晔
 * Date:     2018/9/16 0016 22:28
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
 * @create 2018/9/16 0016
 * @since 1.0.0
 */
public class Sort916 {

    static void insertSort(int... nums) {
        int i, j, x;
        for (i = 0; i < nums.length - 1; i++) {
            x = nums[i + 1];
            j = i;
            while (j > -1 && x < nums[j]) {
                nums[j + 1] = nums[j--];
            }
            nums[j + 1] = x;
        }
    }

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

    static void randomQuickSort(int[] nums, int l, int r) {
        if (nums == null || r - l < 1) {
            return;
        }
        int index = l + (int) ((r - l + 1) * Math.random());
        swap(nums, index, r);
        int[] cur = partition(nums, l, r);
        randomQuickSort(nums, l, cur[0] - 1);
        randomQuickSort(nums, cur[1] + 1, r);
    }

    static void tranditionalQuickSort(int[] nums, int l, int r) {
        if (nums == null || r - l < 1 || nums.length < 2) {
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

    static void mergeSort(int[] nums, int l, int r) {
        if (nums == null || r - l < 1) {
            return;
        }
        int mid = l + ((r - l) >> 1);
        mergeSort(nums, l, mid);
        mergeSort(nums, mid + 1, r);
        merge(nums, l, mid, r);
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
        int left, largest;
        left = index * 2 + 1;
        while (left < size) {
            largest = left + 1 < size && nums[left + 1] > nums[left] ? left + 1 : left;
            largest = nums[largest] > nums[index] ? largest : index;
            if (largest == index) {
                break;
            }
            swap(nums, index, largest);

            index = largest;
            left = index * 2 + 1;
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
//        randomQuickSort(nums, 0, nums.length - 1);
//        tranditionalQuickSort(nums, 0, nums.length - 1);
//        mergeSort(nums,0,nums.length-1);
        heapSort(nums);

        for (int item : nums) {
            System.out.print(item + "   ");
        }
        System.out.println();
    }
}