/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: AllArray516
 * Author:   郭新晔
 * Date:     2018/5/16 0016 9:54
 * Description: 数组的全排列温习---20118-05-16
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package fullRank;

/**
 * 〈一句话功能简述〉<br> 
 * 〈数组的全排列温习---20118-05-16〉
 *
 * @author 郭新晔
 * @create 2018/5/16 0016
 * @since 1.0.0
 */
public class AllArray516 {

    static void allArray(int[] nums,int begin,int end){
        if(begin==end){
            for(int m : nums){
                System.out.print(m+"    ");
            }
            System.out.println();
        }
        for(int i=begin;i<=end;i++){
            if(!accept(nums,begin,i)){
                continue;
            }
            swap(nums,begin,i);
            allArray(nums,begin+1,end);
            swap(nums,begin,i);
        }
    }

    static boolean accept(int[] nums,int begin,int end){
        for(int i=begin;i<end;i++){
            if(nums[i]==nums[end]){
                return false;
            }
        }
        return true;
    }

    static void swap(int[] nums,int a,int b){
        int temp=nums[a];
        nums[a]=nums[b];
        nums[b]=temp;
    }
    public static void main(String[] args){
        int[] nums={
            1,2,3,3
        };
        allArray(nums,0,nums.length-1);
    }
}