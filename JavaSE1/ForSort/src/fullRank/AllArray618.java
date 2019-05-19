/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: AllArray618
 * Author:   郭新晔
 * Date:     2018/6/18 0018 17:28
 * Description: 数组的全排列算法温习
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package fullRank;

/**
 * 〈一句话功能简述〉<br> 
 * 〈数组的全排列算法温习〉
 *
 * @author 郭新晔
 * @create 2018/6/18 0018
 * @since 1.0.0
 */
public class AllArray618 {
    /**
     * 数组内元素交换
     * @param nums
     * @param a
     * @param b
     */
    static void swap(int[] nums,int a,int b){
        int temp=nums[a];
        nums[a]=nums[b];
        nums[b]=temp;
    }

    /**
     * 此时的排列序列是否能够接收
     * @param nums
     * @param low
     * @param high
     * @return
     */
    static boolean isAccept(int[] nums,int low,int high){
        for(int i=low;i<high;i++){
            if(nums[i]==nums[high]){
                return false;
            }
        }
        return true;
    }

    /**
     * 全排列算法
     * @param nums
     * @param begin
     * @param end
     */
    static void allArray(int[] nums,int begin,int end){
        if(begin==end){
            for(int num: nums){
                System.out.print(num+"    ");
            }
            System.out.println();
        }
        for(int i=begin;i<end;i++){
            if(!isAccept(nums,begin,i)){
                continue;
            }
            swap(nums,begin,i);
            allArray(nums,begin+1,end);
            swap(nums,begin,i);
        }
    }

    public static void main(String[] args){
        int[] nums=new int[]{1,2,3};

        allArray(nums,0,nums.length);
    }
}