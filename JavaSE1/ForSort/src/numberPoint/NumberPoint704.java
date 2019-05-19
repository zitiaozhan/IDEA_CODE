/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: NumberPoint704
 * Author:   郭新晔
 * Date:     2018/7/4 0004 16:32
 * Description: 保留指定的小数位数---20018-07-04
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package numberPoint;

import java.text.DecimalFormat;

/**
 * 〈一句话功能简述〉<br> 
 * 〈保留指定的小数位数---20018-07-04〉
 *
 * @author 郭新晔
 * @create 2018/7/4 0004
 * @since 1.0.0
 */
public class NumberPoint704 {
    /**
     * <方法名称>:doubleFormat
     * <方法功能描述>:浮点数的指定格式格式化
     * <方法参数>:
     * <方法创建时间>:
     */
    public static String doubleFormat(double num,String pattern){
        return pattern==null?null:new DecimalFormat(pattern).format(num);
    }

    public static void main(String... args) {
        System.out.println(doubleFormat(3.1415926,"#0.000"));
    }
}