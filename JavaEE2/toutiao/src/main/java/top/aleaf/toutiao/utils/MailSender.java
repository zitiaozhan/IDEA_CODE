package top.aleaf.toutiao.utils;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.util.Map;
import java.util.Properties;

/**
 *
 */
@Service
public class MailSender implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(MailSender.class);
    private JavaMailSenderImpl mailSender;

    @Autowired
    private VelocityEngine velocityEngine;

    /**
     * 使用Velocity模版发送邮件
     *
     * @param to
     * @param subject
     * @param template
     * @param model
     * @return
     */
    public boolean sendWithHTMLTemplate(String to, String subject,
                                        String template, Map<String, Object> model) {
        try {
            //所使用昵称
            String nick = MimeUtility.encodeText("无涯");
            //发件人
            InternetAddress from = new InternetAddress(nick + "<zitiaozhan@163.com>");
            //开始构造邮件
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            //渲染Velocity模版引擎获得结果
            String result = VelocityEngineUtils
                    .mergeTemplateIntoString(velocityEngine, template, "UTF-8", model);
            //收件人邮箱
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setFrom(from);
            //配置子标题
            mimeMessageHelper.setSubject(subject);
            //配置邮件内容
            mimeMessageHelper.setText(result, true);
            //邮件发送
            mailSender.send(mimeMessage);
            logger.info("发送邮件成功");
            return true;
        } catch (Exception e) {
            logger.error("发送邮件失败：" + e.getMessage());
            return false;
        }
    }

    /**
     * mailSender初始化
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        mailSender = new JavaMailSenderImpl();

        // 请输入自己的邮箱和密码，用于发送邮件
        mailSender.setUsername("zitiaozhan@163.com");
        mailSender.setPassword("45777888guoxinye");
        //发件服务器主机
        mailSender.setHost("smtp.163.com");
        // 请配置自己的邮箱和密码
        mailSender.setPort(465);
        mailSender.setProtocol("smtps");
        mailSender.setDefaultEncoding("utf8");
        //配置发件相关属性
        Properties javaMailProperties = new Properties();
        //发件属性，是否支持ssl协议
        javaMailProperties.put("mail.smtp.ssl.enable", true);
        mailSender.setJavaMailProperties(javaMailProperties);
    }
}
