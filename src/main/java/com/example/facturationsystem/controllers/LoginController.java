package com.example.facturationsystem.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

/**
 * Controlador para manejar las solicitudes relacionadas con el inicio de
 * sesión.
 */
@Controller
public class LoginController {

    /**
     * Método que maneja las solicitudes GET a "/login" para mostrar el formulario
     * de inicio de sesión.
     *
     * @param error      un parámetro opcional para indicar un error de inicio de
     *                   sesión
     * @param logout     un parámetro opcional para indicar que el usuario ha
     *                   cerrado sesión
     * @param model      el objeto {@link Model} para agregar atributos para la
     *                   vista
     * @param principal  el objeto {@link Principal} que representa la identidad del
     *                   usuario actual
     * @param attributes el objeto {@link RedirectAttributes} para agregar atributos
     *                   para la redirección
     * @return la vista "login" para mostrar el formulario de inicio de sesión
     */
    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout, Model model, Principal principal,
            RedirectAttributes attributes) {

        if (principal != null) {
            attributes.addFlashAttribute("info", "Ya has iniciado sesión anteriormente");
            return "redirect:/";
        }
        if (error != null) {
            model.addAttribute("error", "Nombre de usuario o contraseña incorrecta");
        }
        if (logout != null) {
            model.addAttribute("success", "Has cerrado sesión con éxito");
        }
        return "login";
    }
}
