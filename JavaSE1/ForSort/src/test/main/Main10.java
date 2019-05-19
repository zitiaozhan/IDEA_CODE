/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Main10
 * Author:   郭新晔
 * Date:     2018/9/25 0025 11:08
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package test.main;
import java.util.*;
/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author 郭新晔
 * @create 2018/9/25 0025
 * @since 1.0.0
 */
public class Main10 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String destThread="";
        StringBuilder text=new StringBuilder();
        while (input.hasNext()){
            destThread=input.nextLine();
            String temp="1234";
            while (temp.length()!=0){
                text.append(temp).append("\n");
                temp=input.nextLine();
            }
            String[] content=text.toString().split("\t");
            for (int i=0;i<content.length;i++){
                String last="";
                String next="";
                if (i>0){
                    last=content[i-1];
                }
                if (i<content.length-1){
                    next=content[i+1];
                }
                String xx=content[i];
                if (destThread.equals(xx)){
                    System.out.print(last+"\t");
                    System.out.print(xx+"\t");
                    System.out.print(next+"\n");
                }
            }
        }
    }
}