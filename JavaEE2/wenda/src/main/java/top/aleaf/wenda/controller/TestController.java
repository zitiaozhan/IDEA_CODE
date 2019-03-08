/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: TestController
 * Author:   郭新晔
 * Date:     2019/1/16 0016 17:46
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.wenda.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * 〈〉
 *
 * @create 2019/1/16 0016
 */
@Controller
public class TestController {
    @RequestMapping(path = {"/test"}, method = RequestMethod.GET)
    public String welcome(Model model) {
        Map<String, String> maps = new HashMap();
        maps.put("name", "Tom");
        maps.put("country", "CN.");
        maps.put("age", "22");
        model.addAttribute("maps", maps);
        return "hello";
    }
}