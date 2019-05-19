/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Sort517
 * Author:   郭新晔
 * Date:     2018/5/17 0017 17:42
 * Description: 排序算法温习---2018-05-17
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
 * 〈排序算法温习---2018-05-17〉
 *
 * @author 郭新晔
 * @create 2018/5/17 0017
 * @since 1.0.0
 */
public class Sort517 {
    /**
     * Lambda为集合排序
     */
    static void collectionSort(){
        List<String> stringList = Arrays.asList("Blue","MySQL","SQLSERVER","Oracle","Java");
        Collections.sort(stringList,(a,b)->a.compareTo(b));
        for(String str : stringList){
            System.out.print(str+"    ");
        }
        System.out.println();

        List<Integer> intList = Arrays.asList(12, 33, 14, 20, 2, 5, 3, 78, 64, 11, 23, 99, 55, 0);
        Collections.sort(intList,(a,b)->a-b);
        for(int i : intList){
            System.out.print(i+"    ");
        }
        System.out.println();
    }

    /**
     * 插入排序
     * @param nums  输入数字数组
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
     * @param nums  输入数字数组
     */
    static void selectSort(int[] nums){
        int i,j,x,temp;
        for(i=0;i<nums.length-1;i++){
            x=i;
            for(j=i+1;j<nums.length;j++){
                x=nums[x]>nums[j]?j:x;
            }
            if(x!=i){
                temp=nums[i];
                nums[i]=nums[x];
                nums[x]=temp;
            }
        }
    }

    /**
     * 快速选择排序
     * @param nums  输入数组参数
     * @param begin 数组开始排序索引
     * @param end   数组结束排序索引
     */
    static void quickSort(int[] nums,int begin,int end){
        if(begin<end){
            int i=begin,j=end,x=nums[i];
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
            quickSort(nums,begin,i-1);
            quickSort(nums,i+1,end);
        }
    }

    /**
     * 冒泡排序算法
     * @param nums  输入参数数组
     */
    static void bubbSort(int[] nums){
        boolean flag=false;
        for(int i=0;i<nums.length-1;i++){
            flag=false;
            for(int j=0;j<nums.length-1-i;j++){
                if(nums[j+1]<nums[j]){
                    int temp=nums[j];
                    nums[j]=nums[j+1];
                    nums[j+1]=temp;
                    flag=true;
                }
            }
            if(!flag){
                break;
            }
        }
    }

    public static void main(String[] args){
        int[] nums = {
                12, 33, 14, 20, 2, 5, 3, 78, 64, 11, 23, 99, 55, 0
        };
        collectionSort();
        //insertSort(nums);
        //selectSort(nums);
        //quickSort(nums, 0, nums.length - 1);
        //bubbSort(nums);
        for (int i : nums) {
            System.out.print(i + "    ");
        }
    }
}