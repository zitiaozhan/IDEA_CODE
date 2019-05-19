/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Sort704
 * Author:   郭新晔
 * Date:     2018/7/4 0004 16:36
 * Description: 排序算法温习---2018-07-04
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package sort.practice;

import java.util.Arrays;
import java.util.Collection;

/**
 * 〈一句话功能简述〉<br> 
 * 〈排序算法温习---2018-07-04〉
 *
 * @author 郭新晔
 * @create 2018/7/4 0004
 * @since 1.0.0
 */
public class Sort704 {
    public static void main(String[] args) {
        int[] nums={56,6,489,3,66,2,2,1,5,5,6,5615,1,564,469,598,6,15,6,65,8,9498};
        
        //insertSort(nums);
        //selectSort(nums);
        //quickSort(nums,0,nums.length-1);
        //bubbleSort(nums);
        //collectionSort(nums);
        
        for(int item : nums){
            System.out.print(item+"   ");
        }
        System.out.println();
    }
    /**
     * <方法名称>:insertSort
     * <方法功能描述>:插入排序算法
     * <方法参数>:
     * <方法创建时间>:
     */
    public static void insertSort(int[] nums){
        int i,j,x;
        for(i=0;i < nums.length-1;i++){
            x=nums[i+1];
            j=i;
            while(j>-1&&nums[j]>x){
                nums[j+1]=nums[j--];
            }
            nums[j+1]=x;
        }
    }
    /**
     * <方法名称>:selectSort
     * <方法功能描述>:直接选择排序算法
     * <方法参数>:
     * <方法创建时间>:
     */
    public static void selectSort(int[] nums){
        int i,j,x,temp;
        for(i=0;i < nums.length-1;i++){
            x=i;
            for(j=i+1;j < nums.length;j++){
                x=nums[x]<nums[j]?x:j;
            }
            if(x!=i){
                temp=nums[x];
                nums[x]=nums[i];
                nums[i]=temp;
            }
        }
    }
    /**
     * <方法名称>:quickSort
     * <方法功能描述>:快速排序算法
     * <方法参数>:
     * <方法创建时间>:
     */
    public static void quickSort(int[] nums,int low,int high){
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
            quickSort(nums,low,i-1);
            quickSort(nums,i+1,high);
        }
    }
    /**
     * <方法名称>:bubbleSort
     * <方法功能描述>:冒泡排序算法
     * <方法参数>:
     * <方法创建时间>:
     */
    public static void bubbleSort(int[] nums){
        boolean flag=true;
        for(int i=0;i < nums.length-1&&flag;i++){
            flag=false;
            for(int j=0;j < nums.length-i-1;j++){
                if(nums[j]>nums[j+1]){
                    int temp=nums[j];
                    nums[j]=nums[j+1];
                    nums[j+1]=temp;
                    flag=true;
                }
            }
        }
    }
    /**
     * <方法名称>:
     * <方法功能描述>:
     * <方法参数>:
     * <方法创建时间>:
     */
    public static void collectionSort(int... nums){
        Collection list=Arrays.asList(56,6,489,3,66,2,2,1,5,5,6,5615,1,564,469,598,6,15,6,65,8,9498);
        list.stream()
                .sorted()
                .forEach(System.out::println);
    }
}