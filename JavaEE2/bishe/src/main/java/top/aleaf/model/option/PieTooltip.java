package top.aleaf.model.option;

import com.google.common.collect.Maps;
import lombok.Data;

import java.util.Map;

/**
 * 〈echarts工具条〉
 *
 * @author 郭新晔
 * @create 2019/5/6 0006
 * Author:   郭新晔
 */
@Data
public class PieTooltip implements Tooltip {
    private String trigger;
    private String formatter;
    private Map<String, String> axisPointer;

    public PieTooltip() {
        this.axisPointer = Maps.newHashMap();
    }

    public PieTooltip trigger(String trigger) {
        this.trigger = trigger;
        return this;
    }

    public PieTooltip formatter(String formatter) {
        this.formatter = formatter;
        return this;
    }

    public PieTooltip addElement(String key, String value) {
        this.axisPointer.put(key, value);
        return this;
    }
}