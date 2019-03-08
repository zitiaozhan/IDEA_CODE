package top.aleaf.toutiao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import top.aleaf.toutiao.dao.CommentDAO;
import top.aleaf.toutiao.dao.LoginTicketDAO;
import top.aleaf.toutiao.dao.NewsDAO;
import top.aleaf.toutiao.dao.UserDAO;
import top.aleaf.toutiao.model.Comment;
import top.aleaf.toutiao.model.EntityType;

import java.util.Date;
import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ToutiaoApplication.class)
@WebAppConfiguration
public class UserDAOTests {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private NewsDAO newsDAO;
    @Autowired
    private LoginTicketDAO ticketDAO;
    @Autowired
    private CommentDAO commentDAO;

    @Test
    public void contextLoads() {
        Random random = new Random();
        for (int i = 11; i < 13; i++) {
            /*User user = new User();
            user.setName(String.format("USER%d", i));
            user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
            user.setPassword("");
            user.setSalt("");
            userDAO.addUser(user);
            */

            if (i == 1) {
                continue;
            }

            /*User user = userDAO.selectById(i + 1);
            News news = new News();
            news.setTitle(String.format("Title{%d}", (i + 1)));
            news.setLink(String.format("http://www.nowcoder.com/%d.html", i));
            news.setImage(user.getHeadUrl());
            news.setLikeCount(i + 1);
            news.setCommentCount(i);
            news.setCreateDate(new Date());
            news.setUserId(user.getId());
            newsDAO.addNews(news);*/

            for (int j = 0; j < 3; j++) {
                Comment comment = new Comment();
                comment.setContent("真的不错 " + j);
                comment.setCreatedDate(new Date());
                comment.setEntityId(i + 1);
                comment.setEntityType(EntityType.NEWS);
                comment.setUserId(3);
                this.commentDAO.addComment(comment);
            }

            /*LoginTicket ticket = new LoginTicket();
            ticket.setUserId(i + 1);
            ticket.setTicket(String.format("TICKET%d", i + 1));
            ticket.setExpired(new Date());
            ticket.setStatus(1);*/
            //this.ticketDAO.updateLoginTicket(String.format("TICKET%d", i + 1), 2);
        }
    }

}

