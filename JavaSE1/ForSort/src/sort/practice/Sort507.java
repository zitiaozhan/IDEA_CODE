/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Sort507
 * Author:   郭新晔
 * Date:     2018/5/7 0007 10:25
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package sort.practice;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author 郭新晔
 * @create 2018/5/7 0007
 * @since 1.0.0
 */
public class Sort507 {
    /**
     * 直接插入排序
     * @param nums
     */
    static void insertSort(int[] nums){
        int i,j,x;
        for(i=0;i<nums.length-1;i++){
            x=nums[i+1];
            j=i;
            while(j>-1&&x<nums[j]){
                nums[j+1]=nums[j--];
            }
            nums[j+1]=x;
        }
    }

    /**
     * 直接选择排序
     * @param nums
     */
    static void selectSort(int[] nums){
        int i,j,x;
        for(i=0;i<nums.length-1;i++){
            x=i;
            j=i+1;
            for(;j<nums.length;j++){
                x=nums[j]<nums[x]?j:x;
            }
            if(x!=i){
                int temp=nums[i];
                nums[i]=nums[x];
                nums[x]=temp;
            }
        }
    }
    static void quickSort(int[] nums,int low,int high){
        if(low<high){
            int i=low,j=high,x=nums[i];
            while(i<j){
                while(i<j&&x<=nums[j]){
                    j--;
                }
                if(i<j){
                    nums[i++]=nums[j];
                }
                while(i<j&&x>nums[i]){
                    i++;
                }
                if(i<j){
                    nums[j--]=nums[i];
                }
            }
            nums[i]=x;
            quickSort(nums,i+1,high);
            quickSort(nums,low,i-1);
        }
    }

    public static void main(String[] args){
        int[] nums = new int[]{
                12,33,14,20,2,5,3,78,64,11,23,99,55,0
        };
        //insertSort(nums);
        //selectSort(nums);
        //quickSort(nums,0,nums.length-1);
        for(int num : nums){
            System.out.print(num+"    ");
        }
    }
}