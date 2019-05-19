package top.aleaf.model.option;

import com.google.common.collect.Maps;
import lombok.Data;

import java.util.Map;

/**
 * 〈〉
 *
 * @author 郭新晔
 * @create 2019/5/6 0006
 * Author:   郭新晔
 */
@Data
public class BarTooltip implements Tooltip {
    private String trigger = "axis";
    private Map<String, String> axisPointer;

    public BarTooltip() {
        this.axisPointer = Maps.newHashMap();
        axisPointer.put("type", "shadow");
    }
}