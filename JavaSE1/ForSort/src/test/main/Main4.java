/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Main4
 * Author:   郭新晔
 * Date:     2018/9/18 0018 20:12
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package test.main;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author 郭新晔
 * @create 2018/9/18 0018
 * @since 1.0.0
 */
public class Main4 {

    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMdd");
        Date d1=new Date();
        int lastDay=0;
        try{
            d1=sdf.parse("0901");
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.MONTH, d1.getMonth());
            lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(""+(d1.getMonth()+1)+lastDay);

        System.out.println(lastDay+=lastDay-=lastDay+=lastDay);

        int x=8;
        int f=x>>2;
        System.out.println(x);
        System.out.println(f);
        x>>=2;
        System.out.println(x);
    }
}