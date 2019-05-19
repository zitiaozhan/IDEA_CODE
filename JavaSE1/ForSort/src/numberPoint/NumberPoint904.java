/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: NumberPoint904
 * Author:   郭新晔
 * Date:     2018/9/4 0004 10:10
 * Description: 小数保留指定位数
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package numberPoint;

import java.text.DecimalFormat;

/**
 * 〈一句话功能简述〉<br>
 * 〈小数保留指定位数〉
 *
 * @author 郭新晔
 * @create 2018/9/4 0004
 * @since 1.0.0
 */
public class NumberPoint904 {
    public static void main(String[] args) {
        System.out.println(doubleFormat(0223.1415926,"0.00000"));
    }

    static String doubleFormat(double num, String pattern) {
        return pattern == null ? null : new DecimalFormat(pattern).format(num);
    }
}