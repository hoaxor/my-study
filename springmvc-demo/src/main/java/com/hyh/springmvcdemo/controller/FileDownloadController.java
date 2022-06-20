package com.hyh.springmvcdemo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;

@Controller
public class FileDownloadController {

    @ResponseBody
    @RequestMapping("/download")
    public ResponseEntity<byte[]> download(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletContext servletContext = ((HttpServlet) request).getServletContext();
        String realPath = servletContext.getRealPath("/js/test.js");
        FileInputStream fileInputStream = new FileInputStream(realPath);
        byte[] bytes = new byte[fileInputStream.available()];
        fileInputStream.read(bytes);
        fileInputStream.close();

        return new ResponseEntity<>(bytes, HttpStatus.OK);
    }

    /**
     * MultipartFile 接收上传的文件
     */
    @ResponseBody
    @RequestMapping("/upload")
    public String upload(@RequestParam("file")MultipartFile file){
        System.out.println(file.getOriginalFilename());
        return "success";
    }

    /**
     * 多文件上传
     */
    @ResponseBody
    @RequestMapping("/uploadFiles")
    public String upload(@RequestParam("file")MultipartFile[] files){
        System.out.println(files.length);
        return "success";
    }
}
