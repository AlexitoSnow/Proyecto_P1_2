package com.espol.contacts.config.constants;

import com.espol.contacts.config.utils.comparator.*;
import com.espol.contacts.domain.entity.Contact;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Constants {
    private Constants() {}

    public static final String APP_NAME = "Contacts App";

    public static final String DIRECTORY_FOLDER = "app/directory/";

    public static final Map<String, Comparator<Contact>> COMPARATORS = Map.of(
            "Nombre", new NameComparator(),
            "Apellido", new LastNameComparator(),
            "Tipo de Contacto", new ContactTypeComparator(),
            "Números agregados", new PhoneCountComparator(),
            "Correos agregados", new EmailCountComparator(),
            "Redes sociales agregadas", new SocialMediaComparator()
    );
}
