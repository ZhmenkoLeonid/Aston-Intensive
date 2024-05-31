package com.zhmenko.util;

import com.zhmenko.customlist.CustomArrayList;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ListUtilsTest {

    static Stream<Arguments> inputData() {
        return Stream.of(
                Arguments.of((new Integer[]{2, 1, 2, 3, 4}), new Integer[]{1, 2, 2, 3, 4}),
                Arguments.of((new Integer[]{2, 2, 0}), new Integer[]{0, 2, 2}),
                Arguments.of((new Integer[]{1, 3, 5}), new Integer[]{1, 3, 5}),
                Arguments.of((new Integer[]{}), new Integer[]{})
        );
    }

    @ParameterizedTest(name = "{index} => array = {0},  expected = {1} ")
    @MethodSource("inputData")
    void sort(final Integer[] input, final Integer[] expected) {
        // given
        CustomArrayList<Integer> list = new CustomArrayList<>();
        list.addAll(input);
        // when
        ListUtils.sort(list);
        // then
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], list.get(i));
        }
    }

    @RepeatedTest(value = 10)
    void randomDataSort() {
        //given
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        int size = rnd.nextInt(100000);
        Integer[] expected = new Integer[size];
        CustomArrayList<Integer> arrayList = new CustomArrayList<>(size);
        for (int i = 0; i < expected.length; i++) {
            expected[i] = rnd.nextInt();
        }
        arrayList.addAll(expected.clone());
        // Using working sort to check
        Arrays.sort(expected);
        //when
        ListUtils.sort(arrayList);
        // then
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], arrayList.get(i));
        }
    }
}