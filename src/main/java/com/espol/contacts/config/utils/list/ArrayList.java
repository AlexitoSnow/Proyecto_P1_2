package com.espol.contacts.config.utils.list;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;


public class ArrayList<E> implements List<E> {

    private E[] data;
    private int size;
    private static final int INITIAL_CAPACITY = 10;

    public ArrayList() {
        data = (E[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    private void ensureCapacity() {
        if (size == data.length) {
            E[] newData = (E[]) new Object[data.length * 2];
            System.arraycopy(data, 0, newData, 0, size);
            data = newData;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        data = (E[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    @Override
    public boolean addFirst(E element) {
        return add(0, element);
    }

    @Override
    public boolean addLast(E element) {
        return add(size, element);
    }

    @Override
    public boolean add(int index, E element) {
        if (index < 0 || index > size) return false;
        ensureCapacity();
        System.arraycopy(data, index, data, index + 1, size - index);
        data[index] = element;
        size++;
        return true;
    }

    @Override
    public E removeFirst() {
        if (isEmpty()) return null;
        return remove(0);
    }

    @Override
    public E removeLast() {
        if (isEmpty()) return null;
        return remove(size - 1);
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index >= size) return null;
        E removed = data[index];
        System.arraycopy(data, index + 1, data, index, size - index - 1);
        data[--size] = null;
        return removed;
    }

    @Override
    public E remove(E element) {
        int index = indexOf(element);
        if (index != -1) {
            return remove(index);
        }
        return null;
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= size) return null;
        return data[index];
    }

    @Override
    public E set(int index, E element) {
        if (index < 0 || index >= size) return null;
        E old = data[index];
        data[index] = element;
        return old;
    }

    @Override
    public E getPrevious(E current) {
        int index = indexOf(current);
        if (index == -1) return null;
        return data[(index - 1 + size) % size];
    }

    @Override
    public E getNext(E current) {
        int index = indexOf(current);
        if (index == -1) return null;
        return data[(index + 1) % size];
    }

    @Override
    public boolean contains(E element) {
        return indexOf(element) != -1;
    }

    @Override
    public int indexOf(E element) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(element)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public ListIterator<E> listIterator() {
        return new ArrayListIterator(0);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new ArrayListIterator(index);
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int index = 0;
            public boolean hasNext() {
                return index < size;
            }
            public E next() {
                if (!hasNext()) throw new NoSuchElementException();
                return data[index++];
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(data[i]);
            if (i < size - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    private static final long serialVersionUID = 3847261938472619384L;

    private class ArrayListIterator implements ListIterator<E> {
        private int index = 0;

        public ArrayListIterator(int index) {
            if (index < 0 || index > size) {
                throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
            }
            this.index = index;
        }

        public boolean hasNext() {
            return index < size;
        }

        public E next() {
            if (!hasNext()) throw new NoSuchElementException();
            return data[index++];
        }

        public boolean hasPrevious() {
            return index > 0;
        }

        public E previous() {
            if (!hasPrevious()) throw new NoSuchElementException();
            return data[--index];
        }

        public int nextIndex() {
            return index;
        }

        public int previousIndex() {
            return index - 1;
        }

        public void remove() {
            if (index == 0) throw new IllegalStateException();
            ArrayList.this.remove(--index);
        }

        public void set(E e) {
            if (index == 0) throw new IllegalStateException();
            ArrayList.this.set(index - 1, e);
        }

        public void add(E e) {
            ArrayList.this.add(index++, e);
        }
    }
}


