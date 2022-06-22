package com.hyh.ssm.servlet;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.springframework.util.CollectionUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;

/**
 * @author : huang.yaohua
 * @date : 2022/6/22 11:54
 */
@Slf4j
public class FileServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
//        ServletInputStream inputStream = request.getInputStream();
//        System.out.println(IOUtils.toString(inputStream, "UTF-8"));
//        resp.getWriter().write("ok");
        
        // tomcat 默认将POST请求的数据按iso-8859-1对请求内容进行编码
        // 解决POST请求内容乱码
        request.setCharacterEncoding("UTF-8");
        // 是否文件上传请求        
        boolean multipartContent = ServletFileUpload.isMultipartContent(request);
        if (multipartContent) {
            // 磁盘文件项工厂
            DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
            // servletFileUpload
            ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);

            try {
                // 解析请求
                List<FileItem> fileItems = servletFileUpload.parseRequest(request);
                log.debug("fileItems.size()={}", fileItems.size());
                if (!CollectionUtils.isEmpty(fileItems)) {
                    for (FileItem next : fileItems) {
                        // 是表单字段，非文件
                        if (next.isFormField()) {
                            log.debug("field next={}", next);
                        } else {
                            log.debug("file next.getName={}", next.getName());
                            log.debug("file next.getSize={}", next.getSize());
                            try (FileOutputStream output = new FileOutputStream("E:\\myCode\\my-study\\Markdown\\tmp.file");
                                 InputStream inputStream = next.getInputStream()) {
                                IOUtils.copy(inputStream, output);
                            }
                        }
                    }
                }
            } catch (FileUploadException e) {
                e.printStackTrace();
            }

            resp.getWriter().write("ok....");

        }


    }
}
