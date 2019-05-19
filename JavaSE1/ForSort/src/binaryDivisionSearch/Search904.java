/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Search904
 * Author:   郭新晔
 * Date:     2018/9/4 0004 10:54
 * Description: 二分查找算法温习
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package binaryDivisionSearch;

import java.util.Arrays;

/**
 * 〈一句话功能简述〉<br>
 * 〈二分查找算法温习〉
 *
 * @author 郭新晔
 * @create 2018/9/4 0004
 * @since 1.0.0
 */
public class Search904 {
    public static void main(String[] args) {
        int[] nums={
          57,89,32,46,76,7,56,67,567,658,65,454,665,4,54,8
        };
        Arrays.sort(nums);
        int index=search(nums,7,1);
        System.out.println("nums["+index+"] = "+nums[index]);
    }

    /**
     * 二分查找算法
     * @param nums  已排序数组
     * @param x     要查找的数字
     * @param pattern   数组排序为升序(>0)或降序(<0)
     * @return
     */
    static int search(int[] nums, int x, int pattern) {
        int index = -1;
        if (pattern == 0 || (pattern > 0 && (x < nums[0] || x > nums[nums.length - 1])) || (pattern < 0 && (x > nums[0] || x < nums[nums.length - 1]))) {
            return -1;
        }
        int begin = 0, end = nums.length - 1, mid;
        while (begin <= end) {
            mid = (begin + end) / 2;
            if (pattern > 0) {
                if (x > nums[mid]) {
                    begin = mid + 1;
                } else if (x < nums[mid]) {
                    end = mid - 1;
                } else if (x == nums[mid]) {
                    index = mid;
                    break;
                }
            } else if (pattern < 0) {
                if (x > nums[mid]) {
                    end = mid - 1;
                } else if (x < nums[mid]) {
                    begin = mid + 1;
                } else if (x == nums[mid]) {
                    index = mid;
                    break;
                }
            }
        }

        return index;
    }
}