/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Search704
 * Author:   郭新晔
 * Date:     2018/7/4 0004 17:21
 * Description: 二分查找算法---2017-07-04
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package binaryDivisionSearch;

import java.util.Arrays;

/**
 * 〈一句话功能简述〉<br>
 * 〈二分查找算法---2017-07-04〉
 *
 * @author 郭新晔
 * @create 2018/7/4 0004
 * @since 1.0.0
 */
public class Search704 {
    public static void main(String[] args) {
        int[] nums = new int[]{
                54, 3, 5, 6, 4, 8, 2, 98, 849, 454, 153, 36, 56, 22, 66
        };
        Arrays.sort(nums);
        for (int item : nums) {
            System.out.print(item + "   ");
        }
        System.out.println();
        int index = search(nums, 98, 1);
        System.out.println("nums[" + index + "] = " + nums[index]);
    }

    /**
     * <方法名称>:search
     * <方法功能描述>:二分查找算法
     * <方法参数>:
     * <方法创建时间>:
     */
    public static Integer search(int[] nums, int x, int ud) {
        if (
                ud == 0 || (ud > 0 && (x < nums[0] || x > nums[nums.length - 1])) || (ud < 0 && (x > nums[0] || x < nums[nums.length - 1]))
                ) {
            return null;
        }
        int low = 0, high = nums.length - 1, mid = 0, index = -1;
        while (low < high) {
            mid = (low + high) / 2;
            if (ud > 0) {
                if (x > nums[mid]) {
                    low = mid + 1;
                } else if (x < nums[mid]) {
                    high = mid - 1;
                } else {
                    index = mid;
                    break;
                }
            }
            if (ud < 0) {
                if (x > nums[mid]) {
                    high = mid - 1;
                } else if (x < nums[mid]) {
                    low = mid + 1;
                } else {
                    index = mid;
                    break;
                }
            }
        }
        return index;
    }
}