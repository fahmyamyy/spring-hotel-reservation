//package com.amyganz.authservice.configs;
//
////import com.amyganz.authservice.services.impl.JpaReceptionistDetailService;
//import com.amyganz.authservice.services.impl.JpaUserDetailService;
//import com.nimbusds.jose.jwk.JWK;
//import com.nimbusds.jose.jwk.JWKSet;
//import com.nimbusds.jose.jwk.RSAKey;
//import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
//import com.nimbusds.jose.jwk.source.JWKSource;
//import com.nimbusds.jose.proc.SecurityContext;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.ProviderManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.jwt.JwtDecoder;
//import org.springframework.security.oauth2.jwt.JwtEncoder;
//import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
//import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
//import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
//import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.stereotype.Component;
//
//
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//@Component("ReceptionistConfig")
//public class RecepSecurityConfig {
//
//    private final RSAKeyProperties rsaKeyProperties;
//    private final JpaUserDetailService jpaUserDetailService;
////    private final JpaReceptionistDetailService jpaReceptionistDetailService;
//
////    @Bean
////    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
////        return http
////                .csrf(csrf -> csrf.disable())
////                .authorizeRequests(auth -> {
////                     auth.antMatchers("/api/v1/auth/login").permitAll();
////                     auth.antMatchers("/api/v1/auth/register").permitAll();
////                     auth.antMatchers("/api/v1/auth/receptionist/login").permitAll();
////                     auth.antMatchers("/api/v1/auth/receptionist/register").permitAll();
////                    auth.anyRequest().authenticated();
////                })
////                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
////                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
////                .exceptionHandling(ex -> ex
////                        .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint(
////
////                        ))
////                        .accessDeniedHandler(new BearerTokenAccessDeniedHandler()))
////                .build();
////    }
////
////    @Bean(name = "UserAuth")
////    public AuthenticationManager authenticationManagerUser(@Qualifier("UserDetails") UserDetailsService userDetailsService) {
////
////        var authProvider = new DaoAuthenticationProvider();
////        authProvider.setUserDetailsService(userDetailsService);
////        authProvider.setPasswordEncoder(passwordEncoder());
////
////        return new ProviderManager(authProvider);
////    }
//
//
//    @Bean(name = "RecepAuth")
//    public AuthenticationManager authenticationManagerRecep(@Qualifier("RecepDetails") UserDetailsService userDetailsService) {
//
//        var authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService);
//        authProvider.setPasswordEncoder(new BCryptPasswordEncoder());
//
//        return new ProviderManager(authProvider);
//    }
////
////
////    @Bean
////    public JwtDecoder jwtDecoder() {
////        return NimbusJwtDecoder.withPublicKey(rsaKeyProperties.getPublicKey()).build();
////    }
////
////    @Bean
////    public JwtEncoder jwtEncoder() {
////        JWK jwk = new RSAKey.Builder(rsaKeyProperties.getPublicKey()).privateKey(rsaKeyProperties.getPrivateKey()).build();
////        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
////        return new NimbusJwtEncoder(jwks);
////    }
//}
