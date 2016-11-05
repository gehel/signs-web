package ch.ledcom.signs;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.IOException;

@SpringBootApplication
@EnableSwagger2
public class SignsApplication extends WebMvcConfigurerAdapter {

    @Bean
    public Jackson2RepositoryPopulatorFactoryBean repositoryPopulator() {
        Resource[] data;
        try {
            data = new PathMatchingResourcePatternResolver().getResources("classpath:ch/ledcom/signs/*.sign.json");
        } catch (IOException e) {
            data = new Resource[] { new ClassPathResource("ch/ledcom/signs/data.json") };
        }

        Jackson2RepositoryPopulatorFactoryBean factory = new Jackson2RepositoryPopulatorFactoryBean();
        factory.setResources(data);
        return factory;
    }

    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.TEXT_HTML)
                .favorParameter(true)
                .mediaType("atom", MediaType.APPLICATION_ATOM_XML);
    }

	public static void main(String[] args) {
		SpringApplication.run(SignsApplication.class, args);
	}
}
