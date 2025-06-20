package com.espol.contacts.domain.entity;

import com.espol.contacts.domain.entity.enums.IndustryType;

public class Company extends Contact {
    private final IndustryType industry;

    private Company(CompanyBuilder builder) {
        super(builder);
        this.industry = builder.industry;
    }

    public static class CompanyBuilder extends ContactBuilder<CompanyBuilder> {
        private IndustryType industry;

        public CompanyBuilder industry(IndustryType industry) {
            this.industry = industry;
            return this;
        }

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

    public IndustryType getIndustry() {
        return industry;
    }

    private static final long serialVersionUID = 6182736451827364518L;
}