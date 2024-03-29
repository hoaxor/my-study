package com.hyh.springmvcdemo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyh.springmvcdemo.model.User;
import com.hyh.springmvcdemo.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;

/**
 * dispatcherServlet拦截所有请求
 *
 * @author : huang.yaohua
 * @date : 2022/6/6 17:51
 */
@Controller
public class MyController {

    @Autowired
    private MyService myService;

    /**
     * 告诉springmvc ,这个方法可以用来处理什么请求
     * "/"可以省略
     */
    @RequestMapping("/hello")
    public String hello(Map<String, Object> map) {
        // 往请求域中写入数据
        map.put("msg", "你好");
        return "hello";
    }

    @RequestMapping("/hello2")
    @ResponseBody
    public String hello2() {
        JSONObject object = new JSONObject();
        object.put("user", "hyh");
        object.put("ag", "12");
        return object.toString();
    }

    /**
     * user 简单java对象未标注ModelAttribute默认会从隐含模型中获取，name为类名首字符小写
     * 否则会使用ModelAttribute指定的name,从ModelMap中获取
     * 使用Valid 开启校验
     * 入参中增加 BindingResult 用来接收参数校验结果
     * 被校验参数与
     */
    @RequestMapping("/hello3")
    @ResponseBody
    private User hello3(@Valid User user, String msg, BindingResult bindingResult, ModelMap map) {
//        User user = new User();
//        user.setAge(age);
//        user.setName(name);
        System.out.println("hello3");
        System.out.println(map.getAttribute("s"));
        System.out.println(msg);
        System.out.println(bindingResult);
        return user;
    }

    @RequestMapping("/hello4")
    @ResponseBody
    private User hello4(@RequestParam("user") User user) {
        System.out.println(user);
        return user;
    }

    @RequestMapping("/hello5")
    @ResponseBody
    private String hello5() {
        return myService.toString();
    }

    @ModelAttribute
    private void modelAttribute(ModelMap map) {
        map.put("s", "1");
        System.out.println("modelAttribute");
    }

    //    @ModelAttribute
    private User modelAttribute2(ModelMap map) {
        User user = new User();
        user.setName("hyh2");
        return user;
    }

    public String conver(String[] messages, Map content) throws Exception {
        String result = messages == null || messages.length == 0 ? "" : messages[0];
        if (result != null && result.length() > 0) {
            JSONObject parse = JSON.parseObject(result);
            parse.putIfAbsent("data", new JSONArray());
        }
        return result;
    }

}
