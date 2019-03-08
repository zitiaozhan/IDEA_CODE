/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: TwoController
 * Author:   郭新晔
 * Date:     2018/11/17 0017 22:20
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.toutiao.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 〈〉
 *
 * @create 2018/11/17 0017
 */
@Controller
public class TwoController {
    //日志 切面
    private static final Logger logger = LoggerFactory.getLogger(TwoController.class);
    @RequestMapping(path = "/two")
    @ResponseBody
    public String two() {
        return "This is two world!";
    }
}