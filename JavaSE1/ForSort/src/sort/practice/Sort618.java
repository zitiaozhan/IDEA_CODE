/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Sort618
 * Author:   郭新晔
 * Date:     2018/6/18 0018 15:57
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package sort.practice;

/**
 * 〈一句话功能简述〉<br> 
 * 〈排序算法温习---2018-06-18〉
 *
 * @author 郭新晔
 * @create 2018/6/18 0018
 * @since 1.0.0
 */
public class Sort618 {
    /**
     * 插入排序算法
     * 判断当前的数字是否小于序列在它之前的数字
     * @param nums
     */
    static void insertSort(int[] nums){
        int i,j,x;
        for(i=0;i<nums.length-1;i++){
            j=i;
            x=nums[i+1];
            while(j>-1&&nums[j]>x){
                nums[j+1]=nums[j--];
            }
            nums[j+1]=x;
        }
    }

    /**
     * 直接选择排序算法
     * @param nums
     */
    static void selectSort(int[] nums){
        int i,j,x,temp;
        for(i=0;i<nums.length-1;i++){
            x=i;
            for(j=i+1;j<nums.length;j++){
                x=nums[j]<nums[x]?j:x;
            }
            if(x!=i){
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

    /**
     * 冒泡排序算法
     * @param nums
     */
    static void bubbleSort(int[] nums){
        boolean flag=true;
        for(int i=0;i<nums.length&&flag;i++){
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
        int[] nums=new int[]{
                5,45,43,22,15,48,0,66,11,85,15,318,618,225,20
        };

        //insertSort(nums);
        //selectSort(nums);
        //quickSort(nums,0,nums.length-1);
        bubbleSort(nums);

        for(int num: nums){
            System.out.print(num+"    ");
        }
    }
}