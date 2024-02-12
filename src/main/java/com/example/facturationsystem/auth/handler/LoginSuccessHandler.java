package com.example.facturationsystem.auth.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import java.io.IOException;

@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    /**
     * Método que se ejecuta cuando un usuario ha iniciado sesión correctamente.
     * Guarda un mensaje de éxito en la sesión y registra información sobre el
     * inicio de sesión en el registro.
     *
     * @param request        la solicitud HTTP
     * @param response       la respuesta HTTP
     * @param authentication la información de autenticación del usuario que inició
     *                       sesión
     * @throws IOException      si ocurre un error de entrada/salida
     * @throws ServletException si ocurre un error durante el manejo del servlet
     */
    @SuppressWarnings("null")
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        SessionFlashMapManager flashMapManager = new SessionFlashMapManager();
        FlashMap flashMap = new FlashMap();
        flashMap.put("success", "Bienvenido " + authentication.getName() + ", has iniciado sesión con éxito");
        flashMapManager.saveOutputFlashMap(flashMap, request, response);

        logger.info("El usuario '" + authentication.getName() + "' ha iniciado sesión con éxito");

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
