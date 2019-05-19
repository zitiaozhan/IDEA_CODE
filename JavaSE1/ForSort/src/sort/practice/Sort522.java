/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Sort522
 * Author:   郭新晔
 * Date:     2018/5/22 0022 21:43
 * Description: 排序算法温习2018-05-22
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package sort.practice;

/**
 * 〈一句话功能简述〉<br> 
 * 〈排序算法温习2018-05-22〉
 *
 * @author 郭新晔
 * @create 2018/5/22 0022
 * @since 1.0.0
 */
public class Sort522 {
    /**
     * 插入排序算法
     * @param nums
     */
    static void insertSort(int[] nums){
        int i,j,x;
        for(i=0;i<nums.length-1;i++){
            x=nums[i+1];
            j=i;
            while(j>-1&&nums[j]>x){
                nums[j+1]=nums[j--];
            }
            nums[j+1]=x;
        }
    }

    /**
     * 选择排序算法
     * @param nums
     */
    static void selectrSort(int[] nums){
        int i,j,x,temp;
        for(i=0;i<nums.length-1;i++){
            x=i;
            for(j=i+1;j<nums.length;j++){
                x=nums[x]>nums[j]?j:x;
            }
            if(x!=j){
                temp=nums[x];
                nums[x]=nums[i];
                nums[i]=temp;
            }
        }
    }

    /**
     * 快速排序算法
     * @param nums
     * @param low
     * @param high
     */
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

    /**
     * 冒泡排序算法
     * @param nums
     */
    static void bubbleSort(int[] nums){
        boolean flag=true;
        for(int i=0;i<nums.length-1&&flag;i++){
            flag=false;
            for(int j=0;j<nums.length-i-1;j++){
                if(nums[j]>nums[j+1]){
                    int temp=nums[j];
                    nums[j]=nums[j+1];
                    nums[j+1]=temp;

                    flag=true;
                }
            }
        }
    }

    public static void main(String[] args){
        int[] nums = new int[]{
                15,3,66,41,19,0,75,12,2,34,154,150,155,6
        };

        //insertSort(nums);
        //selectrSort(nums);
        //quickSort(nums,0,nums.length-1);
        bubbleSort(nums);

        for(int num : nums){
            System.out.print(num+"    ");
        }
        System.out.println();
    }
}