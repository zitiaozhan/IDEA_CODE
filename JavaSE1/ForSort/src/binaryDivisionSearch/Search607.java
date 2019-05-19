/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Search607
 * Author:   郭新晔
 * Date:     2018/6/7 0007 21:21
 * Description: 二分查找算法温习--2018-06-07
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package binaryDivisionSearch;

import java.util.Arrays;

/**
 * 〈一句话功能简述〉<br> 
 * 〈二分查找算法温习--2018-06-07〉
 *
 * @author 郭新晔
 * @create 2018/6/7 0007
 * @since 1.0.0
 */
public class Search607 {
    /**
     * 二分查找升序数组
     * @param nums
     * @param x
     * @return
     */
    static int searchByMin(int[] nums,int x){
        int low=0,high=nums.length-1,mid=0;
        if(x<nums[low]||x>nums[high]||low>high){
            return -1;
        }
        while(low<high){
            mid=(low+high)/2;
            if(nums[mid]>x){
                high=mid-1;
                continue;
            }
            else if(nums[mid]<x){
                low=mid+1;
                continue;
            }
            return mid;
        }
        return -1;
    }

    public static void main(String[] args){
        int[] nums=new int[]{
                12,24,55,1,45,3,105,22,19,78,91,30,32,31,33,105
        };
        Arrays.sort(nums);
        for(int num:nums){
            System.out.print(num+"    ");
        }
        System.out.println();

        int index=0;
        index=searchByMin(nums,19);
        System.out.println("nums["+index+"] = "+nums[index]);
    }
}