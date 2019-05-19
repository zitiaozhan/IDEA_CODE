/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: NumberPoint0624
 * Author:   郭新晔
 * Date:     2018/6/24 0024 18:05
 * Description: 小数的小数位保留---2018-06-24
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package numberPoint;

import java.text.DecimalFormat;

/**
 * 〈一句话功能简述〉<br> 
 * 〈小数的小数位保留---2018-06-24〉
 *
 * @author 郭新晔
 * @create 2018/6/24 0024
 * @since 1.0.0
 */
public class NumberPoint0624 {
    public static void main(String[] args) {
        System.out.println(formatDouble(3.1415926,"0.0000"));
    }

    /**
     * 转化double类型的小数
     * @param num
     * @param pattern
     * @return
     */
    static String formatDouble(double num,String pattern){
        return pattern==null?null:new DecimalFormat(pattern).format(num);
    }
}