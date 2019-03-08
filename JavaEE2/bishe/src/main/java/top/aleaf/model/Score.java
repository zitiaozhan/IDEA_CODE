/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: Score
 * Author:   郭新晔
 * Date:     2019/2/11 0011 15:21
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.model;

import org.springframework.stereotype.Component;

/**
 * 〈〉
 *
 * @create 2019/2/11 0011
 */
@Component
public class Score extends BaseEntity {
    private Integer userId;
    private String name;
    private String scoreSource;
    private Float satScore;
    private Integer status = 0;

    public Integer getUserId() {
        return userId;
    }

    public Score setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScoreSource() {
        return scoreSource;
    }

    public Score setScoreSource(String scoreSource) {
        this.scoreSource = scoreSource;
        return this;
    }

    public Float getSatScore() {
        return satScore;
    }

    public Score setSatScore(Float satScore) {
        this.satScore = satScore;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public Score setStatus(Integer status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        return "Score{" +
                "userId=" + userId +
                ", scoreSource='" + scoreSource + '\'' +
                ", satScore=" + satScore +
                ", status=" + status +
                '}';
    }
}