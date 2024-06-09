# ASTON Homework 1

## Задачи:

- Реализовать свой ArrayList (не потокобезопасный)
- Реализовать алгоритм quicksort для реализованной вами реализации ArrayList.
- Все классы и интерфейсы должны быть задокументированы на уровне класса (class-level javadoc)
- Все публичные методы ваших реализаций должны содержать javadoc
- Документация должна быть в полном объёме и представлять исчерпывающее и интуитивно понятное руководство пользования
  вашим кодом для другого разработчика
- Покрыть код unit-тестами (использовать junit 5, в тестах использовать структуру given-when-then).

## Реализация:

- Кастомная реализация
  ArrayList - [com.zhmenko.customlist.CustomArrayList](./src/main/java/com/zhmenko/customlist/CustomArrayList.java) ([Тесты](./src/test/java/com/zhmenko/customlist/CustomArrayListTest.java))
- Реализация алгоритма Quicksort для
  CustomArrayList - [com.zhmenko.util.ListUtils](./src/main/java/com/zhmenko/util/ListUtils.java) ([Тесты](./src/test/java/com/zhmenko/util/ListUtilsTest.java))

## Инструменты

- Java 11
- Maven
- JUnit 5
- JaCoCo