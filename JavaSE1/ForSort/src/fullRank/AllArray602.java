/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: AllArray602
 * Author:   郭新晔
 * Date:     2018/6/2 0002 19:46
 * Description: 数组的全排列--2018-06-02
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package fullRank;

/**
 * 〈一句话功能简述〉<br> 
 * 〈数组的全排列--2018-06-02〉
 *
 * @author 郭新晔
 * @create 2018/6/2 0002
 * @since 1.0.0
 */
public class AllArray602 {
    static void allArray(int[] nums,int begin,int end){
        if(begin==end){
            for(int num:nums){
                System.out.print(num);
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

    static void swap(int[] nums,int a,int b){
        int temp=nums[a];
        nums[a]=nums[b];
        nums[b]=temp;
    }
    static boolean isAccept(int []nums,int begin,int end){
        for(int i=begin;i<end;i++){
            if(nums[i]==nums[end]){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args){
        int[] nums=new int[]{
                1,2,3
        };
        allArray(nums,0,nums.length);
    }
}