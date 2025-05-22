package com.espol.contacts.domain.entity;

import com.espol.contacts.domain.entity.enums.AddressType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Address {
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private AddressType type;
    private String mapUrl;

    private Address() {}

    @Contract(value = " -> new", pure = true)
    public static @NotNull Address build() {
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
}
