package org.xian.camel.dataformat.transfer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author kaixiang.sun
 * @Date 2024/6/7 13:38
 */
public class DataTransformer {
    Logger logger = LoggerFactory.getLogger(DataTransformer.class);

    public String transform(String input) {
        // 执行转换逻辑
        return input.toUpperCase(); // 示例：将输入转换为大写
    }

    //同名方法
    //bean(DataTransformer.class, "transform(${header.param1}, ${header.param2})")
    public String transform(String input, String output) {
        // 执行转换逻辑
        return input.toUpperCase() +" **** " +output.toUpperCase(); // 示例：将输入转换为大写
    }
}
