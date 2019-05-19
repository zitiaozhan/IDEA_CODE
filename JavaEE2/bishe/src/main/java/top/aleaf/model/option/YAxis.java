package top.aleaf.model.option;

import lombok.Data;

import java.util.List;

/**
 * 〈〉
 *
 * @author 郭新晔
 * @create 2019/5/6 0006
 * Author:   郭新晔
 */
@Data
public class YAxis {
    private String type = "category";
    private List<String> data;
}