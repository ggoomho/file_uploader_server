package com.chols.fu.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.chols.fu.monitor.ProgressMonitor;

@Configuration
public class BaseConfiguration {
    @Bean
    public ProgressMonitor progressMonitor() {
        return new ProgressMonitor();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
