package com.hyh.springbootdemo.imports;

import com.hyh.springbootdemo.model.Bomb;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

/**
 * @author : huang.yaohua
 * @date : 2022/5/28 23:04
 */
@Component
@Import({Bomb.class, MySelector.class})
public class ImportTest {
}
