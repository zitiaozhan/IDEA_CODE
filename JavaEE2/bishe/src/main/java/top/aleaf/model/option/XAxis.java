package top.aleaf.model.option;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class XAxis {
    private String type = "value";
    private List<Double> boundaryGap;

    public XAxis() {
        boundaryGap = Lists.newArrayList(0d, 0.01);
    }
}