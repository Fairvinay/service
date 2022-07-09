package com.internal.configuration;

import java.util.LinkedList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.external.dataservice,com.internal")
@PropertySource( {"classpath:file.properties" } )
public class WebConfig extends WebMvcConfigurerAdapter {

	 @Bean
	    public ViewResolver getViewResolver() {
	        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
	        resolver.setViewClass(JstlView.class);
	        resolver.setExposeContextBeansAsAttributes(true);
	        resolver.setExposePathVariables(true);
	        resolver.setPrefix("/WEB-INF/views/");
	        resolver.setSuffix(".jsp");
	        return resolver;
	    }
	
	  // Static Resource Config
	   @Override
	   public void addResourceHandlers(ResourceHandlerRegistry registry) {
	   
		   // Css resource.
		   registry.addResourceHandler("/styles/**") //
					 .addResourceLocations("/WEB-INF/resources/css/").setCachePeriod(31556926);
			// JS resource.
		   registry.addResourceHandler("/js/**") //
					 .addResourceLocations("/WEB-INF/resources/js/").setCachePeriod(31556926);
		   
	   }
	
	 @Bean
	 public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
	     PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
	     List<Resource> resources = new LinkedList<Resource>();
	     resources.add(new ClassPathResource("file.properties"));
	     // resources.add(new ClassPathResource("config2.properties"));
	     configurer.setLocations(resources.toArray(new Resource[0]));
	     configurer.setIgnoreUnresolvablePlaceholders(true);
	     return configurer;

	 }
}
