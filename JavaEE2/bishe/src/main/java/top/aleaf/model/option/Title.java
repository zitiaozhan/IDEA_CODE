package top.aleaf.model.option;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 〈echarts标题〉
 *
 * @author 郭新晔
 * @create 2019/5/6 0006
 * Author:   郭新晔
 */
@Data
@AllArgsConstructor
public class Title {
    private String text;
    private String subText;
    //横向位置 center居中
    private String x;
}