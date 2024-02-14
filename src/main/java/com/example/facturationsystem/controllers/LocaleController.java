package com.example.facturationsystem.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LocaleController {

    /**
     * Redirige a la última URL visitada.
     *
     * @param request el objeto HttpServletRequest que contiene la información de la solicitud
     * @return una cadena representando la URL a la que redirigir
     */
    @GetMapping("/locale")
    public String locale(HttpServletRequest request) {
        String lastUrl = request.getHeader("referer");
        return "redirect:".concat(lastUrl);
    }
}
