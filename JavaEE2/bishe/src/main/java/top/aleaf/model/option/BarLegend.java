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
public class BarLegend implements Legend {
    private List<String> data;

    public BarLegend() {
        data = Lists.newArrayList();
    }

    public BarLegend addData(String value) {
        data.add(value);
        return this;
    }
}