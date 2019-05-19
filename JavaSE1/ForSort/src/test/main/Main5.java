/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Main5
 * Author:   郭新晔
 * Date:     2018/9/20 0020 21:26
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package test.main;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main5 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String xStr;
        while (input.hasNext()) {
            xStr = input.next();
            System.out.println(getResult(xStr));
        }
    }

    static String getResult(String xStr) {
        if (xStr == null || "".equals(xStr) || xStr.length() > 20) {
            return "";
        }
        String res = "";
        char cc;
        int min = Integer.MAX_VALUE;
        Map<Character, Integer> helpMap = new HashMap<>();
        for (int i = 0; i < xStr.length(); i++) {
            cc = xStr.charAt(i);
            if (cc < 97 || cc > 122) {
                return "";
            }
            if (helpMap.containsKey(cc)) {
                helpMap.replace(cc, helpMap.get(cc) + 1);
            } else {
                helpMap.put(cc, 1);
            }
        }
        for (int item : helpMap.values()) {
            min = min < item ? min : item;
        }
        for (int i = 0; i < xStr.length(); i++) {
            cc = xStr.charAt(i);
            if (helpMap.get(cc) > min) {
                res += cc;
            }
        }
        return res;
    }
}