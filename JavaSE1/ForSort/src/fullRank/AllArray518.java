/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: AllArray518
 * Author:   郭新晔
 * Date:     2018/5/18 0018 15:31
 * Description: 数组的全排列
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package fullRank;

/**
 * 〈一句话功能简述〉<br>
 * 〈数组的全排列2018-05-18〉
 *
 * @author 郭新晔
 * @create 2018/5/18 0018
 * @since 1.0.0
 */
public class AllArray518 {
    /**
     * 数组的全排列
     * @param nums
     * @param begin
     * @param end
     */
    static void allArray(int[] nums,int begin,int end){
        if(begin==end){
            for(int h:nums){
                System.out.print(h+"  ");
            }
            System.out.println();
        }
        for(int i=begin;i<end;i++){
            if(!acceptArray(nums,begin,i)){
                continue;
            }
            swap(nums,begin,i);
            allArray(nums,begin+1,end);
            swap(nums,begin,i);
        }
    }

    /**
     * 数组元素交换
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
     * 当前序列中是否已经重复,重复则不接受
     * @param nums
     * @param begin
     * @param end
     * @return
     */
    static boolean acceptArray(int[] nums,int begin,int end){
        for(int i=begin;i<end;i++){
            if(nums[i]==nums[end]){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args){
        int[] nums={
          1,2,1
        };
        allArray(nums,0,nums.length);
    }
}