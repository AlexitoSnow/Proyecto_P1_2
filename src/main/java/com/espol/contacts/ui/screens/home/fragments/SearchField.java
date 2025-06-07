package com.espol.contacts.ui.screens.home.fragments;

import com.espol.contacts.domain.entity.Contact;
import com.espol.contacts.domain.entity.Person;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeParseException;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchField extends HBox {

    @FXML
    private TextField textField;
    private Consumer<Predicate<Contact>> consumer;

    public SearchField() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SearchField.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException("No se pudo cargar el FXML para SearchFieldController", exception);
        }
    }

    @FXML
    private void search(ActionEvent event) {

        final String searchText = textField.getText();
        if (searchText == null || searchText.trim().isEmpty()) {
            consumer.accept(contact -> true); // No hay filtro, devuelve todos los contactos
            return;
        }
        Predicate<Contact> finalPredicate = contact -> true;

        Pattern filterPattern = Pattern.compile("(\\w+):(\".*?\"|\\S+)");
        Matcher matcher = filterPattern.matcher(searchText);

        String remainingSearchText = searchText;

        while (matcher.find()) {
            String keyword = matcher.group(1).toLowerCase();
            String value = matcher.group(2);

            if (value.startsWith("\"") && value.endsWith("\"")) {
                value = value.substring(1, value.length() - 1);
            }
            final String finalValue = value.toLowerCase();

            Predicate<Contact> specificPredicate = null;
            switch (keyword) {
                case "nombre":
                    specificPredicate = contact -> contact.getName().toLowerCase().contains(finalValue);
                    break;
                case "apellido":
                    // Validar si es una instancia de Person antes de acceder a getLastName()
                    specificPredicate = contact -> contact instanceof Person &&
                            ((Person) contact).getLastName() != null &&
                            ((Person) contact).getLastName().toLowerCase().contains(finalValue);
                    break;
                case "tipo":
                    specificPredicate = contact -> contact.getContactType().name().toLowerCase().contains(finalValue);
                    break;
                case "email":
                    specificPredicate = contact -> contact.getEmails().stream()
                            .anyMatch(email -> email.getEmail().toLowerCase().contains(finalValue));
                    break;
                case "celular":
                    specificPredicate = contact -> contact.getPhones().stream()
                            .anyMatch(phone -> phone.getNumber().toLowerCase().contains(finalValue));
                    break;
                case "social":
                    specificPredicate = contact -> contact.getSocialMedias().stream()
                            .anyMatch(social -> social.getUsername().toLowerCase().contains(finalValue));
                    break;
                case "dir":
                    specificPredicate = contact -> contact.getAddresses().stream()
                            .anyMatch(address -> address.toString().toLowerCase().contains(finalValue));
                    break;
//                case "notas":
//                    specificPredicate = contact -> contact.getNotes() contact.getNotes().contains(finalValue);
//                    break;
                case "fecha":
                    specificPredicate = contact -> {
                        if (contact.getDates().isEmpty()) return false; // No tiene fechas, no coincide

                        // Intentar parsear como fecha completa (dd/MM/yyyy)
                        try {
                            // Asume formato "dd/MM/yyyy" para fecha exacta
                            LocalDate searchDate = LocalDate.parse(finalValue, java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                            return contact.getDates().stream().anyMatch(d -> d.getDate().equals(searchDate));
                        } catch (DateTimeParseException e) {}

                        // Intentar parsear como mes (nombre completo o abreviado)
                        try {
                            // Buscar por nombre de mes (ej. "febrero")
                            // Convierte el nombre del mes a un objeto Month
                            Month searchMonth = Month.valueOf(finalValue.toUpperCase()); // "FEBRERO"
                            return contact.getDates().stream().anyMatch(d -> d.getDate().getMonth() == searchMonth);
                        } catch (IllegalArgumentException e) {}

                        // Intentar parsear como número de día
                        try {
                            int searchDay = Integer.parseInt(finalValue);
                            if (searchDay >= 1 && searchDay <= 31) { // Validar rango de día
                                return contact.getDates().stream().anyMatch(d -> d.getDate().getDayOfMonth() == searchDay);
                            }
                        } catch (NumberFormatException e) {}

                        return false; // Si no coincide con ninguno de los formatos
                    };
                    break;
                default:
                    // Si la palabra clave no es reconocida, se tratará como texto libre
                    break;
            }

            if (specificPredicate != null) {
                finalPredicate = finalPredicate.and(specificPredicate);
            }

            // Para eliminar el fragmento, usamos replaceFirst para evitar problemas si el valor aparece en el texto libre
            remainingSearchText = remainingSearchText.replaceFirst(Pattern.quote(matcher.group(0)), "").trim();
        }

        if (!remainingSearchText.isEmpty()) {
            final String freeText = remainingSearchText.toLowerCase();
            Predicate<Contact> freeTextPredicate = contact ->
                    (contact.getName() != null && contact.getName().toLowerCase().contains(freeText));
            freeTextPredicate = freeTextPredicate.or(contact -> {
                if (contact instanceof Person) {
                    Person person = (Person) contact;
                    return person.getLastName() != null && person.getLastName().toLowerCase().contains(freeText);
                }
                return false;
            });

            finalPredicate = finalPredicate.and(freeTextPredicate);
        }

        consumer.accept(finalPredicate);
    }

    public void setOnSearch(Consumer<Predicate<Contact>> consumer) {
        this.consumer = consumer;
    }
}
