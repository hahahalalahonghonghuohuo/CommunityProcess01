package com.nowcoder.community.util;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: yyp
 * @Date: 2024/4/11 - 04 - 11 - 20:38
 * @Description: com.nowcoder.community.util
 * @version: 1.0
 */

@Component
public class SensitiveFilter {

    private static final Logger logger = LoggerFactory.getLogger(SensitiveFilter.class);

    // 替换符
    private static final String REPLACEMENT = "***";


    // 1. 定义前缀树
    private class TrieNode {
        // 关键词结束的标识
        private boolean isKeywordEnd = false;

        // 当前节点的子节点(key 是下级节点的字符, value 是下级节点)
        private Map<Character, TrieNode> subNodes = new HashMap<>();



        public boolean isKeywordEnd() {
            return isKeywordEnd;
        }

        public void setKeywordEnd(boolean keywordEnd) {
            isKeywordEnd = keywordEnd;
        }

        // 添加子节点的方法
        public void addSubNode(Character c, TrieNode node) {
            subNodes.put(c, node);
        }

        // 获取子节点的方法
        public TrieNode getSubNode(Character c) {
            return subNodes.get(c);
        }

    }

    // 2. 根据敏感词, 初始化前缀树
    // 2.1 初始化根节点
    private TrieNode rootNode = new TrieNode();

    @PostConstruct
    public void init() {
        try (
                InputStream is = this.getClass().getClassLoader().getResourceAsStream("sensitive-words.txt");
                // 把字节流转成字符流, 把字符流转化成缓冲流
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        ) {
            String keyword;
            while ((keyword = reader.readLine()) != null) {
                // 每次读取到的词都存到变量 keyword 中
                // 因为配置文件中是一行一个敏感词, 每读一行, 只要不为空, 就赋值给 keyword
                // 将读到的敏感词添加到前缀树对象中去
                this.addKeyword(keyword);
            }
        } catch (IOException e) {
            logger.error("加载敏感词文件失败: " + e.getMessage());
        }


    }

    /** 将一个敏感词添加到前缀树中 */
    private void addKeyword(String keyword) {
        TrieNode tempNode = rootNode;
        for (int i = 0; i < keyword.length(); i++) {
            char c = keyword.charAt(i);
            TrieNode subNode = tempNode.getSubNode(c);

            if (subNode == null) {
                // 初始化子节点
                subNode = new TrieNode();
                // 将 subNode 挂到他的父亲节点之下, 即当前节点 tempNode
                tempNode.addSubNode(c, subNode);
            }

            // 让 tempNode 指向子节点, 进入下一轮循环
            tempNode = subNode;

            // 设置结束标识
            if (i == keyword.length() - 1) {
                tempNode.setKeywordEnd(true);
            }
        }
    }

    // 3. 编写过滤敏感词的方法(最关键的一步)

    /**
     * 过滤敏感词
     * @param text 待过滤的文本
     * @return 过滤后的文本
     */
    public String filter(String text) {
        if (StringUtils.isBlank(text)) {
            return null;
        }

        // 指针 1 指向 Trie 中的节点
        TrieNode tempNode = rootNode;
        // 指针 2 和 指针 3 指向字符串的首位(是两个索引, 所以是 int 类型)
        int begin = 0;
        int position = 0;
        // 记录结果
        StringBuilder sb = new StringBuilder();

        while (position < text.length()) {
            char c = text.charAt(position);

            // 跳过符号
            if (isSymbol(c)) {
                // 若指针 1 处于根节点, 将此符号计入结果, 让指针 2 向下走一步
                if (tempNode == rootNode) {
                    sb.append(c);
                    begin++;
                }
                // 指针 3 在 if 外面加, 因为不管指针 2 走不走, 指针 3 一定会走
                // 无论符号在开头或中间, 指针 3 都向下走一步
                position++;
                // 跳过符号后, 后续的业务不用执行, 直接 continue 进入下一轮循环
                continue;
            }

            // 迭代检查下级节点
            tempNode = tempNode.getSubNode(c);
            if (tempNode == null) {
                // 以 begin 开头的字符串不是敏感词
                sb.append(text.charAt(begin));
                // 进入下一个位置
                position = ++begin;
                // 将指针 1 重新指向根节点
                tempNode = rootNode;
            } else if (tempNode.isKeywordEnd()) {
                // 发现了敏感词, 将 begin 到 position 的字符串替换掉
                sb.append(REPLACEMENT);
                // 让 position 进入下一个位置, begin 跟上 position
                begin = ++position;
                // 将指针 1 重新指向根节点
                tempNode = rootNode;
            } else {
                // 继续检查下一个字符
                position++;
            }
        }

        // 将最后一批字符计入结果
        sb.append(text.substring(begin));

        return sb.toString();
    }

    /** 判断是否为符号 */
    private boolean isSymbol(Character c) {
        // CharUtils.isAsciiAlphanumeric(c) 普通字符返回 true, 特殊字符返回 false
        // 0x2E80 到 0x9FFF 是东亚文字范围, 不视为符号
        return !CharUtils.isAsciiAlphanumeric(c) && (c < 0x2E80 || c > 0x9FFF);
    }


}
