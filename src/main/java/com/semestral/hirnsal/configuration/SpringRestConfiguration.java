package com.semestral.hirnsal.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by jakub on 01.06.2016.
 */
@EnableWebMvc
@Configuration
@ComponentScan(basePackages = {"com.semestral.hirnsal.rest"})
public class SpringRestConfiguration extends WebMvcConfigurerAdapter{

}
