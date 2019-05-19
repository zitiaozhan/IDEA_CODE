/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: AllArray704
 * Author:   郭新晔
 * Date:     2018/7/4 0004 16:24
 * Description: 数组的全排列---2018-07-04
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package fullRank;

/**
 * 〈一句话功能简述〉<br> 
 * 〈数组的全排列---2018-07-04〉
 *
 * @author 郭新晔
 * @create 2018/7/4 0004
 * @since 1.0.0
 */
public class AllArray704 {
    public static void main(String[] args) {
        int[] nums={1,2,3,2};
        allArray(nums,0,nums.length);
    }
    /**
     * <方法名称>:swap
     * <方法功能描述>:数组内元素交换
     * <方法参数>:
     * <方法创建时间>:
     */
    public static void swap(int[] nums,int a,int b){
        int temp=nums[a];
        nums[a]=nums[b];
        nums[b]=temp;
    }
    /**
     * <方法名称>:isAccept
     * <方法功能描述>:是否接受当前的数组的排列序列
     * <方法参数>:
     * <方法创建时间>:
     */
    public static boolean isAccept(int[] nums,int begin,int end){
        for (int i = begin; i < end; i++) {
            if(nums[i]==nums[end]){
                return false;
            }
        }
        return true;
    }
    /**
     * <方法名称>:allArray
     * <方法功能描述>:数组的全排列综合
     * <方法参数>:
     * <方法创建时间>:
     */
    public static void allArray(int[] nums,int begin,int end){
        if(begin==end){
            for(int item : nums){
                System.out.print(item+"   ");
            }
            System.out.println();
        }
        for (int i = begin; i < end; i++) {
            if(!isAccept(nums,begin,i)){
                continue;
            }
            swap(nums,begin,i);
            allArray(nums,begin+1,end);
            swap(nums,begin,i);
        }
    }
}