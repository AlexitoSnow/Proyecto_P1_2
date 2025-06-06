package com.espol.contacts.domain.entity;

public class Company extends Contact {
    private Company(CompanyBuilder builder) {
        super(builder);
    }

    public static class CompanyBuilder extends ContactBuilder<CompanyBuilder> {
        @Override
        protected CompanyBuilder self() {
            return this;
        }

        @Override
        public Company build() {
            return new Company(this);
        }
    }

    public static CompanyBuilder builder() {
        return new CompanyBuilder();
    }
}