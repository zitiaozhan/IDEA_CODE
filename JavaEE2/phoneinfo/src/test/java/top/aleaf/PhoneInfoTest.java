/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: PhoneInfoTest
 * Author:   郭新晔
 * Date:     2018/12/19 0019 10:12
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import top.aleaf.dao.SummaryDAO;
import top.aleaf.model.Summary;

import java.util.UUID;

/**
 * 〈〉
 *
 * @create 2018/12/19 0019
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PhoneinfoApplication.class)
@WebAppConfiguration
public class PhoneInfoTest {
    @Autowired
    private SummaryDAO summaryDAO;

    @Test
    public void contextLoads() {
        for (int i = 1; i < 11; i++) {
            Summary summary = new Summary();
            summary.setName("Phone" + i);
            summary.setPrice(3399);
            summary.setCpuType("骁龙855");
            summary.setMemory("8+128");
            summary.setDetailId(0);
            summary.setHeadImg("http://localhost:8080/" + UUID.randomUUID().toString().replaceAll("-", "") + ".jpg");
            summary.setStatus(0);
            summary.setBrand("HuaWei");
            this.summaryDAO.addPhone(summary);
        }
    }
}