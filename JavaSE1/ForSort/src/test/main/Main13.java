/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Main13
 * Author:   郭新晔
 * Date:     2018/9/27 0027 19:47
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package test.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author 郭新晔
 * @create 2018/9/27 0027
 * @since 1.0.0
 */
public class Main13 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String s, dict;
        while (input.hasNext()) {
            s = input.nextLine();
            dict = input.nextLine();
            getResult(s, dict);
        }
    }

    static void getResult(String s,String dict){
        List<String> words=new ArrayList<>();
        int l=0,c=0;
        for (int i=0;i<dict.length();i++){
            char cc=dict.charAt(i);
            char c1=dict.charAt(i+1);
            if (cc=='\"'&&c1!='\"'){
                l++;
            }
            if (cc!='\"'&&c1=='\"'){
                c--;
                if(c>l){
                    words.add(dict.substring(l,c+1));
                }
            }
        }
    }
}