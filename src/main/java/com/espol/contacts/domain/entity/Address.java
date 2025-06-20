package com.espol.contacts.domain.entity;

import com.espol.contacts.config.utils.list.ArrayList;
import com.espol.contacts.config.utils.list.List;
import com.espol.contacts.domain.entity.enums.AddressType;

import java.io.Serializable;
import java.util.Objects;

public class Address implements Serializable {
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private AddressType type;
    private String mapUrl;

    private Address() {}

    public static Address build() {
        return new Address();
    }

    public Address street(String street) {
        this.street = street;
        return this;
    }

    public Address city(String city) {
        this.city = city;
        return this;
    }

    public Address state(String state) {
        this.state = state;
        return this;
    }

    public Address postalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public Address country(String country) {
        this.country = country;
        return this;
    }

    public Address type(AddressType type) {
        this.type = type;
        return this;
    }

    public Address mapUrl(String mapUrl) {
        this.mapUrl = mapUrl;
        return this;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCountry() {
        return country;
    }

    public AddressType getType() {
        return type;
    }

    public String getMapUrl() {
        return mapUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return type == address.type;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(type);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        List<String> addressParts = new ArrayList<>();
        if (city != null && !city.isEmpty()) {
            addressParts.addLast(city);
        }
        if (state != null && !state.isEmpty()) {
            addressParts.addLast(state);
        }
        if (country != null && !country.isEmpty()) {
            addressParts.addLast(country);
        }

        if (street != null && !street.isEmpty()) {
            addressParts.addLast(street);
        }

        if (!addressParts.isEmpty()) {
            sb.append(String.join(", ", addressParts));
        }

        if (sb.length() > 0 && postalCode != null) {
            sb.append(". ");
        }

        if (postalCode != null) {
            sb.append(postalCode);
        }

        return sb.toString();
    }

    private static final long serialVersionUID = 927348273648273648L;
}
