package com.example.facturationsystem.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
}
