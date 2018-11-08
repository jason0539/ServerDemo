package com.daoming.web.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebMvcConfigurerAdapter implements WebMvcConfigurer {

    /**
     * 配置静态访问资源
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/my/**").addResourceLocations("classpath:/my/");
    }

    /**
     * 以前要访问一个页面需要先创建个Controller控制类，再写方法跳转到页面，比如LearnResourceController中配置好的访问http:localhost:8080/learn/react页面
     * 在这里配置后就不需要那么麻烦了，直接访问http://localhost:8080/toReact就跳转到indexPageByReact.html页面了，不过这里映射过去的没有带数据而已
     *
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/toReact").setViewName("indexPageByReact.html");
    }
}
