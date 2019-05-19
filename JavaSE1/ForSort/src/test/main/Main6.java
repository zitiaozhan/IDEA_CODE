/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Main6
 * Author:   郭新晔
 * Date:     2018/9/20 0020 21:43
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package test.main;

import java.util.Arrays;
import java.util.Scanner;

public class Main6 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int m=0,n=0;
        int[] pear;
        Helper[] monkeys;
        while (input.hasNext()) {
            n=input.nextInt();
            m=input.nextInt();
            monkeys=new Helper[n];
            pear=new int[m];
            for (int i=0;i<m;i++){
                pear[i]=input.nextInt();
            }
            for (int i=0;i<n;i++){
                monkeys[i]=new Helper(input.nextInt(),input.nextInt());
                input.nextLine();
            }
            int[] res=getResult(n,m,monkeys,pear);
            for(int item : res){
                System.out.println(item);
            }
        }
    }

    static int[] getResult(int n,int m,Helper[] monkeys,int[] pear){
        if (n>10||m>100){
            return null;
        }
        int[] res=new int[n];
        int x=m,index=0;
        Arrays.sort(pear);
        Arrays.sort(monkeys,(Helper h1,Helper h2)->h1.pk-h2.pk);
        while (n>0){
            x=m;
            while (x>0){
                if (monkeys[n-1].hungry>=pear[x-1]){
                    monkeys[n-1].hungry-=pear[x-1];
                    pear[x-1]=0;
                }
                if(x==1){
                    res[index++]=monkeys[n-1].hungry;
                    break;
                }
                x--;
            }
            n--;
        }
        return res;
    }
}
class Helper{
    int pk,hungry;
    public Helper(int pk,int hungry){
        this.pk=pk;
        this.hungry=hungry;
    }
}