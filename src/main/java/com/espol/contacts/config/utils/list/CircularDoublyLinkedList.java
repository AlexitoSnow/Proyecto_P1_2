package com.espol.contacts.config.utils.list;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class CircularDoublyLinkedList<E> implements List<E> {

    private Node<E> head;
    private int size = 0;

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
        head = null;
        size = 0;
    }
    @Override
    public boolean addFirst(E element) {
        Node<E> newNode = new Node<>(element);
        if (isEmpty()) {
            head = newNode;
        } else {
            Node<E> tail = head.getPrevious();
            newNode.setNext(head);
            newNode.setPrevious(tail);
            head.setPrevious(newNode);
            tail.setNext(newNode);
            head = newNode;
        }
        size++;
        return true;
    }

    @Override
    public boolean addLast(E element) {
        if (isEmpty()) return addFirst(element);

        Node<E> newNode = new Node<>(element);
        Node<E> tail = head.getPrevious();

        newNode.setNext(head);
        newNode.setPrevious(tail);
        tail.setNext(newNode);
        head.setPrevious(newNode);

        size++;
        return true;
    }

    @Override
    public E removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();

        E value = head.getElement();
        if (size == 1) {
            head = null;
        } else {
            Node<E> tail = head.getPrevious();
            head = head.getNext();
            head.setPrevious(tail);
            tail.setNext(head);
        }
        size--;
        return value;
    }

    @Override
    public E removeLast() {
        if (isEmpty()) throw new NoSuchElementException();

        Node<E> tail = head.getPrevious();
        E value = tail.getElement();

        if (size == 1) {
            head = null;
        } else {
            Node<E> newTail = tail.getPrevious();
            newTail.setNext(head);
            head.setPrevious(newTail);
        }

        size--;
        return value;
    }

    @Override
    public boolean add(int index, E element) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();

        if (index == 0) return addFirst(element);
        if (index == size) return addLast(element);

        Node<E> current = getNode(index);
        Node<E> previous = current.getPrevious();
        Node<E> newNode = new Node<>(element);

        previous.setNext(newNode);
        newNode.setPrevious(previous);
        newNode.setNext(current);
        current.setPrevious(newNode);

        size++;
        return true;
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();

        if (index == 0) return removeFirst();
        if (index == size - 1) return removeLast();

        Node<E> current = getNode(index);
        current.getPrevious().setNext(current.getNext());
        current.getNext().setPrevious(current.getPrevious());

        size--;
        return current.getElement();
    }

    @Override
    public E remove(E element) {
        if (isEmpty()) return null;

        Node<E> current = head;
        for (int i = 0; i < size; i++) {
            if (current.getElement().equals(element)) {
                if (i == 0) return removeFirst();
                if (i == size - 1) return removeLast();

                current.getPrevious().setNext(current.getNext());
                current.getNext().setPrevious(current.getPrevious());

                size--;
                return current.getElement();
            }
            current = current.getNext();
        }
        return null;
    }

    @Override
    public E get(int index) {
        return getNode(index).getElement();
    }

    @Override
    public E set(int index, E element) {
        Node<E> current = getNode(index);
        E old = current.getElement();
        current.setElement(element);
        return old;
    }

    @Override
    public E getPrevious(E actual) {
        Node<E> node = findNode(actual);
        return node != null ? node.getPrevious().getElement() : null;
    }

    @Override
    public E getNext(E actual) {
        Node<E> node = findNode(actual);
        return node != null ? node.getNext().getElement() : null;
    }

    private Node<E> getNode(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();

        Node<E> current = head;
        for (int i = 0; i < index; i++) current = current.getNext();
        return current;
    }

    private Node<E> findNode(E element) {
        Node<E> current = head;
        for (int i = 0; i < size; i++) {
            if (current.getElement().equals(element)) return current;
            current = current.getNext();
        }
        return null;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Node<E> current = head;
            private int count = 0;

            @Override
            public boolean hasNext() {
                return count < size;
            }

            @Override
            public E next() {
                if (!hasNext()) throw new NoSuchElementException();
                E value = current.getElement();
                current = current.getNext();
                count++;
                return value;
            }
        };
    }

    @Override
    public boolean contains(E element) {
        return indexOf(element) != -1;
    }

    @Override
    public int indexOf(E element) {
        if (isEmpty()) return -1;

        Node<E> current = head;
        for (int i = 0; i < size; i++) {
            if (current.getElement().equals(element)) {
                return i;
            }
            current = current.getNext();
        }
        return -1;
    }

    public ListIterator<E> listIterator() {
        return new CircularListIterator();
    }

    public ListIterator<E> listIterator(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        CircularListIterator iterator = new CircularListIterator();
        for (int i = 0; i < index; i++) {
            iterator.next();
        }
        return iterator;
    }

    @Override
    public String toString() {
        if (isEmpty()) return "[]";

        StringBuilder sb = new StringBuilder("[");
        Node<E> current = head;
        for (int i = 0; i < size; i++) {
            sb.append(current.getElement());
            if (i < size - 1) sb.append(", ");
            current = current.getNext();
        }
        sb.append("]");
        return sb.toString();
    }

    private class CircularListIterator implements ListIterator<E> {
        private Node<E> current = head;
        private int nextIndex = 0;

        @Override
        public boolean hasNext() {
            return size != 0;
        }

        @Override
        public E next() {
            if (size == 0) throw new NoSuchElementException();
            E value = current.getElement();
            current = current.getNext();
            nextIndex = nextIndex < size - 1 ? nextIndex + 1 : 0;
            return value;
        }

        @Override
        public boolean hasPrevious() {
            return size != 0;
        }

        @Override
        public E previous() {
            if (size == 0) throw new NoSuchElementException();
            current = current.getPrevious();
            nextIndex = nextIndex > 0 ? nextIndex - 1 : size - 1;
            return current.getElement();
        }

        @Override
        public int nextIndex() {
            return nextIndex;
        }

        @Override
        public int previousIndex() {
            return nextIndex > 0 ? nextIndex - 1 : size - 1;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void set(E e) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(E e) {
            throw new UnsupportedOperationException();
        }
    }

    private static final long serialVersionUID = 927364827648273648L;
}


