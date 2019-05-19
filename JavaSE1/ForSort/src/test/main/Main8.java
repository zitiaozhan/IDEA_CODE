/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Main8
 * Author:   郭新晔
 * Date:     2018/9/21 0021 20:34
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package test.main;

import java.util.Scanner;
import java.util.Stack;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author 郭新晔
 * @create 2018/9/21 0021
 * @since 1.0.0
 */
public class Main8 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String xStr;
        while (input.hasNext()) {
            xStr = input.nextLine();
            getResult(xStr);
        }
    }

    static void getResult(String xStr) {
        if (xStr == null || "".equals(xStr)) {
            return;
        }
        Stack<String> helpStack=new Stack<>();
        String[] ress=xStr.split(" ");
        for(String item : ress){
            helpStack.push(item);
        }
        while (!helpStack.isEmpty()){
            if (helpStack.size()!=1){
                System.out.print(helpStack.pop()+" ");
            }else {
                System.out.print(helpStack.pop());
            }
        }
    }
}