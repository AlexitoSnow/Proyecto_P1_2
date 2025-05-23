package com.espol.contacts.domain.entity;

import com.espol.contacts.domain.entity.enums.ContactType;

import java.util.*;

// TODO: Convertir en serializable
// TODO: Convertir subclases y clases que usa en serializable
public class Contact {
    protected Long id;
    protected final Set<Email> emails;
    protected final List<Phone> phones;
    protected final Set<Address> addresses;
    protected final Set<ImportantDate> dates;
    protected final List<SocialMedia> socialMedias;
    protected String name;
    protected String notes;
    protected ContactType contactType;
    protected Boolean favorite;

    protected Contact() {
        emails = new HashSet<>();
        phones = new ArrayList<>();
        addresses = new HashSet<>();
        dates = new HashSet<>();
        socialMedias = new ArrayList<>();
        favorite = false;
    }

    public static Contact build() {
        return new Contact();
    }

    public Contact favorite(Boolean favorite) {
        this.favorite = favorite;
        return this;
    }

    public Contact name(String name) {
        this.name = name;
        return this;
    }

    public Contact addEmail(Email email) {
        this.emails.add(email);
        return this;
    }

    public Contact addPhone(Phone phone) {
        this.phones.add(phone);
        return this;
    }

    public Contact addAddress(Address address) {
        this.addresses.add(address);
        return this;
    }

    public Contact removeAddress(Address address) {
        this.addresses.remove(address);
        return this;
    }

    public Contact addDate(ImportantDate date) {
        this.dates.add(date);
        return this;
    }

    public Contact removeDate(ImportantDate date) {
        this.dates.remove(date);
        return this;
    }

    public Contact addSocialMedia(SocialMedia socialMedia) {
        this.socialMedias.add(socialMedia);
        return this;
    }

    public Contact removeSocialMedia(SocialMedia socialMedia) {
        this.socialMedias.remove(socialMedia);
        return this;
    }

    public Contact notes(String notes) {
        this.notes = notes;
        return this;
    }

    public Contact contactType(ContactType contactType) {
        this.contactType = contactType;
        return this;
    }

    public Contact id(Long id) {
        this.id = id;
        return this;
    }

    public Set<Email> getEmails() {
        return emails;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public Set<ImportantDate> getDates() {
        return dates;
    }

    public List<SocialMedia> getSocialMedias() {
        return socialMedias;
    }

    public String getNotes() {
        return notes;
    }

    public ContactType getContactType() {
        return contactType;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public boolean isFavorite() {
        return favorite;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(id, contact.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}