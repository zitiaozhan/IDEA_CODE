/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Test918
 * Author:   郭新晔
 * Date:     2018/9/18 0018 11:24
 * Description: 递归测试
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package testRecursive;

import java.util.HashSet;

/**
 * 〈一句话功能简述〉<br>
 * 〈递归测试〉
 *
 * @author 郭新晔
 * @create 2018/9/18 0018
 * @since 1.0.0
 */
public class Test918 {
    public static void main(String[] args) {
        /*int[] arr={3,2,7,13};
        boolean res=isSum(arr,0,28);
        System.out.println(res);*/

        //printSubStr("abc",0,"");
        //process2("abc".toCharArray(), 0);
        int res = getRes(5);
        System.out.println(res);
    }

    /**
     * 将数组的元素任意个相加是否能得到num结果
     *
     * @param arr
     * @param i
     * @param num
     * @return
     */
    static boolean isSum(int[] arr, int i, int num) {
        if (i == arr.length) {
            return num == 0;
        }
        return isSum(arr, i + 1, num - arr[i]) || isSum(arr, i + 1, num);
    }

    static int getRes(int n) {
        if (n == 1) {
            return 1 * 5 + 1;
        }
        return 5 * getRes(n - 1) / 4 + 1;
    }

    /**
     * 找出某个字符串的所有子序列
     *
     * @param str
     * @param index
     * @param tmp
     */
    static void printSubStr(String str, int index, String tmp) {
        if (index == str.length()) {
            System.out.println(tmp);
            return;
        }
        printSubStr(str, index + 1, tmp);
        printSubStr(str, index + 1, tmp + str.charAt(index));
    }

    static void printAllArray(String str, int index, String tmp) {

    }

    public static void process2(char[] chs, int i) {
        if (i == chs.length) {
            System.out.println(String.valueOf(chs));
        }
        HashSet<Character> set = new HashSet<>();
        for (int j = i; j < chs.length; j++) {
            if (!set.contains(chs[j])) {
                set.add(chs[j]);
                swap(chs, i, j);
                process2(chs, i + 1);
                //swap(chs, i, j);
            }
        }
    }

    static void swap(char[] chs, int a, int b) {
        if (chs == null || a == b || chs[a] == chs[b]) {
            return;
        }
        char tmp = chs[a];
        chs[a] = chs[b];
        chs[b] = tmp;
    }
}