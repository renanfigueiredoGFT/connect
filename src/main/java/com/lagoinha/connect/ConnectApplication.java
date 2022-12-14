package com.lagoinha.connect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.annotations.Api;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@SpringBootApplication
public class ConnectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConnectApplication.class, args);
	}
	
	@Bean
	public Docket postsApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(new ApiInfoBuilder()
			            .title("Envio de Certificado Start")
			            .description("Software de envio de certificado da Lagoinha Niterói")
			            .version("1.0")
			            .license("MIT")
			            .licenseUrl("https://opensource.org/licenses/MIT")
			            .build())
			        .tags(new Tag("Certificado", "Envio dos Certificados do Start"))
			        .select().apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
			        .build();

	}

}
