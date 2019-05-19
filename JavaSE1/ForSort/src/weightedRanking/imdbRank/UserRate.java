/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: UserRate
 * Author:   郭新晔
 * Date:     2019/1/13 0013 16:10
 * Description: 用户与评分
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package weightedRanking.imdbRank;

/**
 * 〈用户与评分〉
 * @create 2019/1/13 0013
 */
public class UserRate {
    private int userId;
    private int movieId;
    private double score;
    private boolean isEffective;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public boolean isEffective() {
        return isEffective;
    }

    public void setEffective(boolean effective) {
        isEffective = effective;
    }
}