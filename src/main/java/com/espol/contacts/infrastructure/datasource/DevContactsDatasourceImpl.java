package com.espol.contacts.infrastructure.datasource;

import com.espol.contacts.domain.datasource.ContactsDatasource;
import com.espol.contacts.domain.entity.*;
import com.espol.contacts.domain.entity.enums.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Logger;

public class DevContactsDatasourceImpl implements ContactsDatasource {
    private final List<Contact> contacts = new ArrayList<>();
    private final Logger LOGGER = Logger.getLogger(DevContactsDatasourceImpl.class.getName());
    private static final DevContactsDatasourceImpl INSTANCE = new DevContactsDatasourceImpl();

    private DevContactsDatasourceImpl() {}

    public static DevContactsDatasourceImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Contact> getAll() {
        if (contacts.isEmpty()) {
            LOGGER.info("Creando lista falsa");
            for (int i = 0; i < 5; i++) {
                final int type = new Random().nextInt(2);
                contacts.add(type == 0 ? Person
                        .builder()
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
                        .id((long) i)
                        .build():
                        Company.builder()
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
        LOGGER.info("Tamaño antes: " + contacts.size());
        if (contacts.contains(contact)) {
            contacts.remove(contact);
        }
        contact.setId(contacts.size() + 1L);
        contacts.add(contact);
        LOGGER.info("Guardando contacto ID: " + contact.getId());
        LOGGER.info("Tamaño después: " + contacts.size());
        return contact;
    }

    @Override
    public void delete(Contact contact) {

    }
}
