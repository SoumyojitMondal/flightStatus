package com.elasticsearch.flightstatus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title="Flight Status APIs",version="1.0",description="APIs to create and fetch flight statuses from Elasticsearch"))
@EnableWebSecurity
public class FlightstatusApplication {
	public static void main(String[] args) {
		SpringApplication.run(FlightstatusApplication.class, args);
	}

	@Bean
  	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
    	http.csrf().disable()
        .authorizeHttpRequests(requests -> requests
		.requestMatchers("/getStatus").permitAll()
		.anyRequest().authenticated())
        .httpBasic();
    	return http.build();
  }

}
 