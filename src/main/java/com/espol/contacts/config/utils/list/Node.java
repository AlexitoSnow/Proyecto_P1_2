package com.espol.contacts.config.utils.list;

import java.io.Serializable;

public class Node<E> implements Serializable {

    private E element;
    private Node<E> next;
    private Node<E> previous;

    public Node(E element) {
        this.element = element;
        this.next = this;
        this.previous = this;
    }
    public E getElement() {
        return element;
    }

    public void setElement(E element) {
        this.element = element;
    }

    public Node<E> getNext() {
        return next;
    }

    public void setNext(Node<E> next) {
        this.next = next;
    }

    public Node<E> getPrevious() {
        return previous;
    }

    public void setPrevious(Node<E> previous) {
        this.previous = previous;
    }
}
