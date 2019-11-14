package com.automationtest.instance;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
@EnableWebMvc
public class StaticResourceConfiguration extends WebMvcConfigurationSupport {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // When overriding default behavior, you need to add default(/) as well as added static paths(/webapp).

        registry
            .addResourceHandler("/public/**") //
            .addResourceLocations("classpath:/public/") // Default Static Loaction
            .setCachePeriod( 3600 )
            .resourceChain(true); //4.1

        registry
            .addResourceHandler("/scripts/**") // 
            .addResourceLocations("classpath:/scripts/");

        registry
            .addResourceHandler("/results/**") // 
            .addResourceLocations("classpath:/results/");

        // Do not use the src/main/webapp/... directory if your application is packaged as a jar.
        // registry
        //     .addResourceHandler("/webapp/**") // « /webapp/css/style.css
        //     .addResourceLocations("/");
    }
}