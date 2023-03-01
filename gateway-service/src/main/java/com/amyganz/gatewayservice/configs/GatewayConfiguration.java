package com.amyganz.gatewayservice.configs;

import com.amyganz.gatewayservice.filters.AdminFilter;
import com.amyganz.gatewayservice.filters.UserFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GatewayConfiguration {
    private final UserFilter filter;
    private final AdminFilter adminFilter;

    @Value("${port.auth}")
    private String PORT_AUTH;
    @Value("${port.user}")
    private String PORT_USER;
    @Value("${port.hotel}")
    private String PORT_HOTEL;
    @Value("${port.reservation}")
    private String PORT_RESERVATION;

    @Bean
    RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/api/v1/hotel/**")
                        .and().method( "PUT", "DELETE")
                        .filters(f -> f.filter(filter).filter(adminFilter))
                        .uri(PORT_HOTEL))
                .route(r -> r.path("/api/v1/hotel/**")
                        .and().method("POST", "GET")
                        .filters(f -> f.filter(filter))
                        .uri(PORT_HOTEL))
                .route(r -> r.path("/api/v1/room/**")
                        .and().method("POST", "PUT", "DELETE")
                        .filters(f -> f.filter(filter).filter(adminFilter))
                        .uri(PORT_HOTEL))
                .route(r -> r.path("/api/v1/room/**")
                        .and().method("GET")
                        .filters(f -> f.filter(filter))
                        .uri(PORT_HOTEL))
                .route(r -> r.path("/api/v1/roomtype/**")
                        .and().method("POST", "PUT", "DELETE")
                        .filters(f -> f.filter(filter).filter(adminFilter))
                        .uri(PORT_HOTEL))
                .route(r -> r.path("/api/v1/roomtype/**")
                        .and().method("GET")
                        .filters(f -> f.filter(filter))
                        .uri(PORT_HOTEL))
                .route(r -> r.path("/api/v1/reservation/**")
                        .and().method("DELETE")
                        .filters(f -> f.filter(filter).filter(adminFilter))
                        .uri(PORT_RESERVATION))
                .route(r -> r.path("/api/v1/reservation/**")
                        .and().method("POST", "PUT", "GET")
                        .filters(f -> f.filter(filter))
                        .uri(PORT_RESERVATION))
//                .route(r -> r.path("/api/v1/user/userinfo/**")
//                        .and().method("GET", "PUT", "DELETE")
//                        .filters(f -> f.filter(filter).filter(adminFilter))
//                        .uri(PORT_USER))
                .route(r -> r.path("/api/v1/user/userinfo/**")
                        .and().method("POST")
                        .filters(f -> f.filter(filter))
                        .uri(PORT_USER))
                .route(r -> r.path("/api/v1/auth/**")
                        .uri(PORT_AUTH))
//
                .build();
    }
}
