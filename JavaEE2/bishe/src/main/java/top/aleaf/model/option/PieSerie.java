package top.aleaf.model.option;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 〈〉
 *
 * @author 郭新晔
 * @create 2019/5/6 0006
 * Author:   郭新晔
 */
@Data
public class PieSerie implements Serie {
    private String name;
    private String type = "pie";
    private String radius = "55%";
    private List<String> center;
    private List<PieData> data;
    private Map<String, PieEmphasis> itemStyle;

    public PieSerie() {
        itemStyle = Maps.newHashMap();
        itemStyle.put("emphasis", new PieEmphasis());
        center = Lists.newArrayList("50%", "60%");
    }
}