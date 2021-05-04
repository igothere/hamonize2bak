package com;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@SpringBootApplication
public class HamonizeApplication {

//운영 반영시 주석 해제
//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//		return builder.sources(DeHomeApplication.class);
//	}

	public static void main(String[] args) {
		SpringApplication.run(HamonizeApplication.class, args);
	}

	@Bean 
	public MessageSource messageSource(){ 
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource(); 
		messageSource.setBasename("classpath:/message/message"); 
		messageSource.setDefaultEncoding("UTF-8"); 
		messageSource.setCacheSeconds(180);
		Locale.setDefault(Locale.getDefault()); 
		
		return messageSource;
		}

}
