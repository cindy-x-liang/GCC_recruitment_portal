package cs2110;

import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * Assignment metadata
 * Name(s) and NetID(s): Cindy Liang CL2329 and Ashley Liu AL844
 * Hours spent on assignment: 7 hours
 */

/**
 * A list of elements of type `T` implemented as a singly linked list.  Null elements are not
 * allowed.
 */
public class LinkedSeq<T> implements Seq<T> {

    /**
     * Number of elements in the list.  Equal to the number of linked nodes reachable from `head`.
     */
    private int size;

    /**
     * First node of the linked list (null if list is empty).
     */
    private Node<T> head;

    /**
     * Last node of the linked list starting at `head` (null if list is empty).  Next node must be
     * null.
     */
    private Node<T> tail;

    /**
     * Assert that this object satisfies its class invariants.
     */
    private void assertInv() {
        assert size >= 0;
        if (size == 0) {
            assert head == null;
            assert tail == null;
        } else {
            assert head != null;
            assert tail != null;

            //the following code is inspired by lecture 10 LinkedList.java source code
            int numLinkedNodes = 0;
            Node<T> currNode = head;
            Node<T> prevNode = null;
            while (currNode != null) {
                numLinkedNodes++;
                prevNode = currNode;
                currNode = currNode.next();
            }
            assert numLinkedNodes == size;
            assert prevNode == tail;

        }
    }

    /**
     * Create an empty list.
     */
    public LinkedSeq() {
        size = 0;
        head = null;
        tail = null;

        assertInv();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void prepend(T elem) {
        assertInv();
        assert elem != null;

        head = new Node<>(elem, head);
        // If list was empty, assign tail as well
        if (tail == null) {
            tail = head;
        }
        size += 1;


        assertInv();
    }

    /**
     * Return a text representation of this list with the following format: the string starts with
     * '[' and ends with ']'.  In between are the string representations of each element, in
     * sequence order, separated by ", ".
     * <p>
     * Example: a list containing 4 7 8 in that order would be represented by "[4, 7, 8]".
     * <p>
     * Example: a list containing two empty strings would be represented by "[, ]".
     * <p>
     * The string representations of elements may contain the characters '[', ',', and ']'; these
     * are not treated specially.
     */
    @Override
    public String toString() {
        String str = "[";
        Node<T> currNode = head;
        int currIndex = 0;
        while (currIndex < size-1) {
            str+=currNode.data()+", ";
            currNode = currNode.next();
            currIndex +=1;
        }
        //need to check if current Node is not null because it would be null for an empty list
        if (currNode!=null){
            str+=currNode.data();
        }
        str += "]";
        return str;
    }

    @Override
    public boolean contains(T elem) {
        assert elem!=null;

        boolean result = false;
        Node<T> currNode = head;

        while (currNode != null) {
            if (currNode.data().equals(elem)){
                result = true;
            }
            currNode = currNode.next();
        }
        return result;
    }

    @Override
    public T get(int index) {
        assert 0<=index && index<=size;

        int currIndex  = 0;
        Node<T> currNode = head;
        while (currIndex < index) {
            currNode = currNode.next();
            currIndex ++;
        }

        assert currNode.data()!=null;
        return currNode.data();
    }

    @Override
    public void append(T elem) {
        assert elem!=null;

        Node<T> newNode = new Node<>(elem, null);
        if (size == 0){
            head = newNode;
            tail = newNode;
        }
        else{
            tail.setNext(newNode);
            tail = newNode;
        }
        size++;
        assertInv();
    }

    @Override
    public void insertBefore(T elem, T successor) {
        assert elem!=null && successor!=null;

        Node<T> newNode = new Node<>(elem, null);
        Node<T> currNode = head;
        Node<T> prevNode = null;
        if (currNode.data().equals(successor)){
            newNode.setNext(head);
            head = newNode;
        }
        else{
            while (!(currNode.data()).equals(successor)) {
                prevNode = currNode;
                currNode = currNode.next();
            }
            prevNode.setNext(newNode);
            newNode.setNext(currNode);
        }
        size++;
        assertInv();
    }

    @Override
    public boolean remove(T elem) {
        assert elem!=null;

        Node<T> currNode = head;
        Node<T> prevNode = null;
        if (currNode.data().equals(elem)){
            head = head.next();
            size--;
            assertInv();
            return true;
        }
        while (currNode.next() != null) {
            if (currNode.data().equals(elem)){
                prevNode.setNext(currNode.next());
                size--;
                assertInv();
                return true;
            }
            prevNode = currNode;
            currNode = currNode.next();
        }
        if (tail.data().equals(elem)){
            prevNode.setNext(null);
            tail = prevNode;
            size--;
            assertInv();
            return true;
        }
        return false;
    }

    /**
     * Return whether this and `other` are `LinkedSeq`s containing the same elements in the same
     * order.  Two elements `e1` and `e2` are "the same" if `e1.equals(e2)`.  Note that `LinkedSeq`
     * is mutable, so equivalence between two objects may change over time.  See `Object.equals()`
     * for additional guarantees.
     */
    @Override
    public boolean equals(Object other) {
        // Note: In the `instanceof` check, we write `LinkedSeq` instead of `LinkedSeq<T>` because
        // of a limitation inherent in Java generics: it is not possible to check at run-time
        // what the specific type `T` is.  So instead we check a weaker property, namely,
        // that `other` is some (unknown) instantiation of `LinkedSeq`.  As a result, the static
        // type returned by `currNodeOther.data()` is `Object`.
        if (!(other instanceof LinkedSeq)) {
            return false;
        }

        LinkedSeq otherSeq = (LinkedSeq) other;
        Node<T> currNodeThis = head;
        Node currNodeOther = otherSeq.head;

        boolean result = true;

        while (currNodeThis != null && currNodeOther !=null) {
            if (!currNodeThis.data().equals(currNodeOther.data())){
                result = false;
            }
            currNodeThis = currNodeThis.next();
            currNodeOther = currNodeOther.next();
        }
        return result;
    }

    /*
     * There is no need to read the remainder of this file for the purpose of completing the
     * assignment.  We have not yet covered the implementation of these concepts in class.
     */

    /**
     * Returns a hash code value for the object.  See `Object.hashCode()` for additional
     * guarantees.
     */
    @Override
    public int hashCode() {
        // Whenever overriding `equals()`, must also override `hashCode()` to be consistent.
        // This hash recipe is recommended in _Effective Java_ (Joshua Bloch, 2008).
        int hash = 1;
        for (T e : this) {
            hash = 31 * hash + e.hashCode();
        }
        return hash;
    }

    /**
     * Return an iterator over the elements of this list (in sequence order).  By implementing
     * `Iterable`, clients can use Java's "enhanced for-loops" to iterate over the elements of the
     * list.  Requires that the list not be mutated while the iterator is in use.
     */
    @Override
    public Iterator<T> iterator() {
        assertInv();

        // Return an instance of an anonymous inner class implementing the Iterator interface.
        // For convenience, this uses Java features that have not eyt been introduced in the course.
        return new Iterator<>() {
            private Node<T> next = head;

            public T next() throws NoSuchElementException {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                T result = next.data();
                next = next.next();
                return result;
            }

            public boolean hasNext() {
                return next != null;
            }
        };
    }
}
