/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Sort1007
 * Author:   郭新晔
 * Date:     2018/10/7 0007 14:43
 * Description:
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
 * @create 2018/10/7 0007
 * @since 1.0.0
 */
public class Sort1007 {

    static void swap(int[] nums, int a, int b) {
        if (a == b || nums[a] == nums[b]) {
            return;
        }
        nums[a] = nums[a] ^ nums[b];
        nums[b] = nums[a] ^ nums[b];
        nums[a] = nums[a] ^ nums[b];
    }

    static void insertSort(int... nums) {
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

    static void hillSort(int... nums) {
        if (nums != null && nums.length > 1) {
            int incrment = nums.length / 2;
            while (incrment > 0) {
                for (int i = 0; i < nums.length - 1; i++) {
                    for (int j = i; j < nums.length - incrment; j += incrment) {
                        if (nums[j] > nums[j + incrment]) {
                            swap(nums, j, j + incrment);
                        }
                    }
                }
                incrment /= 2;
            }
        }
    }

    static void selectSort(int... nums) {
        if (nums != null && nums.length > 1) {
            int i, j, x;
            for (i = 0; i < nums.length; i++) {
                x = i;
                for (j = i + 1; j < nums.length; j++) {
                    x = nums[j] < nums[x] ? j : x;
                }
                if (x != i) {
                    swap(nums, i, x);
                }
            }
        }
    }

    static void bubbleSort(int... nums) {
        if (nums != null && nums.length > 1) {
            boolean flag = true;
            for (int i = nums.length - 1; i >= 0 && flag; i--) {
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

    static void randomQuickSort(int... nums) {
        if (nums != null && nums.length > 1) {
            randomQuickSortBody(nums, 0, nums.length - 1);
        }
    }

    static void tranditionalQuickSort(int... nums) {
        if (nums != null && nums.length > 1) {
            tranditionalQuickSortBody(nums, 0, nums.length - 1);
        }
    }

    static void randomQuickSortBody(int[] nums, int l, int r) {
        if (l < r) {
            int index = l + (int) ((r - l + 1) * Math.random());
            swap(nums, index, r);
            int[] cur = partition(nums, l, r);
            randomQuickSortBody(nums, l, cur[0] - 1);
            randomQuickSortBody(nums, cur[1] + 1, r);
        }
    }

    static void tranditionalQuickSortBody(int[] nums, int l, int r) {
        if (l < r) {
            int[] cur = partition(nums, l, r);
            tranditionalQuickSortBody(nums, l, cur[0] - 1);
            tranditionalQuickSortBody(nums, cur[1] + 1, r);
        }
    }

    static int[] partition(int[] nums, int l, int r) {
        int left = l - 1, right = r;
        while (l < right) {
            if (nums[l] < nums[r]) {
                swap(nums, ++left, l++);
            } else if (nums[l] > nums[r]) {
                swap(nums, --right, l);
            } else {
                l++;
            }
        }
        swap(nums, right, r);
        return new int[]{left + 1, right};
    }

    static void mergeSort(int... nums) {
        if (nums != null && nums.length > 1) {
            mergeSortBody(nums, 0, nums.length - 1);
        }
    }

    static void mergeSortBody(int[] nums, int l, int r) {
        if (l < r) {
            int mid = l + ((r - l) >> 1);
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

    static void heapSort(int... nums) {
        if (nums != null && nums.length > 1) {
            for (int i = 0; i < nums.length; i++) {
                heapInsert(nums, i);
            }
            int size = nums.length;
            swap(nums, 0, --size);
            while (size > 1) {
                heapify(nums, 0, size);
                swap(nums, 0, --size);
            }
        }
    }

    static void heapInsert(int[] nums, int i) {
        while (nums[i] > nums[(i - 1) / 2]) {
            swap(nums, i, (i - 1) / 2);
            i = (i - 1) / 2;
        }
    }

    static void heapify(int[] nums, int index, int size) {
        int left = 2 * index + 1;
        int bigger = Integer.MIN_VALUE;
        while (left < size) {
            bigger = left + 1 < size && nums[left + 1] > nums[left] ? left + 1 : left;
            bigger = nums[index] > nums[bigger] ? index : bigger;
            if (bigger == index) {
                break;
            }
            swap(nums, index, bigger);
            index = bigger;
            left = 2 * index + 1;
        }
    }

    static void barrelSort1(int... nums) {
        int[] help = new int[564];
        int index = 0;
        for (int i = 0; i < nums.length; i++) {
            help[nums[i]]++;
        }
        for (int i = 0; i < help.length; i++) {
            while (--help[i] > -1) {
                nums[index++] = i;
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
        barrelSort1(nums);

        for (int item : nums) {
            System.out.print(item + "   ");
        }
        System.out.println();
    }
}