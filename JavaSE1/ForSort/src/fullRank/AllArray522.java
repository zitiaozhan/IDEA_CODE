/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: AllArray522
 * Author:   郭新晔
 * Date:     2018/5/22 0022 21:56
 * Description: 数组的全排列--2018-05-22
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package fullRank;

/**
 * 〈一句话功能简述〉<br> 
 * 〈数组的全排列--2018-05-22〉
 *
 * @author 郭新晔
 * @create 2018/5/22 0022
 * @since 1.0.0
 */
public class AllArray522 {
    /**
     * 数组元素的全排列
     * @param nums
     * @param begin
     * @param end
     */
    static void allArray(int[] nums,int begin,int end){
        if(begin==end){
            for(int num : nums){
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
    /**
     * 是否接受此次排列
     * @param nums
     * @param begin
     * @param end
     * @return
     */
    static boolean isAccept(int[] nums,int begin,int end){
        for(int i=begin;i<end;i++){
            if(nums[i]==nums[end]){
                return false;
            }
        }
        return true;
    }
    /**
     * 交换元素
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
        int[] nums={
                1,2,3
        };
        allArray(nums,0,nums.length);
    }
}