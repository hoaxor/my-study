package com.hyh.springbootdemo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * @author : huang.yaohua
 * @date : 2022/5/23 15:06
 */
@Service
@Slf4j(topic = "myService")
public class MyService {
    public void print(String msg) {
        log.debug(msg);
        System.out.println(msg);
    }

    public String get(String key) throws IOException {
        Properties properties = new Properties();
        //#早期版本的Java规定.properties文件编码是ASCII编码（ISO8859-1），
        // # 如果涉及到中文就必须用name=\u4e2d\u6587来表示，非常别扭。
        // # 从JDK9开始，Java的.properties文件可以使用UTF-8编码了。
//“ / ”代表了工程的根目录，例如工程名叫做myproject，“ / ”代表了myproject
//        me.class.getResourceAsStream(“/com/x/file/myfile.xml”);
        // 代表当前类的目录
        //me.class.getResourceAsStream(“myfile.xml”);
        properties.load(MyService.class.getResourceAsStream("/my.properties"));
        return properties.getProperty(key);
    }
    
}
