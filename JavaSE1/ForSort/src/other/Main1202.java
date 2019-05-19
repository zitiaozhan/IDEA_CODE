/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Main1202
 * Author:   郭新晔
 * Date:     2018/12/2 0002 18:17
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package other;

/**
 * 〈〉
 *
 * @create 2018/12/2 0002
 */
public class Main1202 {
    public static void main(String[] args) {
        double res = getMidNumber(new int[]{1, 3, 5}, new int[]{2, 4, 6});
        res = findMedianSortedArrays(new int[]{1, 2, 3, 4, 5, 6}, new int[]{});
        System.out.println(res);
    }

    static double getMidNumber(int[] nums1, int[] nums2) {
        double res = 0;
        if (nums1 != null && nums2 != null) {
            int len1 = nums1.length;
            int len2 = nums2.length;
            int len = len1 + len2;
            int midIndex = (len + 1) / 2;
            if (len % 2 != 0) {
                return handleMidNumber(midIndex, len1, nums1, len2, nums2);
            } else {
                return (handleMidNumber(midIndex, len1, nums1, len2, nums2) + handleMidNumber(midIndex + 1, len1, nums1, len2, nums2)) / 2;
            }
        }
        return res;
    }

    static double handleMidNumber(int midIndex, int len1, int[] nums1, int len2, int[] nums2) {
        double res = 0;
        int count = 1, i = 0, j = 0;
        while (count <= midIndex) {
            if (i < len1 && j < len2) {
                if (nums1[i] <= nums2[j]) {
                    res = nums1[i];
                    i++;
                } else {
                    res = nums2[j];
                    j++;
                }
            } else if (i < len1 && j >= len2) {
                res = nums1[i];
                i++;
            } else if (i >= len1 && j < len2) {
                res = nums2[j];
                j++;
            }
            count++;
        }
        return res;
    }

    static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        if (m > n) { // to ensure m<=n
            int[] temp = nums1;
            nums1 = nums2;
            nums2 = temp;
            int tmp = m;
            m = n;
            n = tmp;
        }
        int iMin = 0, iMax = m, halfLen = (m + n + 1) / 2;
        while (iMin <= iMax) {              //当iMin小于等于iMax时执行循环
            int i = (iMin + iMax) / 2;
            int j = halfLen - i;
            if (i < iMax && nums2[j - 1] > nums1[i]) {
                iMin = i + 1; // i is too small
            } else if (i > iMin && nums1[i - 1] > nums2[j]) {
                iMax = i - 1; // i is too big
            } else { // i is perfect
                int maxLeft = 0;
                if (i == 0) {
                    maxLeft = nums2[j - 1];
                } else if (j == 0) {
                    maxLeft = nums1[i - 1];
                } else {
                    maxLeft = Math.max(nums1[i - 1], nums2[j - 1]);
                }
                if ((m + n) % 2 == 1) {
                    return maxLeft;
                }

                int minRight = 0;
                if (i == m) {
                    minRight = nums2[j];
                } else if (j == n) {
                    minRight = nums1[i];
                } else {
                    minRight = Math.min(nums2[j], nums1[i]);
                }

                return (maxLeft + minRight) / 2.0;
            }
        }
        return 0.0;
    }
}