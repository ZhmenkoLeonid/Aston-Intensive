package com.zhmenko.customlist;

import com.zhmenko.exception.OutOfCapacityException;

import java.util.Objects;

/**
 * Custom ArrayList implementation with only few basic methods
 * @param <E> the type of elements in this list
 */
public class CustomArrayList<E> {
    private static final int DEFAULT_CAPACITY = 16;

    // Max available capacity
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    private Object[] elements;
    private int size;

    /**
     * Constructs a new list with the default capacity
     */
    public CustomArrayList() {
        this.elements = new Object[DEFAULT_CAPACITY];
    }

    /**
     * Constructs a new list with the specified capacity
     * @param capacity initial capacity
     */
    public CustomArrayList(final int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("Capacity must be > 0");
        this.elements = new Object[capacity];
    }

    /**
     * Returns the number of elements
     * @return list size
     */
    public int getSize() {
        return size;
    }

    /**
     * Returns true if list not contains elements
     * @return true if list is empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Check containing of element in list
     * @param element element whose presence in this list is to be tested
     * @return true if list contains object
     */
    public boolean isContain(final E element) {
        return indexOf(element) != -1;
    }

    /**
     * Add element to list
     * @param element element to be added to this collection
     * @return true if element successfully added
     */
    public boolean add(final E element) {
        if (size == elements.length) ensureCapacity(size + 1);
        elements[size++] = element;
        return true;
    }

    /**
     * @param index   index at which the specified element is to be inserted
     * @param element element to be inserted
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    public void add(final int index, final E element) {
        Objects.checkIndex(index, size + 1);
        if (size == elements.length) ensureCapacity(size + 1);
        System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = element;
        size++;
    }

    /**
     * Add elements of the specified array to the end of the list
     * @param array - array containing elements to be added to this list
     * @return true if all array elements successfully added
     */
    public boolean addAll(final E[] array) {
        if (array.length == 0) return false;
        if (array.length > (elements.length - size)) ensureCapacity(size + array.length);
        System.arraycopy(array, 0, elements, size, array.length);
        size += array.length;
        return true;
    }

    /**
     * Add elements of the specified array to the specified index
     * @param index index at which to insert the first element from the
     *              specified array
     * @param array array containing elements to be added to this list
     * @return true if all array elements successfully added
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    public boolean addAll(final int index, final E[] array) {
        Objects.checkIndex(index, size + 1);
        if (array.length == 0) return false;
        if (array.length > (elements.length - size)) ensureCapacity(size + array.length);
        int numMoved = size - index;
        if (numMoved > 0)
            System.arraycopy(elements, index, elements, index + array.length, numMoved);
        System.arraycopy(array, 0, elements, index, array.length);
        size += array.length;
        return true;
    }

    /**
     * @param element element to be removed from this list, if present
     * @return true if element removed, false if element not found
     */
    public boolean remove(final E element) {
        int objectIndex = indexOf(element);
        if (objectIndex == -1) return false;
        final int newSize = size - 1;
        if (newSize > objectIndex)
            System.arraycopy(elements, objectIndex + 1, elements, objectIndex, newSize - objectIndex);
        elements[newSize] = null;
        size = newSize;
        return true;
    }

    /**
     * Removes element at the specified index and returns the removed element
     * @param index the index of the element to be removed
     * @return removed element
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    @SuppressWarnings("unchecked")
    public E remove(final int index) {
        Objects.checkIndex(index, size);
        Object removedValue = elements[index];
        final int newSize = size - 1;
        if (newSize > index)
            System.arraycopy(elements, index + 1, elements, index, newSize - index);
        elements[newSize] = null;
        size = newSize;
        return (E) removedValue;
    }

    /**
     * remove all elements from the list
     */
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    /**
     *
     * @param index index of the element to return
     * @return the element at the given index
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    @SuppressWarnings("unchecked")
    public E get(final int index) {
        Objects.checkIndex(index, size);
        return (E) elements[index];
    }

    /**
     * @param index   index of the element to replace
     * @param element element to be stored at the specified position
     * @return old value at the specified position
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    @SuppressWarnings("unchecked")
    public E set(final int index, final E element) {
        Objects.checkIndex(index, size);
        E oldValue = (E) elements[index];
        elements[index] = element;
        return oldValue;
    }


    /**
     * Find the first entry of the specified element in the list
     * @param element element to search for
     * @return first entry element index if element found, else return -1
     */
    public int indexOf(final E element) {
        if (element == null) {
            for (int i = 0; i < size; i++) {
                if (elements[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (element.equals(elements[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * @return array elements string representation
     */
    @Override
    public String toString() {
        if (size == 0) return "[]";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (int i = 0; i < size - 1; i++) {
            stringBuilder
                    .append(elements[i])
                    .append(", ");
        }
        stringBuilder.append(elements[size - 1]);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }


    /**
     * method for increase capacity
     *
     * @throws OutOfCapacityException if capacity needed to be more than possible
     */
    private void ensureCapacity(int minCapacity) {
        if (minCapacity < 0) throw new OutOfCapacityException("capacity overflow occurred");
        // Current capacity >= minCapacity -> we don't need to increment current length
        if (minCapacity - elements.length <= 0) return;

        int newCapacity = elements.length * 2;
        if (newCapacity <= 0)
            newCapacity = MAX_ARRAY_SIZE;
        Object[] increasedCapacityArray = new Object[newCapacity];
        System.arraycopy(elements, 0, increasedCapacityArray, 0, elements.length);
        elements = increasedCapacityArray;
    }
}
