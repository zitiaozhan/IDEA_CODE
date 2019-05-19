package top.aleaf.model.option;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * 〈echarts数据option〉
 *
 * @create 2019/5/6 0006
 * Author:   郭新晔
 */
@Data
public class Option {
    private Title title;
    private Tooltip tooltip;
    private Legend legend;
    private Grid grid;
    private XAxis xAxis;
    private YAxis yAxis;
    private List<Serie> series;

    public void series(Serie... series) {
        this.series = Lists.newArrayList(series);
    }

    public Option title(Title title) {
        this.title = title;
        return this;
    }

    public Option tooltip(Tooltip tooltip) {
        this.tooltip = tooltip;
        return this;
    }

    public Option legend(Legend legend) {
        this.legend = legend;
        return this;
    }

    public Option grid(Grid grid) {
        this.grid = grid;
        return this;
    }

    public Option xAxis(XAxis xAxis) {
        this.xAxis = xAxis;
        return this;
    }

    public Option yAxis(YAxis yAxis) {
        this.yAxis = yAxis;
        return this;
    }
}