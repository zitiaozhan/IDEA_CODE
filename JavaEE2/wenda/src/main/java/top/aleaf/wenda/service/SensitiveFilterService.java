/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: SensitiveFilterService
 * Author:   郭新晔
 * Date:     2019/1/19 0019 18:54
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.wenda.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.util.StringUtil;
import top.aleaf.wenda.controller.UserController;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 〈〉
 *
 * @create 2019/1/19 0019
 */
@Service
public class SensitiveFilterService implements InitializingBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    //默认敏感替换词汇
    private static final String DEFAULT_REPLACEMENT = "***";

    class TrieNode {
        //key:下一字符；value:对应节点
        private Map<Character, TrieNode> subNodes = new HashMap<>();
        //是否为敏感词汇结束字符
        private boolean isSensitiveEnd = false;

        public void addSubNode(Character key, TrieNode value) {
            this.subNodes.put(key, value);
        }

        public TrieNode getSubNode(Character key) {
            return this.subNodes.get(key);
        }

        public boolean isSensitiveEnd() {
            return isSensitiveEnd;
        }

        public void setSensitiveEnd(boolean sensitiveEnd) {
            isSensitiveEnd = sensitiveEnd;
        }
    }

    /**
     * 判断是否是一个符号
     */
    private boolean isSymbol(char c) {
        int ic = (int) c;
        // 0x2E80-0x9FFF 东亚文字范围
        return !StringUtil.isLowercaseAlpha(c) && !StringUtil.isUppercaseAlpha(c) && (ic < 0x2E80 || ic > 0x9FFF);
    }

    //创建根节点
    private TrieNode rootNode = new TrieNode();

    public void addSensitiveWord(String sensitiveWord) {
        TrieNode node = rootNode;
        for (int i = 0; i < sensitiveWord.length(); i++) {
            Character c = sensitiveWord.charAt(i);
            //过滤符号
            if (isSymbol(c)) {
                continue;
            }
            if (node.getSubNode(c) == null) {
                node.addSubNode(c, new TrieNode());
            }
            node = node.getSubNode(c);
            if (i == sensitiveWord.length() - 1) {
                node.setSensitiveEnd(true);
            }
        }
    }

    public String filterSensitiveWord(String srouce) {
        StringBuilder sb = new StringBuilder("");
        TrieNode node = rootNode;
        int begin = 0;
        int position = 0;

        while (position < srouce.length()) {
            Character c = srouce.charAt(position);
            if (isSymbol(c)) {
                if (node == rootNode) {
                    sb.append(c);
                    ++begin;
                }
                position++;
                continue;
            }

            node = node.getSubNode(c);
            if (node == null) {
                sb.append(srouce.charAt(begin));
                position = begin + 1;
                begin = position;
                node = rootNode;
            } else if (node != null && node.isSensitiveEnd()) {
                sb.append(DEFAULT_REPLACEMENT);
                position = position + 1;
                begin = position;
                node = rootNode;
            } else {
                position = position + 1;
            }
        }

        sb.append(srouce.substring(begin));

        return sb.toString();
    }

    /**
     * 初始化时加载敏感词汇文件，建立前缀树
     *
     * @throws Exception
     */
    @Override

    public void afterPropertiesSet() throws Exception {
        rootNode = new TrieNode();

        try {
            InputStream is = Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream("SensitiveWords.txt");
            InputStreamReader read = new InputStreamReader(is);
            BufferedReader bufferedReader = new BufferedReader(read);
            String srouce;
            while ((srouce = bufferedReader.readLine()) != null) {
                srouce = srouce.trim();
                addSensitiveWord(srouce);
            }
            read.close();
        } catch (Exception e) {
            LOGGER.error("读取敏感词文件失败" + e.getMessage());
        }
    }


    public static void main(String[] args) {
        /*SensitiveFilterService service = new SensitiveFilterService();
        service.addSensitiveWord("色情");
        service.addSensitiveWord("赌博");
        System.out.println(service.filterSensitiveWord("%你好$色^&情|@"));*/

        System.out.println(Arrays.toString("/||/index".split("\\|\\|")));
    }
}