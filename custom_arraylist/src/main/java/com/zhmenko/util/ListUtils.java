package com.zhmenko.util;

import com.zhmenko.customlist.CustomArrayList;

import java.util.Comparator;

/**
 * Util class containing various methods for CustomArrayList class
 *
 * @see com.zhmenko.customlist.CustomArrayList
 */
public class ListUtils {
    private ListUtils() {
    }

    /**
     * QuickSort implementation for CustomArrayList
     *
     * @param list the list to be sorted
     * @param <T>  the type of elements in the list, must implements Comparable interface
     */
    public static <T extends Comparable<? super T>> void sort(final CustomArrayList<T> list) {
        quickSort(list, 0, list.getSize() - 1);
    }

    /**
     * QuickSort implementation for CustomArrayList
     *
     * @param list       the list to be sorted
     * @param comparator the comparator to use for sorting
     * @param <T>        the type of elements in the list
     */
    public static <T> void sort(final CustomArrayList<T> list, final Comparator<T> comparator) {
        quickSort(list, comparator, 0, list.getSize() - 1);
    }

    private static <T extends Comparable<? super T>> void quickSort(final CustomArrayList<T> list, final int low, final int high) {
        if (low < high) {
            int pivot = partition(list, low, high);

            quickSort(list, low, pivot - 1);
            quickSort(list, pivot + 1, high);
        }
    }

    private static <T> void quickSort(final CustomArrayList<T> list, final Comparator<T> comparator,
                                      final int low, final int high) {
        if (low < high) {
            int pivot = partition(list, comparator, low, high);

            quickSort(list, comparator, low, pivot - 1);
            quickSort(list, comparator, pivot + 1, high);
        }
    }

    private static <T extends Comparable<? super T>> int partition(final CustomArrayList<T> list, final int low, final int high) {
        T pivot = list.get(high);
        int wall = (low - 1);
        for (int j = low; j < high; j++) {
            if (list.get(j).compareTo(pivot) < 0) {
                wall++;
                swap(list, wall, j);
            }
        }
        swap(list, wall + 1, high);
        return wall + 1;
    }


    private static <T> int partition(final CustomArrayList<T> list, final Comparator<T> comparator,
                                     final int low, final int high) {
        T pivot = list.get(high);
        int wall = (low - 1);
        for (int j = low; j < high; j++) {
            if (comparator.compare(list.get(j), pivot) < 0) {
                wall++;
                swap(list, wall, j);
            }
        }
        swap(list, wall + 1, high);
        return wall + 1;
    }

    /**
     * Swap elements of a list in the given indexes
     *
     * @param list            list in which we want swap elements
     * @param firstElemIndex  to swap
     * @param secondElemIndex to swap
     * @param <T>             list element type
     */
    private static <T> void swap(final CustomArrayList<T> list, final int firstElemIndex, final int secondElemIndex) {
        T temp = list.get(firstElemIndex);
        list.set(firstElemIndex, list.get(secondElemIndex));
        list.set(secondElemIndex, temp);
    }
}
