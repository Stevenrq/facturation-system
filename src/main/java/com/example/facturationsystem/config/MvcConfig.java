package com.example.facturationsystem.config;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

/**
 * Esta clase es una configuración de Spring que implementa
 * {@link org.springframework.web.servlet.config.annotation.WebMvcConfigurer}
 * para configurar las vistas y controladores en la aplicación web MVC.
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    /**
     * Método para agregar una vista para el manejo de errores 403 (Acceso
     * Prohibido).
     * Asocia la URL "/error-403" con la vista "error-403".
     *
     * @param registry el registro de controladores de vista
     */
    @SuppressWarnings("null")
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/error-403").setViewName("error-403");
    }

    /**
     * Configura el LocaleResolver para manejar la configuración regional y el
     * idioma.
     *
     * @return el LocaleResolver configurado
     */
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.forLanguageTag("es-Es"));
        return localeResolver;
    }

    /**
     * Configura el LocaleChangeInterceptor para permitir cambios dinámicos de
     * idioma.
     *
     * @return el LocaleChangeInterceptor configurado
     */
    @Bean
    public LocaleChangeInterceptor changeInterceptor() {
        LocaleChangeInterceptor changeInterceptor = new LocaleChangeInterceptor();
        changeInterceptor.setParamName("lang");
        return changeInterceptor;
    }

    /**
     * Agrega el interceptor LocaleChangeInterceptor al registro de interceptores.
     *
     * @param registry el registro de interceptores
     */
    @SuppressWarnings("null")
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(changeInterceptor());
    }
}
