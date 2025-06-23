package com.espol.contacts.config.constants;

import com.espol.contacts.config.utils.comparator.*;
import com.espol.contacts.domain.entity.Contact;

import java.io.File;
import java.util.Comparator;
import java.util.Map;

public class Constants {
    private Constants() {}

    public static final String APP_NAME = "ContactLi: Libreta de Contactos";

    public static final String DIRECTORY_FOLDER = "app" + File.separator + "directory";

    public static final String ACCOUNTS_FOLDER = "app" + File.separator + "accounts";

    public static final String GALLERY_FOLDER = "app" + File.separator + "gallery";

    public static final Map<String, Comparator<Contact>> COMPARATORS = Map.of(
            "Nombre", new NameComparator(),
            "Apellido", new LastNameComparator(),
            "Tipo de Contacto", new ContactTypeComparator(),
            "Números agregados", new PhoneCountComparator(),
            "Correos agregados", new EmailCountComparator(),
            "Redes sociales agregadas", new SocialMediaComparator()
    );

    public static final String searchInfoText = "El campo de búsqueda de contactos tiene ciertos criterios de filtrado " +
            "que pueden ser de mucha ayuda." +
            "\n*Criterios de Filtrado:*\n\n" +
            "nombre, \tapellido, \ttipo, \temail, \tcelular, \tsocial, \tdir, \tnotas, \tfecha, \tindustria\n\n" +
            "*Importante:*\n\n*-* El texto que no se relacione a algún criterio se interpretará como filtro de nombre o apellido.\n" +
            "*-* Si el texto tiene más de una palabra para el filtro, debe ir entre comillas (\"\").\n" +
            "*-* Para iniciar la búsqueda, presione enter o dé clic en el botón buscar (ícono de lupa).\n" +
            "*-* Para limpiar la búsqueda, simplemente borra el texto del campo de búsqueda y realice una búsqueda con el campo de texto vacío.\n" +
            "*-* Cada criterio de búsqueda se separa por un espacio.\n*-* Los criterios de búsqueda son opcionales.\n" +
            "*-* Las consultas se anidan, por lo que, si algún criterio no se encuentra, no se mostrará ningún contacto asociado.\n" +
            "Ejemplo de Búsqueda:\n\n" +
            "*nombre:Alex apellido:nieves tipo:PERSONA email:globant.com celular:23 social:alexito dir:ecu notas:\"Estudiante Politécnico\" fecha:01/01/2025 der*\n\n" +
            "*Interpretación:* Contactos que contengan el nombre Alex, apellido Nieves, tipo de contacto Persona, algún correo de globant.com, algún celular con el número 23, " +
            "alguna red social que contenga el username \"alexito\", con \"ecu\" en su dirección, con una nota que contenga \"Estudiante Politécnico\", con alguna fecha del 1ro de enero de 2025 " +
            "y con el nombre que contenga \"der\"\n";

    public static final String galleryInfoText = "La galería de contactos es una herramienta que permite visualizar " +
            "las fotos de un contactos de forma circular, es decir, presionando las flechas de atrás o adelante, podrá navegar entre las fotos infinitamente.\n\n" +
            "La galería en tamaño grande se puede abrir al hacer clic en cualquier foto de la vista previa de la galería del contacto.";

    public static final String navigationInfoText = "La navegación de contactos se puede realizar mediante dos formas:\n\n" +
            "*1. Barra Lateral:* Se encuentra en la parte izquierda de la pantalla, puede seleccionar cualquier contacto de allí, modificando criterios de ordenamiento o cambiando entre las distintas vistas disponibles.\n\n" +
            "*2. Barra inferior:* Se encuentra invisible, pero se activa en cuanto selecciona algún contacto, le mostrará el índice del contacto en el que se encuentra, y las flechas le permitirá avanzar y retroceder " +
            "entre los contactos de forma indefinida, es decir, cuando llegue al final de la lista, volverá al principio automáticamente, y viceversa.";

    public static final String relatedContactsInfoText = "No hay mucho que decir de los contactos relacionados " +
            "excepto que, al seleccionar un contacto de la pestaña de contactos relacionados, podrá navegar hacia la información de dicho contacto fácilmente.";
}
