/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: NumberPoint702
 * Author:   郭新晔
 * Date:     2018/7/2 0002 16:51
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package numberPoint;

import java.text.DecimalFormat;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author 郭新晔
 * @create 2018/7/2 0002
 * @since 1.0.0
 */
public class NumberPoint702 {
    public static void main(String[] args) {
        System.out.println(formatDouble(3.1415926,"#0.0000"));
    }

    /**
     * <方法名称>:
     * <方法功能描述>:
     * <方法参数>:
     * <方法创建时间>:
     */
    public static String formatDouble(double num,String pattern){
        return pattern==null?null:new DecimalFormat(pattern).format(num);
    }
}