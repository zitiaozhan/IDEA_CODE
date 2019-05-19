/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: NumberPoint607
 * Author:   郭新晔
 * Date:     2018/6/7 0007 21:43
 * Description: 自定义保留小数位数温习
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package numberPoint;

import java.text.DecimalFormat;

/**
 * 〈一句话功能简述〉<br> 
 * 〈自定义保留小数位数温习〉
 *
 * @author 郭新晔
 * @create 2018/6/7 0007
 * @since 1.0.0
 */
public class NumberPoint607 {
    public static void main(String[] args){
        double num=0.3366;
        System.out.println(getDecimal(num,"#0.0000"));
    }

    static String getDecimal(double num,String pattern) {
        DecimalFormat dFormat = new DecimalFormat(pattern);
        String str = dFormat.format(num);
        return str;
    }
}