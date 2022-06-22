package com.hyh.ssm.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * @author : huang.yaohua
 * @date : 2022/6/22 11:41
 */
@Controller
public class FileController {

    @Autowired
    private ServletContext servletContext;

    @RequestMapping("/uploadMVC")
    @ResponseBody
    public String upload(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ServletInputStream inputStream = request.getInputStream();
        ;
        System.out.println(IOUtils.toString(inputStream, "UTF-8"));
        return "ok";
    }


    @RequestMapping("/download")
    @ResponseBody
    public String download(@RequestParam("file") String file, HttpServletRequest request,
                           HttpServletResponse response) throws IOException {
        // 获取文件在服务器上的真实路径
        String realPath = servletContext.getRealPath("/files/redis阿里巴巴.pdf");
        System.out.println(realPath);

        if ("pdf".equals(file)) {
            try (FileInputStream fileInputStream =
                         new FileInputStream(realPath);
                 OutputStream outputStream = response.getOutputStream()) {

                // 设置响应头
                // 内容处理方式, 和文件名
                // 文件名乱码问题解决
                response.setHeader("Content-Disposition", "attachment;filename="
                        + URLEncoder.encode("redis阿里巴巴.pdf", "UTF-8"));

                IOUtils.copy(fileInputStream, outputStream);
            }
        }
        return "ok";
    }

}
