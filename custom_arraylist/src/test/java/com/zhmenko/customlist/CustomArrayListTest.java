package com.zhmenko.customlist;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class CustomArrayListTest {
    private CustomArrayList<String> list;

    @BeforeEach
    void setupThis() {
        list = new CustomArrayList<>();
    }

    @ParameterizedTest(name = "{index} - <{0}>")
    @ValueSource(strings = {"World", "", " "})
    @NullSource
    void givenEmptyList_WhenAddElement_ThenSizeShouldIncreaseAndListChangedCorrectly(final String word) {
        //given

        //when
        list.add("Hello");
        list.add(word);
        //then
        assertEquals(2, list.getSize());
        assertEquals("Hello", list.get(0));
        assertEquals(word, list.get(1));
    }

    @Test
    void givenDataFilledList_WhenRemoveElementByObject_ThenSizeShouldDecreaseAndListChangedCorrectly() {
        // given
        list.add("Hello");
        list.add("World");
        // when
        list.remove("Hello");
        // then
        assertEquals(1, list.getSize());
        assertEquals("World", list.get(0));
    }

    @ParameterizedTest(name = "{index} - array size - <{0}> elements")
    @ValueSource(ints = {0, 1, 2, 3, 4, 5})
    void givenDataFilledList_WhenGetSize_ThenShouldReturnCorrectSize(final int size) {
        // given
        for (int i = 0; i < size; i++) {
            list.add("just data");
        }
        // when
        int actualSize = list.getSize();
        // then
        assertEquals(size, actualSize);
    }

    @Test
    void givenEmptyList_WhenCallIsEmptyMethod_ThenShouldReturnTrue() {
        // given
        // when
        boolean isEmpty = list.isEmpty();
        // then
        assertTrue(isEmpty);
    }

    @Test
    void givenNonEmptyList_WhenCallIsEmptyMethod_ThenShouldReturnFalse() {
        // given
        list.add("rlly important data");
        // when
        boolean isEmpty = list.isEmpty();
        // then
        assertFalse(isEmpty);
    }

    @Test
    void givenDataFilledList_WhenCallIsContainMethodOnExistingElement_ThenShouldReturnTrue() {
        // given
        list.add("another data");
        list.add("I'm exist");
        list.add("another data");
        // when
        boolean isContains = list.isContain("I'm exist");
        // then
        assertTrue(isContains);
    }

    @Test
    void givenDataFilledList_WhenCallIsContainMethodOnNotExistingElement_ThenShouldReturnFalse() {
        // given
        list.add("another data");
        list.add("I'm exist");
        list.add("another data");
        // when
        boolean isContains = list.isContain("Apple");
        // then
        assertFalse(isContains);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 5, 10, 15, 200, 10000, 200000})
    void givenEmptyList_WhenAddElements_ThenSizeShouldIncreaseAndListChangedCorrectly(int size) {
        // given
        String dummy = "dummy";
        // when
        for (int i = 0; i < size; i++) {
            list.add(dummy);
        }
        // then
        assertEquals(size, list.getSize());
        for (int i = 0; i < size; i++) {
            assertEquals(dummy, list.get(i));
        }
    }

    @Test
    void givenDataFilledList_WhenAddElementByIndex_ThenSizeShouldIncreaseAndListChangedCorrectly() {
        // given
        list.add("Hello");
        list.add("World");
        // when
        list.add(1, "my");
        // then
        assertEquals(3, list.getSize());
        assertEquals("Hello", list.get(0));
        assertEquals("my", list.get(1));
        assertEquals("World", list.get(2));
    }

    @ParameterizedTest(name = "{index} - <{0}>")
    @ValueSource(ints = {-1, 3, Integer.MAX_VALUE, Integer.MIN_VALUE})
    void givenDataFilledList_WhenAddElementByWrongIndex_ThenShouldThrowException(final int index) {
        // given
        list.add("Hello");
        list.add("World");
        // when
        Executable action = () -> list.add(index, "my");
        // then
        assertThrows(IndexOutOfBoundsException.class, action);
    }

    @Test
    void givenDataFilledList_WhenAddElementsArray_ThenSizeShouldIncreaseAndListChangedCorrectly() {
        // given
        list.add("Hello");
        list.add(", World");
        // when
        list.addAll(new String[]{", my", " dear", " world"});
        // then
        assertEquals(5, list.getSize());
        assertEquals("Hello", list.get(0));
        assertEquals(", World", list.get(1));
        assertEquals(", my", list.get(2));
        assertEquals(" dear", list.get(3));
        assertEquals(" world", list.get(4));
    }

    @Test
    void givenDataFilledList_WhenAddElementsArrayByIndex_ThenSizeShouldIncreaseAndListChangedCorrectly() {
        // given
        list.add("Hello");
        list.add(", World");
        // when
        list.addAll(1, new String[]{"1", "2", "3"});
        // then
        assertEquals(5, list.getSize());
        assertEquals("Hello", list.get(0));
        assertEquals("1", list.get(1));
        assertEquals("2", list.get(2));
        assertEquals("3", list.get(3));
        assertEquals(", World", list.get(4));
    }


    @ParameterizedTest(name = "{index} - <{0}>")
    @ValueSource(ints = {-1, 3, Integer.MAX_VALUE, Integer.MIN_VALUE})
    void givenDataFilledList_WhenAddElementsArrayByWrongIndex_ThenShouldThrowException(final int index) {
        // given
        list.add("Hello");
        list.add(", World");
        // when
        Executable action = () -> list.addAll(index, new String[]{", my", " dear", " world"});
        // then
        assertThrows(IndexOutOfBoundsException.class, action);
    }

    @Test
    void givenDataFilledList_WhenRemoveExistingElement_ThenSizeShouldDecreaseAndListChangedCorrectlyAndReturnTrue() {
        // given
        list.add("Hello");
        list.add(", World");
        // when
        boolean result = list.remove("Hello");
        // then
        assertTrue(result);
        assertEquals(1, list.getSize());
        assertEquals(", World", list.get(0));
    }

    @Test
    void givenDataFilledList_WhenRemoveNotExistingElement_ThenSizeAndListShouldntChangedAndReturnFalse() {
        // given
        list.add("Hello");
        list.add(", World");
        // when
        boolean result = list.remove("another string");
        // then
        assertFalse(result);
        assertEquals(2, list.getSize());
        assertEquals("Hello", list.get(0));
        assertEquals(", World", list.get(1));
    }

    @Test
    void givenDataFilledList_WhenRemoveElementByIndex_ThenSizeShouldDecreaseAndListChangedCorrectly() {
        // given
        list.add("Hello");
        list.add(", World");
        // when
        list.remove(0);
        // then
        assertEquals(1, list.getSize());
        assertEquals(", World", list.get(0));
    }

    @ParameterizedTest(name = "{index} - <{0}>")
    @ValueSource(ints = {-1, 2, Integer.MAX_VALUE, Integer.MIN_VALUE})
    void givenDataFilledList_WhenRemoveElementByWrongIndex_ThenShouldThrowException(final int index) {
        // given
        list.add("Hello");
        list.add(", World");
        // when
        Executable action = () -> list.remove(index);
        // then
        assertThrows(IndexOutOfBoundsException.class, action);
    }

    @Test
    void givenDataFilledList_WhenGetElementByIndex_ThenShouldReturnCorrectElement() {
        // given
        list.add("Hello");
        list.add(", World");
        // when
        String elem = list.get(0);
        // then
        assertEquals("Hello", elem);
    }

    @ParameterizedTest(name = "{index} - <{0}>")
    @ValueSource(strings = {"Hello"})
    @NullAndEmptySource
    void givenDataFilledList_WhenCallIndexOf_ThenShouldReturnIndexOfFirstElementEntry(String word) {
        // given
        list.add(", World");
        list.add(word);
        list.add(", World");
        list.add(word);
        // when
        int helloIndex = list.indexOf(word);
        // then
        assertEquals(1, helloIndex);
    }

    @Test
    void givenDataFilledList_WhenCallIndexOfOfNonExistingElement_ThenShouldReturnIntErrorCode() {
        // given
        list.add(", World");
        list.add("Hello");
        list.add(", World");
        list.add("Hello");
        // when
        int helloIndex = list.indexOf("Hello, World");
        // then
        assertEquals(-1, helloIndex);
    }

    @Test
    void givenDataFilledList_WhenClearList_ThenSizeShouldBeZeroAndListEmpty() {
        // given
        list.add("Hello");
        list.add(", World");
        // when
        list.clear();
        // then
        assertEquals(0, list.getSize());
        assertTrue(list.isEmpty());
    }

    @Test
    void givenDataFilledList_WhenCallToString_ThenShouldReturnStringRepresentation() {
        // given
        list.add("Hello");
        list.add(", World");
        // when
        String actual = list.toString();
        // then
        assertEquals("[Hello, , World]", actual);
    }

    @Test
    void givenDataFilledList_WhenSetElement_ThenShouldCorrectListChange() {
        // given
        list.add("Hello");
        list.add(", World");
        // when
        list.set(0, "Bye");
        // then
        assertEquals(2, list.getSize());
        assertEquals("Bye", list.get(0));
    }

    @ParameterizedTest(name = "{index} - <{0}>")
    @ValueSource(ints = {-1, 2, Integer.MAX_VALUE, Integer.MIN_VALUE})
    void givenDataFilledList_WhenSetElementByWrongIndex_ThenShouldThrowException(final int index) {
        // given
        list.add("Hello");
        list.add(", World");
        // when
        Executable action = () -> list.set(index, "I want to be in this list, please!");
        // then
        assertThrows(IndexOutOfBoundsException.class, action);
    }
}