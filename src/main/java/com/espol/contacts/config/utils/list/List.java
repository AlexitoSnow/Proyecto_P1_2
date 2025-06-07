package com.espol.contacts.config.utils.list;

import java.io.Serializable;
import java.util.Comparator;

// TODO: Add contains method to check if an element exists in the list
// TODO: Add indexOf method to find the index of an element
// TODO: Remove public modifier, as it is redundant in an interface
// TODO: Remove comments that are not necessary for understanding the code
public interface List<E> extends Iterable<E>, Serializable {

    public int size();

    public boolean isEmpty();

    public void clear();

    public boolean addFirst(E element); // inserta E al inicio

    public boolean addLast(E element); // inserta E al final

    public E removeFirst(); // remueve E al inicio

    public E removeLast(); // remueve E al final

    public boolean add(int index, E element); // inserta E en posición index

    public E remove(int index); // remueve y retorna E en posición index

    public E remove(E elemento); // remueve E

    public E get(int index); // retorna E ubicado en la posición index

    public E set(int index, E element); // setea E en la posición index

    public E getPrevious(E actual);

    public E getNext(E actual);

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
