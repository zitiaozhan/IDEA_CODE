package test.main;

import java.util.ArrayList;

public class Mid713 {
    public static void main(String[] args) {
        int m = 1;
        int n = 22;
        System.out.print(f(m, n));
    }

    static String f(int m, int n) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        StringBuffer sb = new StringBuffer();
        m = m % n;
        while (m != 0) {

            if (list.contains(m)) {
                int i = 0;
                for (; i < sb.length(); i++) {
                    //记录循环节开始的位置
                    if (sb.charAt(i) - '0' == m * 10 / n) {
                        break;
                    }
                }
                //sb.delete(0, i);
                break;

            } else {
                //转入余数
                list.add(m);
                //装入商的值
                sb.append(m * 10 / n);
            }
            m = m * 10 % n;
        }
        return sb.toString();
    }
}