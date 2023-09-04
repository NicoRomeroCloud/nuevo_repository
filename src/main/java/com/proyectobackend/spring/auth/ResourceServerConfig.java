package com.proyectobackend.spring.auth;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
@EnableResourceServer
public class ResourceServerConfig  extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/cliente/clientes", "/api/products/**", "/api/productos/**", "/api/product-category", "api/cliente/uploads/**", "/images/**", "/api/productos/**", "/api/productos/listar/ordenes", "/api/user/**", "/api/productos/uploads/{nombreFoto:.+}").permitAll()
                .antMatchers(HttpMethod.POST, "/api/user/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/cliente/clientes/upload").permitAll()
                .antMatchers(HttpMethod.POST, "/api/role/rol").permitAll()
                .antMatchers(HttpMethod.POST, "/api/user/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/user/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/user/**").permitAll()

                .antMatchers(HttpMethod.GET, "/api/orders/search/findByCustomerEmail").permitAll()



                .antMatchers(HttpMethod.POST, "/api/user/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/cliente/uploads/*").permitAll()
                .antMatchers(HttpMethod.POST, "/api/productos/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/productos/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/productos/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/checkout").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/api/checkout").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/api/cliente/clientes/upload").hasAnyRole("USER", "ADMIN")/*
                .antMatchers(HttpMethod.POST, "api/cliente/clientes/upload", "api").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.POST, "api/cliente/clientes").hasRole("ADMIN")
                .antMatchers("api/cliente/clientes/**").hasRole("ADMIN")*/
                .anyRequest().authenticated()
                .and().cors().configurationSource(corsConfigurationSource());


    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(Arrays.asList("*"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowCredentials(true);
        config.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter(){

        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(corsConfigurationSource()));

        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);

        return bean;
    }



}
