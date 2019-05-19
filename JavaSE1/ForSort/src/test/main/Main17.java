/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Main17
 * Author:   郭新晔
 * Date:     2018/9/29 0029 21:11
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package test.main;

import java.util.Scanner;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author 郭新晔
 * @create 2018/9/29 0029
 * @since 1.0.0
 */
public class Main17 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String xStr;
        while (input.hasNext()) {
            xStr = input.nextLine();
            System.out.println(getResult(xStr));
        }
    }

    static boolean getResult(String str) {
        str = str.replaceAll("\\]", "");
        str = str.replaceAll("\\[", "");
        String[] nums = str.split(",");
        for (int i = 0; i < nums.length; ) {
            int x = Integer.parseInt(nums[i]);
            nums[i] = "0";
            if (x == 0) {
                break;
            }
            i += x;
            if (i < 0 || i > nums.length - 1) {
                return true;
            }
        }
        return false;
    }
}