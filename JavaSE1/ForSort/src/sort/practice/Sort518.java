/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Sort518
 * Author:   郭新晔
 * Date:     2018/5/18 0018 14:59
 * Description: 排序算法温习---2018-05-18
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package sort.practice;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈排序算法温习---2018-05-18〉
 *
 * @author 郭新晔
 * @create 2018/5/18 0018
 * @since 1.0.0
 */
public class Sort518 {

    static void collectionSort(){
        List<Integer> numList= Arrays.asList(12, 33, 14, 20, 2, 5, 3, 78, 64, 11, 23, 99, 55, 0);
        long before=System.currentTimeMillis();
        Collections.sort(numList,(a, b)->a-b);
        /*
        numList
                .stream()
                .sorted()
                .forEach(System.out::println);*/
        long after=System.currentTimeMillis();
        for (int i : numList) {
            System.out.print(i + "    ");
        }
        System.out.println("算法排序使用时间(毫秒): "+(after-before));
    }
    /**
     * 插入排序,从当前元素向前比较,若前一个元素比其大就左移一个位置,直到比此元素小为止
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
     * 选择排序算法
     * 假设第一个元素是最小的,向后遍历比当前元素更小的并替换
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
                temp=nums[i];
                nums[i]=nums[x];
                nums[x]=temp;
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
        int[] nums = {
                12, 33, 14, 20, 2, 5, 3, 78, 64, 11, 23, 99, 55, 0
        };
        long before=System.currentTimeMillis();
        collectionSort();
        //insertSort(nums);
        //selectSort(nums);
        //quickSort(nums, 0, nums.length - 1);
        //bubbleSort(nums);
        long after=System.currentTimeMillis();
        for (int i : nums) {
            System.out.print(i + "    ");
        }
        System.out.println("算法排序使用时间(毫秒): "+(after-before));
    }
}