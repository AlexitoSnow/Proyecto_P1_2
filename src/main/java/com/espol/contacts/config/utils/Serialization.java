package com.espol.contacts.config.utils;

import com.espol.contacts.config.SessionManager;
import com.espol.contacts.config.constants.Constants;
import com.espol.contacts.config.utils.list.ArrayList;
import com.espol.contacts.config.utils.list.List;
import com.espol.contacts.domain.entity.Contact;
import com.espol.contacts.domain.entity.User;
import com.espol.contacts.infrastructure.datasource.ContactsDatasourceImpl;

import java.io.*;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;


public abstract class Serialization {
    private final static Logger LOGGER = Logger.getLogger(Serialization.class.getName());

    private static File getFile() {
        final User user = SessionManager.getInstance().getCurrentUser();
        final File file = new File(Constants.DIRECTORY_FOLDER, user.getUsername() + ".contacts");
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                String path;
                try {
                    path = file.getCanonicalPath();
                } catch (IOException ex) {
                    path = file.getAbsolutePath();
                }
                LOGGER.log(Level.SEVERE, "Error al crear el archivo: " + path, e);
                throw new RuntimeException(e);
            }
        }
        return file;
    }

    public static File getFile(String filename, boolean createIfNotExists) {
        final File file = new File( Constants.ACCOUNTS_FOLDER + File.separator + filename + ".user");
        if (createIfNotExists && !file.exists()) {
            try {
                if (file.getParentFile() != null && !file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();
            } catch (IOException e) {
                LOGGER.severe("Error creating user file: " + e.getMessage());
            }
        }
        return file;
    }



    public static Optional<T> serializeFile(Object content,String fileName,boolean condition){
        File file = getFile();
        if (condition) {
            file = getFile();
        } else {
            file = getFile(fileName, true);
        }
        try {
            LOGGER.info("Serializando Contenido en " + file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException(e);

        }
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file.getCanonicalPath()))){
            oos.writeObject(content);
        }catch(IOException e) {
            LOGGER.severe("Error al serializar el contenido: " + e.getMessage());
            e.printStackTrace();
        }


       return null;
    }

    public static Optional<User>  deSerializeFile(String fileName, boolean condition){
        File file;
        if (condition) {
            file = getFile();
        } else {
            file = getFile(fileName, true);
        }


        if (file.length() == 0) {
            LOGGER.info("El archivo está vacío o se creó recientemente: " + file.getAbsolutePath());
            return (T) new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            LOGGER.info("Deserializando desde " + file.getCanonicalPath());
            return (T)ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.warning("No se pudo deserializar el archivo" + e.getCause());
            e.printStackTrace();
            return null;
        }
    }





}
