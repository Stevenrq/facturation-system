package com.example.facturationsystem.config;

import com.example.facturationsystem.auth.handler.LoginSuccessHandler;
import com.example.facturationsystem.services.JpaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Esta clase es una configuración de Spring para la seguridad de la aplicación.
 * Habilita la seguridad de método mediante la anotación
 * {@link org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity}.
 */
@EnableMethodSecurity(securedEnabled = true)
@Configuration
public class SpringSecurityConfig {

    @Autowired
    private LoginSuccessHandler successHandler;

    @Autowired
    private JpaUserDetailsService userDetailsService;

    /**
     * Método que devuelve un bean de codificador de contraseñas BCrypt.
     *
     * @return un objeto {@link BCryptPasswordEncoder}
     */
    @Bean
    public static BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configura el {@link AuthenticationManagerBuilder} para utilizar el servicio
     * de detalles de usuario JPA y el codificador de contraseñas BCrypt.
     *
     * @param build el objeto {@link AuthenticationManagerBuilder} a configurar
     * @throws Exception si ocurre un error durante la configuración
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder build) throws Exception {
        build.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    /**
     * Configura la cadena de filtros de seguridad HTTP.
     *
     * @param http el objeto {@link HttpSecurity} a configurar
     * @return la cadena de filtros de seguridad configurada
     * @throws Exception si ocurre un error durante la configuración
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers("/", "/index", "/home", "/css/**", "/js/**", "/images/**", "/list").permitAll()
                        // .requestMatchers("/see/**").hasAnyRole("USER")
                        // .requestMatchers("/uploads/**").hasAnyRole("USER")
                        // .requestMatchers("/form/**").hasAnyRole("ADMIN")
                        // .requestMatchers("/delete/**").hasAnyRole("ADMIN")
                        // .requestMatchers("/invoice/**").hasAnyRole("ADMIN")
                        .anyRequest().authenticated())
                .formLogin(formLogin -> formLogin.loginPage("/login").permitAll()
                        .successHandler(successHandler))
                .logout(LogoutConfigurer::permitAll) // (logout -> logout.permitAll())
                .exceptionHandling(exceptionHandling -> exceptionHandling.accessDeniedPage("/error-403"));
        return http.build();
    }
}
