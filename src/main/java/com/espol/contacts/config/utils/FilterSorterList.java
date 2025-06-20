package com.espol.contacts.config.utils;

import com.espol.contacts.config.utils.list.CircularDoublyLinkedList;
import com.espol.contacts.config.utils.list.List;

import java.util.Comparator;
import java.util.function.Predicate;

public class FilterSorterList {

    private FilterSorterList(){}

    public static <E> List<E> filterAndSort(
            List<E> contacts,
            Predicate<E> filter,
            Predicate<E> search,
            Comparator<E> comparator
    ) {
        List<E> result = new CircularDoublyLinkedList<>();
        contacts.forEach(contact -> {
            if (filter.test(contact) && search.test(contact)) result.addLast(contact);
        });
        if (comparator != null) result.sort(comparator);
        return result;
    }
}
