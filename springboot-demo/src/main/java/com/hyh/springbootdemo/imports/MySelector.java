package com.hyh.springbootdemo.imports;

import com.hyh.springbootdemo.model.Cup;
import com.hyh.springbootdemo.model.ToiletPaper;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author : huang.yaohua
 * @date : 2022/5/28 23:17
 */
public class MySelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{Cup.class.getName(), ToiletPaper.class.getName()};
    }
}
