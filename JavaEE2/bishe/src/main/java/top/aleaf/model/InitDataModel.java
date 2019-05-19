/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: InitDataModel
 * Author:   郭新晔
 * Date:     2019/4/7 0007 11:03
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * 〈〉
 *
 * @author 郭新晔
 * @create 2019/4/7 0007
 */
@Component
public class InitDataModel {
    public static Map<Integer, List<Double>> scoreAllocation = Maps.newHashMap();

    @PostConstruct
    public void initData() {
        scoreAllocation.put(1, Lists.newArrayList(1.0));
        scoreAllocation.put(2, Lists.newArrayList(0.65, 0.35));
        scoreAllocation.put(3, Lists.newArrayList(0.55, 0.25, 0.2));
        scoreAllocation.put(4, Lists.newArrayList(0.5, 0.25, 0.15, 0.1));
        scoreAllocation.put(5, Lists.newArrayList(0.5, 0.25, 0.1, 0.1, 0.05));
        scoreAllocation.put(6, Lists.newArrayList(0.45, 0.2, 0.1, 0.1, 0.1, 0.05));
        scoreAllocation.put(7, Lists.newArrayList(0.4, 0.2, 0.1, 0.1, 0.1, 0.05, 0.05));
        scoreAllocation.put(8, Lists.newArrayList(0.38, 0.2, 0.1, 0.1, 0.1, 0.05, 0.05, 0.02));
        scoreAllocation.put(9, Lists.newArrayList(0.38, 0.2, 0.1, 0.1, 0.1, 0.05, 0.05, 0.01, 0.01));
        scoreAllocation.put(10, Lists.newArrayList(0.38, 0.2, 0.1, 0.1, 0.1, 0.05, 0.04, 0.01, 0.01, 0.01));
        scoreAllocation.put(11, Lists.newArrayList(0.38, 0.2, 0.1, 0.1, 0.1, 0.04, 0.04, 0.01, 0.01, 0.01, 0.01));
    }
}