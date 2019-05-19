/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: NumberPointer618
 * Author:   郭新晔
 * Date:     2018/6/18 0018 22:16
 * Description: 保留指定位数的小数
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package numberPoint;

import java.text.DecimalFormat;

/**
 * 〈一句话功能简述〉<br> 
 * 〈保留指定位数的小数〉
 *
 * @author 郭新晔
 * @create 2018/6/18 0018
 * @since 1.0.0
 */
public class NumberPointer618 {
    public static void main(String[] args){
        System.out.println("num = "+getDecimal(3.1415926,"#00.0000"));
    }
    static String getDecimal(double num,String pattern){
        DecimalFormat df=new DecimalFormat(pattern);
        return df.format(num);
    }
}