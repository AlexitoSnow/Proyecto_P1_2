package com.espol.contacts.domain.entity;

public class Person extends Contact {
    private final String middleName;
    private final String lastName;

    private Person(PersonBuilder builder) {
        super(builder);
        this.middleName = builder.middleName;
        this.lastName = builder.lastName;
    }

    public static class PersonBuilder extends ContactBuilder<PersonBuilder> {
        private String middleName;
        private String lastName;

        public PersonBuilder middleName(String middleName) {
            this.middleName = middleName;
            return this;
        }

        public PersonBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        @Override
        protected PersonBuilder self() {
            return this;
        }

        @Override
        public Person build() {
            return new Person(this);
        }
    }

    public static PersonBuilder builder() {
        return new PersonBuilder();
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        if (middleName != null && !middleName.isEmpty()) {
            sb.append(" ").append(middleName);
        }
        if (lastName != null && !lastName.isEmpty()) {
            sb.append(" ").append(lastName);
        }
        return sb.toString();
    }

    private static final long serialVersionUID = 5728391029384756102L;
}
