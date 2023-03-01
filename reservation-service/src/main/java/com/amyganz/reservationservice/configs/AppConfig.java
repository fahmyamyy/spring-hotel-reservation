package com.amyganz.reservationservice.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {
    @Bean
    public ModelMapper modelMapper() {
        return  new ModelMapper();
    }
    @Bean
    public ObjectMapper objectMapper() { return new ObjectMapper(); }
    @Bean
    public RestTemplate restTemplate() {return new RestTemplate();}
    @Bean
    public WebClient client() {return WebClient.create(); }
    @Value("${port.hotel}")
    private String PORT_HOTEL;
    @Value("${port.user}")
    public String PORT_USER;
    @Bean
    public String PortHotel() {
        return PORT_HOTEL;
    };
//    @Bean
//    public String PortUser() {
//        return PORT_USER;
//    };

}
