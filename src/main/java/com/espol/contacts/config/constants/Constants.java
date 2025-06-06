package com.espol.contacts.config.constants;

import com.espol.contacts.config.utils.comparator.*;
import com.espol.contacts.domain.entity.Contact;

import java.io.File;
import java.util.Comparator;
import java.util.Map;

public class Constants {
    private Constants() {}

    public static final String APP_NAME = "Contacts App";

    public static final String DIRECTORY_FOLDER = "app" + File.separator + "directory";

    public static final String ACCOUNTS_FOLDER = "app" + File.separator + "accounts";

    public static final Map<String, Comparator<Contact>> COMPARATORS = Map.of(
            "Nombre", new NameComparator(),
            "Apellido", new LastNameComparator(),
            "Tipo de Contacto", new ContactTypeComparator(),
            "Números agregados", new PhoneCountComparator(),
            "Correos agregados", new EmailCountComparator(),
            "Redes sociales agregadas", new SocialMediaComparator()
    );
}
