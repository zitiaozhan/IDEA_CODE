/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: Score
 * Author:   郭新晔
 * Date:     2019/2/11 0011 15:21
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.model;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 〈〉
 *
 * @author 郭新晔
 * @create 2019/2/11 0011
 */
@Component
@Data
public class Score extends BaseEntity {
    private String userNumber;
    private String name;
    private Integer projectNum;
    private String scoreSource;
    private Double satScore;
    private Integer status = 0;
}