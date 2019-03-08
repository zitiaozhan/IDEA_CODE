/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: ConstantUtil
 * Author:   郭新晔
 * Date:     2019/2/15 0015 15:22
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.aleaf.mapper.StringsMapper;
import top.aleaf.model.Strings;

import java.util.*;

/**
 * 〈〉
 *
 * @create 2019/2/15 0015
 */
@Component
public class ConstantUtil implements InitializingBean {
    public static final int PROJECT_STATUS_SUBMIT = 0;
    public static final int PROJECT_STATUS_SUCCESS = 1;
    public static final int PROJECT_STATUS_REFUSE = 2;
    public static final int PROJECT_STATUS_BACK = 3;
    public static final int PROJECT_STATUS_DELETE = 4;
    public static final String MAIL_VALIDATE_SPLIT = "#MAIL#";

    public Map<String, Map<String, List<String>>> optionDatas = new HashMap<>();

    @Autowired
    private StringsMapper stringsMapper;

    @Override
    public void afterPropertiesSet() throws Exception {
        List<Strings> stringsList1 = stringsMapper.selectByExample(GeneralExample.getConditionAndGroupAndOrderExample(
                Strings.class, " entity_type ASC ", " GROUP BY entity_type ", "STATUS=0 "));
        for (Strings item1 : stringsList1) {
            Map<String, List<String>> fieldValues = new HashMap<>();
            List<Strings> stringsList2 = stringsMapper.selectByExample(GeneralExample.getConditionAndGroupExample(
                    Strings.class, " GROUP BY entity_field", "entity_type=" + item1.getEntityType() + " "));
            for (Strings item2 : stringsList2) {
                List<Strings> stringsList3 = stringsMapper.selectByExample(GeneralExample.getConditionExample(
                        Strings.class, "entity_type=" + item1.getEntityType() + " AND entity_field='" + item2.getEntityField() + "' "));
                List<String> values = new ArrayList<>();
                for (Strings item3 : stringsList3) {
                    values.add(item3.getContent());
                }
                fieldValues.put(item2.getEntityField(), values);
            }
            optionDatas.put("" + item1.getEntityType(), fieldValues);
        }

        /*Map<String, List<String>> fieldValues1 = new HashMap<>();
        fieldValues1.put("projectType", Arrays.asList("科技服务", "科技开发", "科学研究"));
        fieldValues1.put("projectStatus", Arrays.asList("已立项", "已到款", "已结题"));
        optionDatas.put("1", fieldValues1);

        Map<String, List<String>> fieldValues2 = new HashMap<>();
        fieldValues2.put("projectType", Arrays.asList("国家级", "省部级", "厅局级", "校级"));
        fieldValues2.put("projectStatus", Arrays.asList("已立项", "已到款", "已结题"));
        optionDatas.put("2", fieldValues2);

        Map<String, List<String>> fieldValues3 = new HashMap<>();
        fieldValues3.put("prizeType", Arrays.asList("国家科学技术奖", "教育部人文社会科学研究优秀成果奖",
                "省部级科学技术奖", "省部级哲学社会科学优秀成果奖", "国科奖办公室批科学技术奖", "厅级科学技术奖", "厅级人文社会科学成果奖"));
        fieldValues3.put("prizeLevel", Arrays.asList("特等奖", "一等奖", "二等奖", "三等奖"));
        optionDatas.put("3", fieldValues3);

        Map<String, List<String>> fieldValues4 = new HashMap<>();
        fieldValues4.put("area", Arrays.asList("国内", "国外"));
        fieldValues4.put("type", Arrays.asList("专著", "译著", "编著", "科普读物", "工具书"));
        fieldValues4.put("publishLevel", Arrays.asList("一类出版社", "二类出版社", "其它出版社"));
        optionDatas.put("4", fieldValues4);

        Map<String, List<String>> fieldValues5 = new HashMap<>();
        fieldValues5.put("type", Arrays.asList("发明专利", "实用新型专利", "外观设计专利"));
        optionDatas.put("5", fieldValues5);

        Map<String, List<String>> fieldValues6 = new HashMap<>();
        fieldValues6.put("ownWay", Arrays.asList("原始取得"));
        fieldValues6.put("rightRange", Arrays.asList("全部权利"));
        optionDatas.put("6", fieldValues6);

        Map<String, List<String>> fieldValues9 = new HashMap<>();
        fieldValues9.put("level", Arrays.asList("特种期刊", "权威一类", "权威二类", "权威三类",
                "核心一类", "核心二类", "其余国内公开期刊", "增刊/专刊"));
        fieldValues9.put("publishRange", Arrays.asList("国内", "国际"));
        optionDatas.put("9", fieldValues9);*/
    }
}