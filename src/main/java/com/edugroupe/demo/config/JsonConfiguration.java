package com.edugroupe.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

import com.edugroupe.demo.metiers.projections.RecetteView;

@Configuration
public class JsonConfiguration implements RepositoryRestConfigurer {

	@Bean
	public SpelAwareProxyProjectionFactory ProjectionFactory() {
		return new SpelAwareProxyProjectionFactory();
	}
	
	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
		config.exposeIdsFor(RecetteView.class);
	}
}