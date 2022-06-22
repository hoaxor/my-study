package com.hyh.ssm.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : huang.yaohua
 * @date : 2022/6/22 17:23
 */
public class CookieServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 给浏览器发Cookie
        // 方式一：resp.addCookie, 默认会话时间有效
        Cookie cookie = new Cookie("username", "hyh");
        // 设置cookie有效期，单位秒
        // 正数，表示多少秒后过期
        // 负数，表示永久
        // 0，立即失效
        cookie.setMaxAge(5);
        resp.addCookie(cookie);
        // 方式二: 设置响应头，方式一和方式二不能同时使用，多个cookie用分号分隔
//        resp.setHeader("Set-Cookie", "msg=hello;username=hyh");
        resp.getWriter().write("add cookies succeed");
    }
}
