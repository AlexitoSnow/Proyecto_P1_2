package com.espol.contacts.domain.entity;

import com.espol.contacts.domain.entity.enums.DateType;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Objects;

public class ImportantDate implements Serializable {
    private final LocalDate date;
    private final DateType type;

    public ImportantDate(LocalDate date, DateType type) {
        this.date = date;
        this.type = type;
    }

    public LocalDate getDate() {
        return date;
    }

    public DateType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ImportantDate that = (ImportantDate) o;
        return type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(type);
    }

    @Override
    public String toString() {
        return date.format(new DateTimeFormatterBuilder().appendPattern("dd/MM/yyyy").toFormatter());
    }
}
