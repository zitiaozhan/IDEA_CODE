/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: RankModel
 * Author:   郭新晔
 * Date:     2019/1/13 0013 15:42
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package weightedRanking.imdbRank;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 〈〉
 *
 * @create 2019/1/13 0013
 */
public class RankModel {
    //最低投票人数
    private static final int MINIMUMPEOPLE = 1250;
    //所有电影平均分数
    private static final double AVERAGESCORE = 4.6;
    //当前平均分数
    private volatile double currentAverage;
    //有效人数
    private volatile int effectivePeople;
    //当前电影序列
    private int movieId;
    //最终评分
    private volatile double finalRating;
    //用户与评分
    private List<UserRate> rates;

    public RankModel(int movieId) {
        this.movieId = movieId;
    }

    public int getMovieId() {
        return movieId;
    }

    public double getFinalRating() {
        //获得有效人数
        this.effectivePeople = this.rates.size();

        if (this.effectivePeople >= MINIMUMPEOPLE) {
            ExecutorService service = Executors.newSingleThreadExecutor();
            //计算当前总分数
            Future<Double> total = service.submit(new Callable<Double>() {
                @Override
                public Double call() throws Exception {
                    double total = 0;
                    for (UserRate item : rates) {
                        total += item.getScore();
                    }
                    return total;
                }
            });

            try {
                //获得当前平均值
                this.currentAverage = total.get() / this.effectivePeople;
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.finalRating =
                    ((double) this.effectivePeople / (this.effectivePeople + MINIMUMPEOPLE)) * this.currentAverage
                            +
                            ((double) MINIMUMPEOPLE / (this.effectivePeople + MINIMUMPEOPLE)) * AVERAGESCORE;
            this.finalRating = Double.parseDouble(doubleFormat(this.finalRating, "#0.00"));
        }

        return finalRating;
    }

    public double getCurrentAverage() {
        return currentAverage;
    }

    public int getEffectivePeople() {
        return effectivePeople;
    }

    public void addRate(UserRate userRate) {
        this.rates = this.rates == null ? new ArrayList<>(2000) : this.rates;
        if (userRate.isEffective()) {
            this.rates.add(userRate);
        }
    }

    private String doubleFormat(double num, String pattern) {
        return pattern == null ? null : new DecimalFormat(pattern).format(num);
    }
}