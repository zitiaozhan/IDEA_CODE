/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Array510
 * Author:   郭新晔
 * Date:     2018/5/10 0010 15:43
 * Description: 温习数组的全排列--2018-5-10
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package fullRank;

/**
 * 〈一句话功能简述〉<br> 
 * 〈温习数组的全排列--2018-5-10〉
 *
 * @author 郭新晔
 * @create 2018/5/10 0010
 * @since 1.0.0
 */
public class Array510 {
    static void fullArray(int[] nums,int begin,int end){
        if(begin==end){
            for(int x : nums){
                System.out.print(x+"    ");
            }
            System.out.println();
        }
        for(int i=begin;i<=end;i++){
            if(!swapAccept(nums,begin,i)){
                continue;
            }
            swap(nums,begin,i);
            fullArray(nums,begin+1,end);
            swap(nums,begin,i);
        }
    }
    static boolean swapAccept(int[] nums,int begin,int end){
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
        int[] nums=new int[]{
                3,2,2
        };
        fullArray(nums,0,nums.length-1);
    }
}