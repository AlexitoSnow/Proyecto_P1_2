package com.espol.contacts.domain.entity;

import com.espol.contacts.domain.entity.enums.DateType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Objects;

public class ImportantDate {
    private LocalDate date;
    private DateType type;

    public ImportantDate() {}

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

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setType(DateType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ImportantDate that = (ImportantDate) o;
        return Objects.equals(date, that.date) && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, type);
    }

    @Override
    public String toString() {
        return date.format(new DateTimeFormatterBuilder().appendPattern("dd/MM/yyyy").toFormatter());
    }
}
