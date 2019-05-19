/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: AllArray517
 * Author:   郭新晔
 * Date:     2018/5/17 0017 18:20
 * Description: 数组的全排列温习
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package fullRank;

/**
 * 〈一句话功能简述〉<br> 
 * 〈数组的全排列温习〉
 *
 * @author 郭新晔
 * @create 2018/5/17 0017
 * @since 1.0.0
 */
public class AllArray517 {
    /**
     * 数组的全排列
     * @param nums
     * @param begin
     * @param end
     */
    static void allArray(int[] nums,int begin,int end){
        if(begin==end){
            for(int i : nums){
                System.out.print(i+"  ");
            }
            System.out.println();
        }
        for(int x=begin;x<=end;x++){
            if(!accept(nums,begin,x)){
                continue;
            }
            swap(nums,begin,x);
            allArray(nums,begin+1,end);
            swap(nums,begin,x);
        }
    }

    /**
     * 是否接受这次的排列
     * @param nums
     * @param begin
     * @param end
     * @return
     */
    static boolean accept(int[] nums,int begin,int end){
        for(int i=begin;i<end;i++){
            if(nums[i]==nums[end]){
                return false;
            }
        }
        return true;
    }

    /**
     * 数组元素的交换
     * @param nums
     * @param a
     * @param b
     */
    static void swap(int[] nums,int a,int b){
        int temp=nums[a];
        nums[a]=nums[b];
        nums[b]=temp;
    }

    public static void main(String[] args){
        int[] nums=new int[]{
          1,2,3
        };
        allArray(nums,0,nums.length-1);
    }
}