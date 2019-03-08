package top.aleaf.toutiao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import top.aleaf.toutiao.model.User;
import top.aleaf.toutiao.utils.JedisAdapter;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ToutiaoApplication.class)
@WebAppConfiguration
public class JedisTests {
    @Autowired
    private JedisAdapter jedisAdapter;

    @Test
    public void contextLoads() {
        User user = new User();
        user.setPassword("*****");
        user.setHeadUrl("http://image.nowcoder.com?id=10");
        user.setSalt("hello-thank-you");
        user.setName("Mr.Lei");
        user.setId(110);
        jedisAdapter.setObject("userTest", user);

        System.out.println(jedisAdapter.get("userTest"));
    }

}

