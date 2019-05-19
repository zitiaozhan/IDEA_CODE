/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: IMDBRanking
 * Author:   郭新晔
 * Date:     2019/1/13 0013 15:16
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package weightedRanking.imdbRank;

import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 〈模拟10000名用户对5部电影进行评分并排序，并发问题尚待解决，不完善〉
 *
 * @create 2019/1/13 0013
 */
public class IMDBRanking {
    static Map<Integer, RankModel> ranks = new HashMap<Integer, RankModel>();

    public static void main(String[] args) {
        testRank();
    }

    public static void testRank() {
        AtomicInteger num = new AtomicInteger(0);

        ExecutorService service = Executors.newFixedThreadPool(10);

        Runnable movieTest = new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 1000; i++) {
                        int movieId = Integer.parseInt(doubleFormat((Math.random() * 4) + 1, "#0"));
                        double score = Math.random() * 8 + 1;
                        UserRate userRate = new UserRate();
                        userRate.setMovieId(movieId);
                        if (i % 99 == 0) {
                            userRate.setEffective(false);
                        } else {
                            userRate.setEffective(true);
                        }
                        userRate.setScore(score);
                        userRate.setUserId(num.incrementAndGet());
                        System.out.println(String.format("用户：%d  评价了电影：%d", userRate.getUserId(), userRate.getMovieId()));

                        if (!ranks.containsKey(userRate.getMovieId())) {
                            ranks.put(userRate.getMovieId(), new RankModel(userRate.getMovieId()));
                        }
                        ranks.get(userRate.getMovieId()).addRate(userRate);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        for (int i = 0; i < 10; i++) {
            service.submit(movieTest);
        }
        service.shutdown();

        if (service.isShutdown()) {
            sleep(3000);
            //推出缓存值
            for (Map.Entry<Integer, RankModel> item : ranks.entrySet()) {
                item.getValue().getFinalRating();
            }
            for (Map.Entry<Integer, RankModel> item : ranks.entrySet()) {
                System.out.println(String.format("%d人评价了  电影：%d  最终评分：%s  平均评分：%s",
                        item.getValue().getEffectivePeople(),
                        item.getValue().getMovieId(),
                        item.getValue().getFinalRating() == 0 ? "暂无评分" : ("" + item.getValue().getFinalRating()),
                        item.getValue().getCurrentAverage()==0?"条件不满足，不统计":""+item.getValue().getCurrentAverage()));
            }
            System.out.println("======================排序后=========================");
            ArrayList<Map.Entry<Integer, RankModel>> result = sortMap(ranks);
            for (Map.Entry<Integer, RankModel> item : result) {
                System.out.println(String.format("%d人评价了  电影：%d  最终评分：%s  平均评分：%s",
                        item.getValue().getEffectivePeople(),
                        item.getValue().getMovieId(),
                        item.getValue().getFinalRating() == 0 ? "暂无评分" : ("" + item.getValue().getFinalRating()),
                        item.getValue().getCurrentAverage()==0?"条件不满足，不统计":""+item.getValue().getCurrentAverage()));
            }
            System.out.println("====================================================");
        }
    }

    public static ArrayList<Map.Entry<Integer, RankModel>> sortMap(Map map) {
        List<Map.Entry<Integer, RankModel>> entries = new ArrayList<Map.Entry<Integer, RankModel>>(map.entrySet());
        Collections.sort(entries,
                (Map.Entry<Integer, RankModel> o1, Map.Entry<Integer, RankModel> o2)
                        -> o1.getValue().getFinalRating() - o2.getValue().getFinalRating() < 0 ? 1 : -1);
        return (ArrayList<Map.Entry<Integer, RankModel>>) entries;
    }

    public static void sleep(int mill) {
        try {
            Thread.sleep(new Random().nextInt(mill));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String doubleFormat(double num, String pattern) {
        return pattern == null ? null : new DecimalFormat(pattern).format(num);
    }
}