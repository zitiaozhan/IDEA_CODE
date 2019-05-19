/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: NumberPointer620
 * Author:   郭新晔
 * Date:     2018/6/20 0020 19:19
 * Description: 保留指定小数位
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package numberPoint;

import java.text.DecimalFormat;

/**
 * 〈一句话功能简述〉<br>
 * 〈保留指定小数位〉
 *
 * @author 郭新晔
 * @create 2018/6/20 0020
 * @since 1.0.0
 */
public class NumberPointer620 {
    public static void main(String... args) {
        System.out.println(convertPoint(3.1415926, "#0.0000"));
    }

    /**
     * DecimalFormat 是 NumberFormat 的一个具体子类，用于格式化十进制数字。
     * 该类设计有各种功能
     *
     * @param num
     * @param pattern
     * @return
     */
    static String convertPoint(double num, String pattern) {
        if (pattern == null && pattern.equals("")) {
            return null;
        }
        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(num);
    }
}