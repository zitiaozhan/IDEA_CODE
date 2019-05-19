/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Sort506
 * Author:   郭新晔
 * Date:     2018/5/6 0006 12:42
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
 * @create 2018/5/6 0006
 * @since 1.0.0
 */
public class Sort506 {
    /**
     * 直接插入排序
     * 从数组的第一个元素开始遍历,
     * 如果下一个元素小于(大于)当前元素
     * 则依次向前比较并将当前元素后移一个位置
     * 直到到大于(小于)了为止
     * @param nums  传入数组
     */
    static void insertSort(int[] nums){
        int i,j,x;
        for(i=0;i<nums.length-1;i++){
            j = i;
            x=nums[i+1];
            while(j>-1&&x<nums[j]){
                nums[j+1] = nums[j--];
            }
            nums[j+1] = x;
        }
    }

    /**
     * 直接选择排序
     * 从第一个元素开始遍历,直到倒数第二个元素
     * 将当前遍历的元素的下标当作最小(最大)的存储为x
     * 然后遍历嵌套依次遍历直到最后一个元素,并判断当前元素值是否小于(大于)x下标中的值
     * 若小于(大于),则x等于当前下标
     * 若x!=i,则将nums[i]与nums[x]的值互换
     * @param nums  传入数组
     */
    static void selectSort(int[] nums){
        int i,j,x;
        for(i=0;i<nums.length-1;i++){
            x=i;
            for(j=i+1;j<nums.length;j++){
                x=nums[j]<nums[x]?j:x;
            }
            if(x!=i){
                int temp=nums[i];
                nums[i]=nums[x];
                nums[x]=temp;
            }
        }
    }

    /**
     * 快速排序
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