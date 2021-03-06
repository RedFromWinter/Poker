package com.nogo.poker.configuration;

import com.nogo.poker.web.PaginationResolver;
import com.nogo.poker.web.RequestContextResolver;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.List;

@EnableWebMvc
@ComponentScan(basePackages = {"com.nogo"})
@Configuration
public class DispatcherConfig extends WebMvcConfigurerAdapter {
  private static final String MESSAGE_SOURCE = "/resources/messages";

  @Override
  public void addResourceHandlers(final ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/css/**")
        .addResourceLocations("/resources/css/")
        .setCachePeriod(31556926);
    registry.addResourceHandler("/js/**")
        .addResourceLocations("/resources/js/")
        .setCachePeriod(31556926);
    registry.addResourceHandler("/htm/**")
        .addResourceLocations("/resources/htm/")
        .setCachePeriod(31556926);
  }

  @Override
  public void configureDefaultServletHandling(final DefaultServletHandlerConfigurer configurer) {
    configurer.enable();
  }

  /**
   * Configuraiton for the InternalResourceViewResolver.
   *
   * @return resolver
   */
  @Bean
  public InternalResourceViewResolver getInternalResourceViewResolver() {
    final InternalResourceViewResolver resolver = new InternalResourceViewResolver();
    resolver.setPrefix("/WEB-INF/");
    resolver.setSuffix(".html");
    return resolver;
  }

  /**
   * Configuration for the MessageSource.
   *
   * @return messageSource
   */
  @Bean(name = "messageSource")
  public MessageSource messageSource() {
    final ReloadableResourceBundleMessageSource messageSource =
        new ReloadableResourceBundleMessageSource();
    messageSource.setBasename(MESSAGE_SOURCE);
    messageSource.setCacheSeconds(5);
    return messageSource;
  }

  @Override
  public Validator getValidator() {
    final LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
    validator.setValidationMessageSource(messageSource());
    return validator;
  }

  @Override
  public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> argumentResolvers) {
    final RequestContextResolver requestContextResolver = new RequestContextResolver();
    final PaginationResolver paginationResolver = new PaginationResolver();
    argumentResolvers.add(requestContextResolver);
    argumentResolvers.add(paginationResolver);
  }

}
