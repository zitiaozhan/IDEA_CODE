package top.aleaf.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import top.aleaf.mapper.StringsMapper;
import top.aleaf.model.Strings;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈〉
 *
 * @author 郭新晔
 * @create 2019/2/15 0015
 */
@Component
public class ConstantUtil implements InitializingBean {
    public static final int PROJECT_STATUS_SUBMIT = 0;
    public static final int PROJECT_STATUS_SUCCESS = 1;
    public static final int PROJECT_STATUS_REFUSE = 2;
    //用户被注销时
    public static final int PROJECT_STATUS_USER_INVALID = 3;
    public static final int PROJECT_STATUS_DELETE = 4;
    public static final String MAIL_VALIDATE_SPLIT = "#MAIL#";
    public static final String DATE_SPLIT = "X";
    /**
     * 登录验证
     */
    public static final String TOKEN = "ticket";
    public static final String COMMA = ",";
    /**
     * 公告
     */
    public static final int MESSAGE_BULLETIN = 31;
    /**
     * 系统通知
     */
    public static final int MESSAGE_SYSTEM = 32;
    /**
     * 正式用户id开始
     */
    public static final int MESSAGE_USER = 50;
    /**
     * 批量注册账户初始密码
     */
    public static final String USER_PWD = "123456";
    /**
     * 小数位数
     */
    public static final String DOUBLE_PATTERN = "0.00";

    public Map<String, Map<String, List<String>>> optionDatas = new HashMap<>();

    @Resource
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

    }
}