package org.emartos.mediaconverter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@SuppressWarnings("uncommentedmain")
@SpringBootApplication
@EnableSwagger2
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Docket swaggerConfiguration() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                //Restriction based on the URL of the API
                .paths(PathSelectors.ant("/api/v1/*/*"))
                //Restriction based on the pkg name
                //We exclude spring related configuration, like error-controller
                .apis(RequestHandlerSelectors.basePackage("org.emartos.mediaconverter.rest.v1"))
                .build()
                .apiInfo(apiDetails());
    }

    private ApiInfo apiDetails() {
        return new ApiInfo(
                "Media Converter API",
                "Media Converter API project",
                "master-SNAPSHOT",
                "Free to use",
                new springfox.documentation.service.Contact("Enric Martos", "https://github.com/enricmartos", "enric.martos@gmail.com"),
                "API License",
                "https://github.com/enricmartos",
                Collections.emptyList());
    }

}
