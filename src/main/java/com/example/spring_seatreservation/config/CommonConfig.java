package com.example.spring_seatreservation.config;


import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

@Configuration
public class CommonConfig {

    /* 设置上传文件大小限制
     * @param:
     * @return: javax.servlet.MultipartConfigElement
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //单个文件最大-30MB
        factory.setMaxFileSize(DataSize.ofMegabytes(30));
        //该方法已降级
        //factory.setMaxRequestSize("30MB");
        /// 设置总上传数据总大小-120MB
        factory.setMaxRequestSize(DataSize.ofMegabytes(120));
        return factory.createMultipartConfig();
    }
}
