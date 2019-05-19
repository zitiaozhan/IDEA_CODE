/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Main
 * Author:   郭新晔
 * Date:     2018/9/7 0007 14:55
 * Description: Main
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package test.main;

/**
 * 〈一句话功能简述〉<br>
 * 〈Main〉
 *
 * @author 郭新晔
 * @create 2018/9/7 0007
 * @since 1.0.0
 */

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String line1 = "";
        String line2 = "";
        while (input.hasNext()) {
            line1 = input.nextLine();
            line2 = input.nextLine();
            String[] nums1 = line1.split(" ");
            String[] nums2 = line2.split(" ");

            System.out.println(getPeopleNum(nums1, nums2));
        }
    }

    static int getPeopleNum(String[] nums1, String[] nums2) {
        int number=0;
        int[] giArr=new int[nums1.length];
        int[] sjArr=new int[nums2.length];
        for (int i=0;i<nums1.length;i++){
            giArr[i]=Integer.parseInt(nums1[i]);
        }
        for (int i=0;i<nums2.length;i++){
            sjArr[i]=Integer.parseInt(nums2[i]);
        }
        insertSort(giArr);
        insertSort(sjArr);

        int i= giArr.length-1,j=sjArr.length-1;
        while (j>-1&&i>-1){
            if (sjArr[j]>=giArr[i]){
                j--;
                i--;
                number++;
            }
            else if (sjArr[j]<giArr[i]){
                j--;
            }
        }
        return number;
    }

    static void insertSort(int... nums) {
        int i, j, x;
        for (i = 0; i < nums.length - 1; i++) {
            x = nums[i + 1];
            j = i;
            while (j > -1 && nums[j] < x) {
                nums[j + 1] = nums[j--];
            }
            nums[j + 1] = x;
        }
    }
}