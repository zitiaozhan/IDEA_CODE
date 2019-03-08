/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: OneController
 * Author:   郭新晔
 * Date:     2018/11/16 0016 15:02
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.toutiao.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import top.aleaf.toutiao.service.TouTiaoService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 〈〉
 *
 * @create 2018/11/16 0016
 */
@Controller
public class OneController {
    //日志  切面处理
    private static final Logger logger = LoggerFactory.getLogger(OneController.class);
    //Autowired  自动装配
    @Autowired
    private TouTiaoService tiaoService;

    /*
    @RequestMapping(path = {"/", "/index"})
    @ResponseBody
    public String getIndex() {
        logger.info("Method: getIndex");
        return "Hello everyone";
    }
    */

    /**
     * @param articleId URL链接的参数:/articleId
     * @param userId    URL链接的参数:/userId
     * @param type      URL中请求参数:?type=
     * @param page      URL中请求参数:?page=
     * @return
     */
    @RequestMapping(path = {"/article/{articleId}/{userId}"})
    @ResponseBody
    public String getArticles(@PathVariable(value = "articleId") int articleId,
                              @PathVariable(value = "userId") int userId,
                              @RequestParam(name = "type", defaultValue = "2") int type,
                              @RequestParam(name = "page", defaultValue = "1") int page) {
        logger.info("Method: getArticles");
        return String.format("{articleId = %d},{userId = %d},{type = %d},{page = %d}",
                articleId, userId, type, page);
    }

    /**
     * 使用Velocity模版引擎指定响应页面
     *
     * @param model
     * @return
     */
    @RequestMapping(path = {"/vm"})
    public String news(Model model) {
        model.addAttribute("value1", "vv1");
        return "news";
    }

    @RequestMapping(path = {"/setting"})
    @ResponseBody
    public String settings() {
        return "/settings";
    }

    /**
     * request常用
     *
     * @param request
     * @return
     */
    @RequestMapping(path = {"/request"})
    @ResponseBody
    public String testRequest(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        Enumeration<String> headerEnum = request.getHeaderNames();
        while (headerEnum.hasMoreElements()) {
            String name = headerEnum.nextElement();
            sb.append(name + " : " + request.getHeader(name) + "<br/>");
        }
        sb.append("<br/>");
        for (Cookie cookie : request.getCookies()) {
            sb.append("Cookie");
            sb.append(cookie.getName() + " : " + cookie.getValue());
            sb.append("<br/>");
        }

        return sb.toString();
    }

    /**
     * response常用
     *
     * @param id
     * @param key
     * @param value
     * @param response
     * @return
     */
    @RequestMapping(path = {"/response"})
    @ResponseBody
    public String testResponse(@CookieValue(value = "id", defaultValue = "21") int id,
                               @RequestParam(value = "key", defaultValue = "key") String key,
                               @RequestParam(value = "value", defaultValue = "value") String value,
                               HttpServletResponse response) {
        response.addCookie(new Cookie(key, value));
        //response.addHeader(key, value);
        return "id = " + id;
    }

    /**
     * 重定向1
     *
     * @param code
     * @return
     */
    @RequestMapping(path = {"/redirect/{code}"})
    @ResponseBody
    public RedirectView testRedirect(@PathVariable(value = "code") int code) {
        RedirectView rv = new RedirectView("/", true);
        if (code == 302) {
            rv.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        }
        return rv;
    }

    /**
     * 重定向2
     *
     * @return
     */
    @RequestMapping(path = {"/redirect"})
    @ResponseBody
    public String testRedirect() {
        return "redirect:/";
    }

    /**
     * 抛出错误
     *
     * @param key
     * @return
     */
    @RequestMapping(path = {"/admin"})
    @ResponseBody
    public String testError(@RequestParam(value = "key", required = false) String key) {
        if ("admin".equals(key)) {
            return "Hello Admin!";
        }
        throw new IllegalArgumentException("Key Error");
    }

    /**
     * 自定义处理错误
     *
     * @param e
     * @return
     */
    @ExceptionHandler
    @ResponseBody
    public String handleError(Exception e) {
        return "遇到问题：" + e.getMessage();
    }

    /**
     * IOC自动装配Bean
     *
     * @return
     */
    @RequestMapping(path = "/ioc")
    @ResponseBody
    public String testIOC() {
        return "IOC: " + this.tiaoService.welcome();
    }

    @RequestMapping(path = "/testVelocity")
    public String testVelocity(Model model) {
        List<Integer> nums = Arrays.asList(new Integer[]{0, 1, 4, 9, 16, 25, 36, 49, 64, 81});
        Map<String, String> fruitMap = new HashMap<>();
        fruitMap.put("香蕉", "黄色");
        fruitMap.put("西瓜", "绿色");
        fruitMap.put("火龙果", "红色");
        model.addAttribute("nums", nums);
        model.addAttribute("fruitMap", fruitMap);
        return "test";
    }
}