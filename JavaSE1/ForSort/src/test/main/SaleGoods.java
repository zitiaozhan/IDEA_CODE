package test.main;

import java.text.*;
import java.util.*;

/**
 * 美团在吃喝玩乐等很多方面都给大家提供了便利。
 * 最近又增加了一项新业务：小象生鲜。
 * 这是新零售超市，你既可以在线下超市门店选购生鲜食品，
 * 也可以在手机App上下单，最快30分钟就配送到家。
 * 新店开张免不了大优惠。我们要在小象生鲜超市里采购n个物品，
 * 每个物品价格为ai，有一些物品可以选择八折优惠（称为特价优惠）。
 * 有m种满减优惠方式，满减优惠方式只有在所有物品都不选择特价优惠时才能使用，
 * 且最多只可以选择最多一款。
 * 每种满减优惠描述为(bi,ci)，即满bi减ci（当消费>=bi时优惠ci）。
 * 求要买齐这n个物品（必须一单买齐），至少需要多少钱（保留两位小数）。
 */
public class SaleGoods {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int n, m;
        int[] products;
        int[] sale1;
        int[] prefs;
        int[] sale2;
        double money = 0;
        while (input.hasNext()) {
            n = input.nextInt();
            m = input.nextInt();
            products = new int[n];
            sale1 = new int[n];
            prefs = new int[m];
            sale2 = new int[m];
            for (int i = 0; i < n; i++) {
                products[i] = input.nextInt();
                sale1[i] = input.nextInt();
            }
            for (int i = 0; i < m; i++) {
                prefs[i] = input.nextInt();
                sale2[i] = input.nextInt();
            }
            double b1 = buy1(n, products, sale1);
            double b2 = buy2(n, products, m, prefs, sale2);
            money = b1 < b2 ? b1 : b2;
            System.out.println(getTwoDecimal(money));
        }
    }

    static double buy1(int n, int[] products, int[] sale1) {
        double sum = 0;
        for (int i = 0; i < n; i++) {
            sum += sale1[i] == 1 ? products[i] * 0.8 : products[i];
        }
        return sum;
    }

    static double buy2(int n, int[] products, int m, int[] prefs, int[] sale2) {
        double sum = -1;
        int sp = 0;
        for (int i = 0; i < n; i++) {
            sp += products[i];
        }
        sum = sp;
        for (int i = 0; i < m; i++) {
            if (sp >= prefs[i]) {
                double temp = sp - sale2[i];
                sum = sum > temp ? temp : sum;
            }
        }
        return sum;
    }

    static String getTwoDecimal(double num) {
        DecimalFormat dFormat = new DecimalFormat("#.00");
        String str = dFormat.format(num);
        if (str.substring(str.indexOf("."), str.length()).length() < 2) {
            str += "0";
        }
        return str;
    }
}