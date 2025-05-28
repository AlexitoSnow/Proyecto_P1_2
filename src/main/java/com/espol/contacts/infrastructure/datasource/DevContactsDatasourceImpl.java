package com.espol.contacts.infrastructure.datasource;

import com.espol.contacts.domain.datasource.ContactsDatasource;
import com.espol.contacts.domain.entity.*;
import com.espol.contacts.domain.entity.enums.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class DevContactsDatasourceImpl implements ContactsDatasource {
    private final List<Contact> contacts = new ArrayList<>();

    @Override
    public List<Contact> getAll() {
        if (contacts.isEmpty()) {
            for (int i = 0; i < 30; i++) {
                final int type = new Random().nextInt(2);
                contacts.add(type == 0 ? Person
                        .build()
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
                        .id((long) i) :
                        Company.build()
                                .name("Company RANDOM FULL TEXT LOREM IPSUM" + (i + 1))
                        .addPhone(new Phone(PhoneType.MAIN, "0983434"))
                                .contactType(ContactType.Empresa)
                );
            }
        }
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
            contacts.remove(contact);
        }
        contacts.add(contact);
        return contact;
    }

    @Override
    public void delete(Contact contact) {

    }
}
