package com.espol.contacts.domain.entity.enums;

public enum Relationship {
    FATHER("Padre"),
    MOTHER("Madre"),
    SISTER("Hermana"),
    BROTHER("Hermano"),
    SON("Hijo"),
    DAUGHTER("Hija"),
    GRANDMOTHER("Abuela"),
    GRANDFATHER("Abuelo"),
    UNCLE("Tío"),
    AUNT("Tía"),
    COUSIN("Primo"),
    BOSS("Jefe"),
    COLLEAGUE("Colega"),
    SECRETARY("Secretario"),
    ASSISTANT("Asistente"),
    FRIEND("Amigo"),
    PARTNER("Pareja"),
    NEIGHBOR("Vecino"),
    OTHER("Otro");

    private final String label;

    Relationship(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
