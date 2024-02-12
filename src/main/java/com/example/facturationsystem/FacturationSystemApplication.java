package com.example.facturationsystem;

import com.example.facturationsystem.services.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class FacturationSystemApplication implements CommandLineRunner {

    @Autowired
    private UploadFileService uploadFileService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(FacturationSystemApplication.class, args);
    }

    /**
     * Método que se ejecuta al iniciar la aplicación.
     * Realiza la inicialización del servicio de carga de archivos y genera
     * contraseñas encriptadas de ejemplo.
     *
     * @param args los argumentos de línea de comandos
     * @throws Exception si ocurre un error durante la inicialización
     */
    @Override
    public void run(String... args) throws Exception {
        uploadFileService.deleteAll(); // Elimina todos los archivos existentes
        uploadFileService.init(); // Inicializa el servicio de carga de archivos

        String password = "12345";

        // Genera y muestra contraseñas encriptadas de ejemplo
        for (int i = 0; i < 2; i++) {
            String bCryptPassword = bCryptPasswordEncoder.encode(password);
            System.out.println("Contraseña: " + bCryptPassword);
        }
    }
}
