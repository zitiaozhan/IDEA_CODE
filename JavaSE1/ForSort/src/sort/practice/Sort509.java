/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Sort509
 * Author:   郭新晔
 * Date:     2018/5/9 0009 16:46
 * Description: 练习排序算法--日期:2018-05-09
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package sort.practice;

/**
 * 〈一句话功能简述〉<br> 
 * 〈练习排序算法--日期:2018-05-09〉
 *
 * @author 郭新晔
 * @create 2018/5/9 0009
 * @since 1.0.0
 */
public class Sort509 {
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
    static void selectSort(int[] nums){
        int i,j,x,temp;
        for(i=0;i<nums.length-1;i++){
            x=i;
            for(j=i+1;j<nums.length;j++){
                x=nums[j]<nums[x]?j:x;
            }
            if(x!=i){
                temp=nums[i];
                nums[i]=nums[x];
                nums[x]=temp;
            }
        }
    }
    static void quickSort(int[] nums,int low,int high){
        if(low<high){
            int i=low,j=high,x=nums[i];
            while(i<j){
                while(i<j&&x<nums[j]){
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
            quickSort(nums,low,i-1);
            quickSort(nums,i+1,high);
        }
    }

    public static void main(String[] args){
        int[] nums = new int[]{
                12,33,14,20,2,5,3,78,64,11,23,99,55,0
        };
        //insertSort(nums);
        //selectSort(nums);
        quickSort(nums,0,nums.length-1);
        for(int num : nums){
            System.out.print(num+"    ");
        }
    }
}