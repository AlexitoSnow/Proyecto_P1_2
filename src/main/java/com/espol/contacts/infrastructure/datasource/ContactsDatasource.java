package com.espol.contacts.infrastructure.datasource;

import com.espol.contacts.config.constants.Constants;
import com.espol.contacts.domain.datasource.BaseDatasource;
import com.espol.contacts.domain.entity.Contact;
import com.espol.contacts.domain.entity.Phone;

import java.util.List;
import java.util.Optional;

public class ContactsDatasource implements BaseDatasource<Contact> {

    // TODO: Llamar a deserializeFile()
    @Override
    public List<Contact> getAll() {
        return List.of();
    }

    @Override
    public Optional<Contact> getById(Long id) {
        final List<Contact> contacts = deserializeFile();
        return contacts.stream().filter(contact -> contact.getId().equals(id)).findFirst();
    }

    @Override
    public Optional<Contact> getByPhone(String phone) {
        final List<Contact> contacts = deserializeFile();
        return contacts.stream().filter(c -> c.getPhones().contains(new Phone(null, phone))).findFirst();
    }

    // TODO: Serializar la lista actualizada con el nuevo contacto
    // TODO: Llamar a deserializeFile() para obtener la lista
    // TODO: Buscar el contacto en la lista
    // TODO: Validar que el número no exista en otro contacto (puedes usar getByPhone)
    // TODO: Si el número existe, lanzar la excepción personalizada DuplicatedContactException
    // TODO: Si el contacto existe, actualizar la referencia (todo puede cambiar menos el Id)
    // TODO: Si el contacto no existe, darle un nuevo id (generarlo como el siguiente número en la lista) y luego agregarlo a la lista
    // TODO: Retornar el mismo item recibido, esto sirve para actualizar la UI como validación de "contacto creado"
    // Como ven, la lógica para actualizar y agregar sigue el mismo flujo
    // hasta la búsqueda, por eso se considera un solo method para ambos casos
    @Override
    public Contact save(Contact contact) {
        return null;
    }

    // TODO: Llamar a deserializeFile() para obtener la lista
    // TODO: Remover el elemento
    // TODO: De ser necesario, validar con try-catch en caso de que el parámetro recibido sea null
    @Override
    public void delete(Contact contact) {
    }

    // TODO: Verificar la existencia de la carpeta
    // TODO: Crear la carpeta del directorio de contactos en caso de no existir
    // TODO: Retornar la referencia a la carpeta
    // TODO: Corregir el tipo de dato de retorno
    private String getFolder() {
        // Recordar que en esa ruta se almacenan los directorios de contacto
        final String directories = Constants.DIRECTORY_FOLDER;
        return directories;
    }

    // TODO: Implementar la deserialización del archivo, pueden usar un nombre Hardcodeado como "dummy-contacts.contacts"
    // TODO: Llamar a getFolder para obtener la referencia del directorio
    // TODO: Corregir variable file que obtiene el archivo
    private List<Contact> deserializeFile() {
        final String file = getFolder() + "dummy-contacts.contacts";
        return null;
    }
}
