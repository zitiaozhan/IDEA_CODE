/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Main19
 * Author:   郭新晔
 * Date:     2018/10/8 0008 20:03
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package test.main;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author 郭新晔
 * @create 2018/10/8 0008
 * @since 1.0.0
 */
public class Main19 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int money, count;
        ArrayList<Zhb> zs = new ArrayList<>();
        money = input.nextInt();
        count = input.nextInt();
        int i = 0;
        while (input.hasNext()) {
            Zhb tmp1 = new Zhb(input.nextInt(), Integer.parseInt(input.nextLine().trim()));
            zs.add(tmp1);
            if (i++ == 3) {
                break;
            }
        }
        System.out.println(getResult(money, count, zs));
    }

    static int getResult(int money, int count, ArrayList<Zhb> zs) {
        int res = 0;
        Zhb tmp1;
        float[] values = new float[zs.size()];
        for (int i = 0; i < zs.size(); i++) {
            tmp1 = zs.get(i);
            values[i] = tmp1.price > money ? -1 : (float) tmp1.price / (float) tmp1.level;
        }
        while (money > 0 && count > 0) {
            int index = getMinIndex(values);
            if (index == -1) {
                return res;
            }
            tmp1 = zs.get(index);
            res += tmp1.level;
            money -= tmp1.price;
            count--;
        }

        return res;
    }

    static int getMinIndex(float[] values) {
        int min = 0;
        for (int i = 1; i < values.length; i++) {
            if (values[i] == -1) {
                continue;
            }
            if (values[min] == -1 && values[i] != -1) {
                min = i;
            }
            min = values[i] < values[min] ? i : min;
        }
        min = values[min] == -1 ? -1 : min;
        values[min] = -1;
        return min;
    }
}

class Zhb {
    int price;
    int level;

    public Zhb(int price, int level) {
        this.price = price;
        this.level = level;
    }
}