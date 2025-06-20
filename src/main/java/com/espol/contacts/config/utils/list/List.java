package com.espol.contacts.config.utils.list;

import java.io.Serializable;
import java.util.Comparator;
import java.util.ListIterator;


public interface List<E> extends Iterable<E>, Serializable {

    int size();

    boolean isEmpty();

    void clear();

    boolean addFirst(E element);

    boolean addLast(E element);

    E removeFirst();

    E removeLast();

    boolean add(int index, E element);

    E remove(int index);

    E remove(E elemento);

    E get(int index);

    E set(int index, E element);

    E getPrevious(E actual);

    E getNext(E actual);

    boolean contains(E element);

    int indexOf(E element);

    ListIterator<E> listIterator();

    default void sort(Comparator<E> comparator) {
        int n = this.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                E a = this.get(j);
                E b = this.get(j + 1);
                if (comparator.compare(a, b) > 0) {
                    this.set(j, b);
                    this.set(j + 1, a);
                }
            }
        }
    }
}
