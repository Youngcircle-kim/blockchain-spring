package com.uxm.blockchain.config;

import com.uxm.blockchain.common.converters.GenreRequestConverter;
import com.uxm.blockchain.common.converters.TypeRequestConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebConfig implements WebMvcConfigurer {
//  @Override
//  public void addFormatters(FormatterRegistry registry){
//    registry.addFormatter(new GenreRequestConverter());
//    registry.addFormatter(new TypeRequestConverter());
//  }
}
