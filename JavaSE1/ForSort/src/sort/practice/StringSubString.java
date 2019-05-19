/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: StringSubString
 * Author:   郭新晔
 * Date:     2018/9/14 0014 22:24
 * Description: 求出字符串的所有子串
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package sort.practice;

/**
 * 〈一句话功能简述〉<br>
 * 〈求出字符串的所有子串〉
 *
 * @author 郭新晔
 * @create 2018/9/14 0014
 * @since 1.0.0
 */
public class StringSubString {

    public void getAllSubstrings(String str) {
        if (str.length() == 0){
            return;
        }
        else {
            for (int i = 0; i < str.length(); i++) {
                for (int j = i; j < str.length() - 1; j++) {
                    System.out.println(str.substring(i, i + j));
                }
            }
        }
    }

}