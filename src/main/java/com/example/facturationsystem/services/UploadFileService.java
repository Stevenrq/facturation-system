package com.example.facturationsystem.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Interfaz que define operaciones para el servicio de carga de archivos.
 */
public interface UploadFileService {

    /**
     * Carga un recurso con el nombre de archivo especificado.
     *
     * @param filename el nombre del archivo a cargar
     * @return el recurso cargado
     * @throws MalformedURLException si ocurre un error al crear la URL para el
     *                               recurso
     */
    Resource load(String filename) throws MalformedURLException;

    /**
     * Copia un archivo multipart a la ubicación de almacenamiento.
     *
     * @param photo el archivo multipart a copiar
     * @return el nombre del archivo copiado
     * @throws IOException si ocurre un error durante la operación de copia
     */
    String copy(MultipartFile photo) throws IOException;

    /**
     * Elimina un archivo con el nombre de archivo especificado.
     *
     * @param filename el nombre del archivo a eliminar
     * @return true si el archivo fue eliminado con éxito; false si no se pudo
     *         eliminar o si no existe
     */
    boolean delete(String filename);

    /**
     * Elimina todos los archivos almacenados.
     */
    void deleteAll();

    /**
     * Inicializa el servicio de carga de archivos.
     *
     * @throws IOException si ocurre un error durante la inicialización
     */
    void init() throws IOException;
}
