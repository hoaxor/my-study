package com.hyh.springbootdemo.typefilter;

import com.hyh.springbootdemo.service.MyService;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

/**
 * @author : huang.yaohua
 * @date : 2022/5/29 20:10
 */
public class MyTypeFilter implements TypeFilter {
    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        Class<?> aClass = null;
        try {
            aClass = Class.forName(metadataReader.getClassMetadata().getClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        //判断当前类型是否MyService
        return MyService.class.isAssignableFrom(aClass);
    }
}
