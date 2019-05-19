/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Main1201
 * Author:   郭新晔
 * Date:     2018/12/1 0001 22:26
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package other;

/**
 * 〈〉
 *
 * @create 2018/12/1 0001
 */
public class Main1201 {
    /**
     * 根据奇偶条件得到中位数
     * 时间复杂度：O（M+N）
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int len1 = nums1.length;
        int len2 = nums2.length;
        int flag = (len1 + len2 + 1) / 2; //中位数的下标值

        if ((len1 + len2) % 2 == 1) {     //如果合并后的数组长度是奇数：
            return getRankNum(flag, nums1, len1, nums2, len2);
        } else {
            double pre = getRankNum(flag, nums1, len1, nums2, len2);
            double sub = getRankNum(flag + 1, nums1, len1, nums2, len2);
            return (pre + sub) / 2;
        }
    }

    public static double getRankNum(int flag, int[] nums1, int len1, int[] nums2, int len2) {
        double middle = 0;                  //要返回的中位数
        int count = 1, i = 0, j = 0;
        while (count <= flag) {             //count位于理想中位数的左边时循环，直到count超过flag
            if (i < len1 && j < len2) {     //当下标i、j都没有越界时
                if (nums1[i] <= nums2[j]) { //如果数组1的i元素小于等于数组2的j元素
                    middle = nums1[i];      //数组1的i下标向后移动一个位置
                    i++;
                } else {
                    middle = nums2[j];
                    j++;
                }
            } else if (i < len1 && j >= len2) {
                middle = nums1[i];
                i++;
            } else if (i >= len1 && j < len2) {
                middle = nums2[j];
                j++;
            }
            count++;                        //每次循环count自增一次
        }
        return middle;
    }

    public static void main(String[] args) {
        double res = findMedianSortedArrays(new int[]{1, 3, 5}, new int[]{2, 4, 6});
        System.out.println(res);
    }
}