web文件上传



```html
<--enctype 在使用包含文件上传控件的表单时，必须使用该值。-->
<form action="${ctp}/upload" enctype="multipart/form-data" method="post">
    username: <input type="text" name="username"/>
    file: <input type="file" name="file"/>
</form>
```

```java
package com.hyh.ssm.servlet;

import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : huang.yaohua
 * @date : 2022/6/22 11:54
 */
public class FileServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        ServletInputStream inputStream = request.getInputStream();
        System.out.println(IOUtils.toString(inputStream, "UTF-8"));
        resp.getWriter().write("ok");
    }
}

```



![image-20220622115958778](\picture\image-20220622115958778.png)

```te
------WebKitFormBoundaryJqQGABGAQuk3NbEn --分隔符
Content-Disposition: form-data; name="username" --表单项1的内容
--空格
0027019952 --值
------WebKitFormBoundaryJqQGABGAQuk3NbEn
Content-Disposition: form-data; name="file"; filename="�ļ��ϴ��ܿ�ƽ̨.txt"
Content-Type: text/plain
--空行
--接文件内容
1���ϴ��ļ���19.47��������
cd /home/wls/uploadfiles/

2����47������ͨ��sftp��¼��Ȼ���ļ������Զ�sftp��������
      
sftp iscdtdf@10.159.176.40
���룺
Ua!Ysc20@

����kdzhĿ¼��
cd kdzh

�ϴ��ļ���
put 6�·���װ��һַ����˺�-����ƥ����.xlsx
put �����-ȥ��0514.xlsx

telnet 10.229.19.34 23
wls
Qwer1@#$

curl -H "Content-type: application/json" -X POST -d '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://service.sas.isearch.com">   <soapenv:Header/>   <soapenv:Body>      <ser:sendInfo4File>         <!--Optional:-->         <ser:RequestInfo><![CDATA[{"code": "202205010938040002","4a_account": "OEcuibocheng","app_account": "OEcuibocheng","file_name": "�����-ȥ��0514.xlsx","file_path":  "/data/webdav/iscdtdf/kdzh/","sensitivity_level":"1","file_type ":"��ϸ","timestamp": "2022-05-01 10:25:51", "service_id": "hn_kdzh","token": "4c979cc136b341308a11b1a8527c67b6","md5_data": "00b6579b17590559017e397e30026c85"}        ]]>         </ser:RequestInfo>      </ser:sendInfo4File>   </soapenv:Body></soapenv:Envelope>' http://10.154.52.155:9080/webDataService/api


#!/bin/bash

msgId="$(date +%Y%m%d%H%M%S)0001"
current=$(date "+%Y-%m-%d %H:%M:%S")
echo $1
data="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://service.sas.isearch.com\">   <soapenv:Header/>   <soapenv:Body>      <ser:sendInfo4File> <ser:RequestInfo><![CDATA[{"code": \"$msgId\",\"4a_account\": \"zhangwei6\",\"app_account\": \"zhangwei6\",\"file_name\": \"$1\",\"file_path\":  \"/data/webdav/iscdtdf/kdzh/\",\"sensitivity_level\":\"1\",\"file_type \":\"��ϸ\",\"timestamp\": \"$current\", \"service_id\": \"hn_kdzh\",\"token\": \"4c979cc136b341308a11b1a8527c67b6\",\"md5_data\": \"00b6579b17590559017e397e30026c85\"}        ]]>         </ser:RequestInfo>      </ser:sendInfo4File>   </soapenv:Body></soapenv:Envelope>"
echo $data

curl -H "Content-type: application/json" -X POST -d "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://service.sas.isearch.com\">   <soapenv:Header/>   <soapenv:Body>      <ser:sendInfo4File> <ser:RequestInfo><![CDATA[{"code": \"$msgId\",\"4a_account\": \"zhangwei6\",\"app_account\": \"zhangwei6\",\"file_name\": \"$1\",\"file_path\":  \"/data/webdav/iscdtdf/kdzh/\",\"sensitivity_level\":\"1\",\"file_type \":\"��ϸ\",\"timestamp\": \"$current\", \"service_id\": \"hn_kdzh\",\"token\": \"4c979cc136b341308a11b1a8527c67b6\",\"md5_data\": \"00b6579b17590559017e397e30026c85\"}        ]]>         </ser:RequestInfo>      </ser:sendInfo4File>   </soapenv:Body></soapenv:Envelope>" http://10.154.52.155:9080/webDataService/api
------WebKitFormBoundaryJqQGABGAQuk3NbEn--


```



