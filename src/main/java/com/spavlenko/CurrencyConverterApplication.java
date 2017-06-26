package com.spavlenko;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.spavlenko.util.LoggingInterceptor;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author sergii.pavlenko
 * @since March 28, 2017
 */
@EnableCaching
@EnableSwagger2
@SpringBootApplication
public class CurrencyConverterApplication extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(CurrencyConverterApplication.class, args);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoggingInterceptor()).addPathPatterns("/**");
    }

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage(getClass().getPackage().getName())).paths(PathSelectors.any())
                .build();
    }

}
