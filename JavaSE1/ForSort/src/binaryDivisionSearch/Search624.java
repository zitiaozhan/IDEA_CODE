/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Search624
 * Author:   郭新晔
 * Date:     2018/6/24 0024 18:10
 * Description: 二分查找算法温习---2018-06-24
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package binaryDivisionSearch;

import java.util.Arrays;

/**
 * 〈一句话功能简述〉<br> 
 * 〈二分查找算法温习---2018-06-24〉
 *
 * @author 郭新晔
 * @create 2018/6/24 0024
 * @since 1.0.0
 */
public class Search624 {
    public static void main(String[] args) {
        int[] nums={549,489,5,0,298,21,20,2,22,1,87,11,546,5,78,5,50,651,51,65};
        Arrays.sort(nums);
        for(int i=0;i<nums.length;i++){
            System.out.println("nums["+i+"] = "+nums[i]);
        }
        int index=search(nums,22);
        System.out.println("二分查找结果: nums["+index+"] = "+nums[index]);
    }

    /**
     * 二分查找算法
     * @param nums
     * @param x
     * @return
     */
    static Integer search(int[] nums,int x){
        if(nums[0]>=nums[nums.length-1]){
            if(x>nums[0]|x<nums[nums.length-1]){
                return null;
            }
        }else{
            if(x<nums[0]|x>nums[nums.length-1]){
                return null;
            }
        }
        int low=0,high=nums.length-1,mid=(low+high)/2;
        while(low<=high){
            mid=(low+high)/2;
            if(nums[mid]<x){
                low=mid+1;
            }
            else if(nums[mid]>x){
                high=mid-1;
            }else if(nums[mid]==x){
                return mid;
            }
        }
        return null;
    }
}