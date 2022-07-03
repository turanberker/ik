package com.mbt.yapikredi.ik.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    public static final String EMPLOYEE_TAG = "Employee Service";
    public static final String REQUEST_TAG = "Request Service";
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.mbt.yapikredi.ik.controller"))
                .paths(PathSelectors.regex("/.*"))
                .build().apiInfo(apiEndPointsInfo())
                .tags(
                        new Tag(EMPLOYEE_TAG,"Çalışanlara dair işlemlerin yapıldığı servistir"),
                        new Tag(REQUEST_TAG,"İzin taleplerine dair   işlemlerin yapıldığı servistir")
                );

    }
    private ApiInfo apiEndPointsInfo() {
        return new ApiInfoBuilder().title("Yapı Kredi Test Case")
                .description("Yapıkredi HR Application")
                .contact(new Contact("Berker Turan", "", "turanberker@yahoo.com"))
                .build();
    }
}
