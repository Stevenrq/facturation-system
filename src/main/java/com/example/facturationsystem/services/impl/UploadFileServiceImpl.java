package com.example.facturationsystem.services.impl;

import com.example.facturationsystem.services.UploadFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UploadFileServiceImpl implements UploadFileService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final static String UPLOADS_FOLDER = "uploads";

    @Override
    @SuppressWarnings("null")
    public Resource load(String filename) throws MalformedURLException {
        Path pathPhoto = getAbsolutePath(filename);

        logger.info("pathPhoto" + pathPhoto);

        Resource resource;
        resource = new UrlResource(pathPhoto.toUri());
        if (!resource.exists() || !resource.isReadable()) {
            throw new RuntimeException("Error: Can't load photo: " + pathPhoto);
        }
        return resource;
    }

    @Override
    public String copy(MultipartFile photo) throws IOException {
        String uniqueFilename = UUID.randomUUID() + "_" + photo.getOriginalFilename();
        Path rootPath = getAbsolutePath(uniqueFilename);

        logger.info("rootPath: " + rootPath);

        Files.copy(photo.getInputStream(), rootPath);
        return uniqueFilename;
    }

    @Override
    public boolean delete(String filename) {
        if (filename == null || filename.isEmpty()) {
            return false;
        }
        Path rootPath = getAbsolutePath(filename);
        File file = rootPath.toFile();
        if (file.exists() && file.canRead()) {
            return file.delete();
        }
        return false;
    }

    /**
     * Método que devuelve la ruta absoluta de un archivo en la carpeta de subida.
     *
     * @param filename el nombre del archivo para el que se desea obtener la ruta
     *                 absoluta
     * @return la ruta absoluta del archivo
     */
    public Path getAbsolutePath(String filename) {
        return Paths.get(UPLOADS_FOLDER).resolve(filename).toAbsolutePath();
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(Paths.get(UPLOADS_FOLDER).toFile());
    }

    @Override
    public void init() throws IOException {
        Files.createDirectory(Paths.get(UPLOADS_FOLDER));
    }
}
