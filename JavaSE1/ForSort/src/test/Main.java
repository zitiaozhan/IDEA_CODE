package test;

import java.util.*;
public class Main{
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        int n,m,k,c;
        int[] wms;
        int[][] grade;
        while(input.hasNext()){
            n=input.nextInt();
            m=input.nextInt();
            k=input.nextInt();
            c=input.nextInt();
            wms=new int[m];
            grade=new int[n][m];
            for(int i=0;i<m;i++){
                wms[i]=input.nextInt();
            }
            for(int i=0;i<n;i++){
                for(int j=0;j<m;j++){
                    grade[i][j]=input.nextInt();
                }
            }
            printResult(n,k,c,wms,grade);
        }
    }
    
    static void printResult(int n,int k,int c,int[] wms,int[][] grade){
        int[] res=new int[n];
        int[] numbers=new int[n];
        int[] numbs=new int[n];
        int temp=-1,maxg=0;
        for(int i=0;i<n;i++){
            for(int j=0;j<wms.length;j++){
                if(grade[i][j]==-1){
                    temp=i;
                    continue;
                }
                res[i]+=wms[j]*grade[i][j];
            }
        }
        System.arraycopy(res,0,numbers,0,n);
        System.arraycopy(res,0,numbs,0,n);

        for(int j=0;j<wms.length;j++){
            if(grade[temp][j]==-1){
                maxg+=wms[j]*c;
                continue;
            }
            maxg+=wms[j]*grade[temp][j];
        }
        numbs[temp]=maxg;

        int[] nums=insertSort(numbers);
        int[] nums2=insertSort(numbs);
        int index1=search(nums,res[temp]);
        int index2=search(nums2,maxg);
        if(index1<=k-1){
            for(int i=0;i<res.length;i++){
                if(res[i]==nums[k-1]){
                    System.out.println(3);
                    continue;
                }
                if(i==temp){
                    System.out.println(1);
                    continue;
                }
                System.out.println(search(nums,res[i])<=k-1?1:2);
            }
        }else if(index1>k-1&&index2<=k-1){
            for(int i=0;i<res.length;i++){
                if(res[i]==nums2[k-1]){
                    System.out.println(3);
                    continue;
                }
                if(i==temp){
                    System.out.println(3);
                    continue;
                }
                int x=search(nums,res[i]);
                System.out.println(x<k-1?1:x==k-1?3:2);
            }
        }else{
            for(int i=0;i<res.length;i++){
                if(res[i]==nums2[k-1]){
                    System.out.println(3);
                    continue;
                }
                if(i==temp){
                    System.out.println(2);
                    continue;
                }
                System.out.println(search(nums,res[i])<=k-1?1:2);
            }
        }
    }

    static int[] insertSort(int[] nums){
        int i,j,x;
        for(i=0;i<nums.length-1;i++){
            j=i;
            x=nums[i+1];
            while(j>-1&&x>nums[j]){
                nums[j+1]=nums[j--];
            }
            nums[j+1]=x;
        }
        return nums;
    }
    static int search(int[] arr,int key){
        int low = 0;
        int high = arr.length - 1;
        int middle = 0;

        if(key > arr[low] || key < arr[high] || low > high){
            return -1;
        }
        while(low <= high){
            middle = (low + high) / 2;
            if(arr[middle] > key){
                low = middle + 1;
            }else if(arr[middle] < key){
                high = middle - 1;
            }else{
                return middle;
            }
        }
        return -1;
    }
}