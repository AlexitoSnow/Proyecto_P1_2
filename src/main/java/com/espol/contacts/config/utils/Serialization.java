package com.espol.contacts.config.utils;

import java.io.*;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Serialization {
    private final static Logger LOGGER = Logger.getLogger(Serialization.class.getName());

    private Serialization() {}

    private static File getFile(String path) {
        final File file = new File(path);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Error creating file: " + e.getMessage(), e);
            }
        }
        return file;
    }

    public static <T> void serializeFile(T content, String path){
        File file = getFile(path);

        LOGGER.info("Serializando Contenido en " + file.getAbsolutePath());

        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file.getCanonicalPath()))){
            oos.writeObject(content);
        }catch(IOException e) {
            LOGGER.severe("Error al serializar el contenido: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static <T> Optional<T> deserializeFile(String path) {
        File file = new File(path);
        if (!file.exists() || file.length() == 0) {
            LOGGER.info("El archivo no existe o está vacío: " + file.getAbsolutePath());
            return Optional.empty();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            LOGGER.info("Deserializando desde " + file.getCanonicalPath());
            return Optional.ofNullable((T) ois.readObject());
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.warning("No se pudo deserializar el archivo: " + e.getMessage());
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
