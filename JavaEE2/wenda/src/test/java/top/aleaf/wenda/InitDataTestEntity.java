package top.aleaf.wenda;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import top.aleaf.wenda.mapper.QuestionMapper;
import top.aleaf.wenda.mapper.UserMapper;
import top.aleaf.wenda.model.Question;
import top.aleaf.wenda.model.User;
import top.aleaf.wenda.util.GeneralExample;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
//@Transactional	//添加后事务不再提交
@SpringBootTest(classes = WendaApplication.class)
public class InitDataTestEntity {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;

    @Test
    public void testUser() {
        //数据全部删除
        /*userMapper.delete(new User());

		//插入数据
		for(int i=0;i < 10;i++){
			User user=new User();
			Random random=new Random();
			user.setHead_url(String.format("http://images.nowcoder.com/head/%dt.png",random.nextInt(1000)));
			user.setName(String.format("User-%d",i+1));
			user.setPassword("");
			user.setSalt("");
			userMapper.insert(user);
		}*/

        System.out.println(userMapper.selectByPrimaryKey(53));
        List<User> users = userMapper.selectByExample(GeneralExample.getBaseExample(User.class));
        System.out.println(Arrays.toString(users.toArray()));
        Assert.assertNotNull(userMapper.selectOne(new User().setName("User-2")));
    }

    @Test
    public void testQuestion() {
        //插入数据
        for (int i = 0; i < 10; i++) {
            Question question = new Question();
            Random random = new Random();
            question.setCommentCount(i + 1);
            Date date = new Date();
            date.setTime(date.getTime() - 1000 * 3600 * i);
            question.setCreatedDate(date);
            question.setUserId(52 + i);
            question.setTitle(String.format("Question-%d", i + 1));
            question.setContent(String.format("Hahahahahahah...Question-%d__Content-%d", i + 1, i + 1));
            questionMapper.insert(question);
        }
    }

}

