package top.aleaf.model.option;

import com.google.common.collect.Lists;
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
public class PieLegend implements Legend {
    private String orient;
    private String left;
    private List<String> data;

    public PieLegend() {
        data = Lists.newArrayList();
    }

    public PieLegend setOrient(String orient) {
        this.orient = orient;
        return this;
    }

    public PieLegend setLeft(String left) {
        this.left = left;
        return this;
    }

    public PieLegend addData(String value) {
        data.add(value);
        return this;
    }
}