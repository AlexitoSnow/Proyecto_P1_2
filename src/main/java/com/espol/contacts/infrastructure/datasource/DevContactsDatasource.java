package com.espol.contacts.infrastructure.datasource;

import com.espol.contacts.domain.datasource.BaseDatasource;
import com.espol.contacts.domain.entity.*;
import com.espol.contacts.domain.entity.enums.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Logger;

public class DevContactsDatasource implements BaseDatasource<Contact> {
    private final List<Contact> contacts = new ArrayList<>();
    private final Logger LOGGER = Logger.getLogger(DevContactsDatasource.class.getName());
    private static DevContactsDatasource instance;

    private DevContactsDatasource() {}

    public static DevContactsDatasource getInstance() {
        if (instance == null) {
            instance = new DevContactsDatasource();
        }
        return instance;
    }

    @Override
    public List<Contact> getAll() {
        if (contacts.isEmpty()) {
            LOGGER.info("Creando lista falsa");
            for (int i = 0; i < 5; i++) {
                final int type = new Random().nextInt(2);
                contacts.add(type == 0 ? Person
                        .builder()
                        .id((long) i)
                        .middleName("Alexito")
                        .lastName("Snow")
                        .name("Person RANDOM FULL TEXT LOREM IPSUM" + (i + 1))
                        .addPhone(new Phone(PhoneType.PHONE, "0983434"))
                        .contactType(ContactType.Persona)
                        .addAddress(Address.build()
                                .city("Guayaquil").country("Ecuador").type(AddressType.OFICINA))
                        .addDate(new ImportantDate(LocalDate.now(), DateType.ANNIVERSARY))
                        .addEmail(new Email(EmailType.PRINCIPAL, "alexit@gmail.com"))
                        .addEmail(new Email(EmailType.TRABAJO, "alexit@gmail.com"))
                        .addSocialMedia(new SocialMedia("alexitosnow", SocialPlatform.INSTAGRAM))
                        .addSocialMedia(new SocialMedia("alexitosnow", SocialPlatform.TIKTOK))
                        .addSocialMedia(new SocialMedia("Alexander Nieves", SocialPlatform.LINKEDIN))
                        .notes("Este man es una persona chévere")
                        .build():
                        Company.builder()
                                .id((long) i)
                                .name("Company RANDOM FULL TEXT LOREM IPSUM" + (i + 1))
                        .addPhone(new Phone(PhoneType.MAIN, "0983434"))
                                .contactType(ContactType.Empresa).build()
                );
            }
        }
        LOGGER.info("Obteniendo lista: " + contacts.size() + " contactos.");
        return contacts;
    }

    @Override
    public Optional<Contact> getById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Contact> getByPhone(String phone) {
        return Optional.empty();
    }

    @Override
    public Contact save(Contact contact) {
        if (contacts.contains(contact)) {
            contacts.set(contacts.indexOf(contact), contact);
            LOGGER.info("Actualizando contacto ID: " + contact.getId());
            return contact;
        }
        contact.setId(contacts.size() + 1L);
        contacts.add(contact);
        LOGGER.info("Guardando contacto ID: " + contact.getId());
        return contact;
    }

    @Override
    public void delete(Contact contact) {
        contacts.remove(contact);
    }
}
