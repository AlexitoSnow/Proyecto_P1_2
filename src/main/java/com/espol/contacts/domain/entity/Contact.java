package com.espol.contacts.domain.entity;

import com.espol.contacts.domain.entity.enums.ContactType;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Contact implements Serializable {
    protected Long id;
    protected final Set<Email> emails;
    protected final Set<Phone> phones;
    protected final Set<Address> addresses;
    protected final Set<ImportantDate> dates;
    protected final Set<SocialMedia> socialMedias;
    protected Set<RelatedContact> relatedContacts;
    protected Set<String> gallery;
    protected String name;
    protected String notes;
    protected ContactType contactType;
    protected Boolean favorite;
    protected String profilePicture;

    protected Contact(ContactBuilder<?> builder) {
        this.id = builder.id;
        this.emails = builder.emails;
        this.phones = builder.phones;
        this.addresses = builder.addresses;
        this.dates = builder.dates;
        this.socialMedias = builder.socialMedias;
        this.name = builder.name;
        this.notes = builder.notes;
        this.contactType = builder.contactType;
        this.favorite = builder.favorite;
        this.relatedContacts = builder.relatedContacts;
        this.gallery = builder.gallery;
        this.profilePicture = builder.profilePicture;
    }

    public static abstract class ContactBuilder<T extends ContactBuilder<T>> {
        protected Long id;
        protected Set<Email> emails = new HashSet<>();
        protected Set<Phone> phones = new HashSet<>();
        protected Set<Address> addresses = new HashSet<>();
        protected Set<ImportantDate> dates = new HashSet<>();
        protected Set<SocialMedia> socialMedias = new HashSet<>();
        protected Set<RelatedContact> relatedContacts = new HashSet<>();
        protected Set<String> gallery = new HashSet<>();
        protected String name;
        protected String notes;
        protected ContactType contactType;
        protected Boolean favorite = false;
        protected String profilePicture;

        public T id(Long id) {
            this.id = id;
            return self();
        }

        public T name(String name) {
            this.name = name;
            return self();
        }

        public T profilePicture(String profilePicture) {
            this.profilePicture = profilePicture;
            return self();
        }

        public T addEmail(Email email) {
            this.emails.add(email);
            return self();
        }

        public T addPhone(Phone phone) {
            this.phones.add(phone);
            return self();
        }

        public T addAddress(Address address) {
            this.addresses.add(address);
            return self();
        }

        public T addDate(ImportantDate date) {
            this.dates.add(date);
            return self();
        }

        public T addSocialMedia(SocialMedia socialMedia) {
            this.socialMedias.add(socialMedia);
            return self();
        }

        public T addImage(String image) {
            this.gallery.add(image);
            return self();
        }

        public T addRelatedContact(RelatedContact relatedContact) {
            this.relatedContacts.add(relatedContact);
            return self();
        }

        public T notes(String notes) {
            this.notes = notes;
            return self();
        }

        public T contactType(ContactType contactType) {
            this.contactType = contactType;
            return self();
        }

        public T favorite(Boolean favorite) {
            this.favorite = favorite;
            return self();
        }

        protected abstract T self();
        public abstract Contact build();
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public Set<Email> getEmails() {
        return emails;
    }

    public Set<String> getGallery() {
        return gallery;
    }

    public Set<Phone> getPhones() {
        return phones;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public Set<ImportantDate> getDates() {
        return dates;
    }

    public Set<SocialMedia> getSocialMedias() {
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

    public Set<RelatedContact> getRelatedContacts() {
        return relatedContacts;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
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

    @Override
    public String toString() {
        return name;
    }
}