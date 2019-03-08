/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: TouTiaoService
 * Author:   郭新晔
 * Date:     2018/11/17 0017 22:02
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.toutiao.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 〈〉
 * @create 2018/11/17 0017
 */
@Service
public class TouTiaoService {
    private static final Logger logger = LoggerFactory.getLogger(TouTiaoService.class);
    public String hello(){
        return "hello man!";
    }
    public String welcome(){
        return "Welcome to TouTiao!";
    }
}