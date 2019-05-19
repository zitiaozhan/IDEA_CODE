/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Practice1129
 * Author:   郭新晔
 * Date:     2018/11/29 0029 19:24
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package sort.practice;

/**
 * 〈〉
 *
 * @create 2018/11/29 0029
 */
public class Practice1129 {
    static void swap(int[] nums, int a, int b) {
        if (a == b || nums[a] == nums[b]) {
            return;
        }
        nums[a] = nums[a] ^ nums[b];
        nums[b] = nums[a] ^ nums[b];
        nums[a] = nums[a] ^ nums[b];
    }

    static void selectSort(int... nums) {
        if (nums != null && nums.length > 1) {
            int i, j, x;
            for (i = 0; i < nums.length; i++) {
                x = i;
                for (j = i; j < nums.length; j++) {
                    x = nums[x] < nums[j] ? x : j;
                }
                if (x != i) {
                    swap(nums, x, i);
                }
            }
        }
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
            int increment = nums.length / 2;
            while (increment > 0) {
                for (int i = 0; i < nums.length; i++) {
                    for (int j = 0; j < nums.length - increment; j += increment) {
                        if (nums[j] > nums[j + increment]) {
                            swap(nums, j, j + increment);
                        }
                    }
                }

                increment /= 2;
            }
        }
    }

    static void bubbleSort(int... nums) {
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

    static void randomQuickSort(int... nums) {
        if (nums != null && nums.length > 1) {
            randomQuickSoortBody(nums, 0, nums.length - 1);
        }
    }

    static void randomQuickSoortBody(int[] nums, int l, int r) {
        if (l < r) {
            int index = l + (int) (Math.random() * (r - l + 1));
            swap(nums, index, r);
            int[] cur = partition(nums, l, r);
            randomQuickSoortBody(nums, l, cur[0] - 1);
            randomQuickSoortBody(nums, cur[1] + 1, r);
        }
    }

    static void tranditionalQuickSort(int... nums) {
        if (nums != null && nums.length > 1) {
            tranditionalQuickSoortBody(nums, 0, nums.length - 1);
        }
    }

    static void tranditionalQuickSoortBody(int[] nums, int l, int r) {
        if (l < r) {
            int[] cur = partition(nums, l, r);
            tranditionalQuickSoortBody(nums, l, cur[0] - 1);
            tranditionalQuickSoortBody(nums, cur[1] + 1, r);
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
        return new int[]{
                less + 1, more
        };
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
            heapSortBody(nums, 0, nums.length - 1);
        }
    }

    static void heapSortBody(int[] nums, int l, int r) {
        if (l < r) {
            for (int i = l; i <= r; i++) {
                heapInsert(nums, i);
            }
            int size = r - l + 1;
            swap(nums, 0, --size);
            while (size > 0) {
                heaptify(nums, size);
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

    static void heaptify(int[] nums, int size) {
        int i = 0, left = 2 * i + 1, more;
        while (left < size) {
            more = left + 1 < size && nums[left + 1] > nums[left] ? left + 1 : left;
            more = nums[more] > nums[i] ? more : i;
            if (more == i) {
                break;
            }
            swap(nums, i, more);
            i = more;
            left = 2 * i + 1;
        }
    }

    static void barrelSort(int... nums) {
        if (nums != null && nums.length > 1) {
            int max = nums[0];
            for (int item : nums) {
                max = max > item ? max : item;
            }
            int[] barrel = new int[max + 1];
            for (int item : nums) {
                barrel[item]++;
            }
            int j = 0;
            for (int i = 0; i < barrel.length; i++) {
                while (barrel[i]-- > 0) {
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
        barrelSort(nums);

        for (int item : nums) {
            System.out.print(item + "   ");
        }
        System.out.println();
    }
}