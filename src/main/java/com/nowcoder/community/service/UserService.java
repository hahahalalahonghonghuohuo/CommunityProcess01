package com.nowcoder.community.service;

import com.nowcoder.community.dao.LoginTicketMapper;
import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.LoginTicket;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.util.CommunityConstant;
import com.nowcoder.community.util.CommunityUtil;
import com.nowcoder.community.util.MailClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @Author: yyp
 * @Date: 2024/3/13 - 03 - 13 - 17:48
 * @Description: com.nowcoder.community.service
 * @version: 1.0
 */

@Service
public class UserService implements CommunityConstant {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @Value("${community.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    public User findUserById(int id) {
        return userMapper.selectById(id);
    }

    /** 注册的方法 */
    public Map<String, Object> register(User user) {
        Map<String, Object> map = new HashMap();

        // 空值判断处理
        if (user == null) {
            throw new IllegalArgumentException("参数不能为空!");
        }
        if (StringUtils.isBlank(user.getUsername())) {
            map.put("usernameMsg", "账号不能为空!");
            return map;
        }
        if (StringUtils.isBlank(user.getPassword())) {
            map.put("passwordMsg", "密码不能为空!");
            return map;
        }
        if (StringUtils.isBlank(user.getEmail())) {
            map.put("emailMsg", "邮箱不能为空!");
            return map;
        }

        // 验证账号
        User u = userMapper.selectByName(user.getUsername());
        if (u != null) {
            map.put("usernameMsg", "该账号已存在!");
            return map;
        }

        // 验证邮箱
        u = userMapper.selectByEmail(user.getEmail());
        if (u != null) {
            // 得换一个邮箱注册
            map.put("emailMsg", "该邮箱已被注册");
            return map;
        }

        // 注册用户
        user.setSalt(CommunityUtil.generateUUID().substring(0, 5));
        user.setPassword(CommunityUtil.md5(user.getPassword() + user.getSalt()));
        user.setType(0);
        user.setStatus(0);
        user.setActivationCode(CommunityUtil.generateUUID());

        // 给新用户分配一个随机头像
        user.setHeaderUrl(String.format("http://images.nowcoder.com/head/%dt.png",
                new Random().nextInt(1000)));
        user.setCreateTime(new Date());
        userMapper.insertUser(user);

        // 激活邮件
        Context context = new Context();
        context.setVariable("email", user.getEmail());
        // 激活路径
        // http://localhost:8080/community/activation/101/code
        String url = domain + contextPath + "/activation/" + user.getId() +
                "/" + user.getActivationCode();
        context.setVariable("url", url);
        String content = templateEngine.process("/mail/activation", context);
        // 如果是固定的业务, 用固定的标题即可
        mailClient.sendMail(user.getEmail(), "激活账号", content);

        return map;
    }

    /** 激活的方法 */
    public int activation(int userId, String code) {
        User user = userMapper.selectById(userId);
        if (user.getStatus() == 1) {
            return ACTIVATION_REPEAT;
        } else if (user.getActivationCode().equals(code)) {
            // 更新用户的状态为已激活
            userMapper.updateStatus(userId, 1);
            return ACTIVATION_SUCCESS;
        } else {
            return ACTIVATION_FAILURE;
        }
    }

    /** 实现登录功能 */
    public Map<String, Object> login(String username, String password, int expiredSeconds) {
        Map<String, Object> map = new HashMap<>();

        // 对空值进行判断处理(包括账号和密码)
        if (StringUtils.isBlank(username)) {
            map.put("usernameMsg", "账号不能为空!");
            return map;
        }
        if (StringUtils.isBlank(username)) {
            map.put("passwordMsg", "密码不能为空!");
            return map;
        }

        // 对账号和密码的合法性进行验证
        // 1. 验证账号是否存在
        User user = userMapper.selectByName(username);
        if (user == null) {
            map.put("usernameMsg", "该账号不存在!");
            return map;

        }

        // 2. 验证状态(该账号是否激活)
        if (user.getStatus() == 0) {
            map.put("usernameMsg", "该账号未激活!");
            return map;

        }

        // 3. 验证密码
        password = CommunityUtil.md5(password + user.getSalt());
        if (!user.getPassword().equals(password)) {
            map.put("passwordMsg", "密码不正确!");
            return map;
        }

        // 登录成功, 生成登录凭证
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(user.getId());
        loginTicket.setTicket(CommunityUtil.generateUUID());
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date(System.currentTimeMillis() + expiredSeconds * 1000));
        loginTicketMapper.insertLoginTicket(loginTicket);

        // 我们在项目中没有用 Session, 而是在表中存储用户登录信息
        map.put("ticket", loginTicket.getTicket());

        return map;
    }

    /** 实现退出功能 */
    public void logout(String ticket) {
        // status 为 1 表示无效
        loginTicketMapper.updateStatus(ticket, 1);
    }

    /** 根据 ticket 凭证返回 LoginTicket */
    public LoginTicket findLoginTicket(String ticket) {
        return loginTicketMapper.selectByTicket(ticket);
    }

}
