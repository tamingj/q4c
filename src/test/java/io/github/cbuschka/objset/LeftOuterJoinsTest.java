package io.github.cbuschka.objset;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;

class LeftOuterJoinsTest {
    private List<String> left;
    private List<String> right;
    private List<String> result;

    @Test
    void bothEmptyProducesEmptyResult() {
        givenLeft();
        givenRight();

        whenLeftOuterJoined();

        thenResultIsEmpty();
    }

    @Test
    void disjunctSets() {
        givenLeft("a");
        givenRight("b");

        whenLeftOuterJoined();

        thenResultIs("a:null");
    }

    @Test
    void twoPairs() {
        givenLeft("a1", "b1");
        givenRight("a2", "b2");

        whenLeftOuterJoined();

        thenResultIs("a1:a2", "b1:b2");
    }

    @Test
    void twoWithThree() {
        givenLeft("a1", "a2", "b", "c");
        givenRight("a2", "a1", "d", "a3", "e");

        whenLeftOuterJoined();

        thenResultIs("a1:a2", "a1:a1", "a1:a3", "a2:a2", "a2:a1", "a2:a3", "b:null", "c:null");
    }

    @Test
    void leftEmpty() {
        givenLeft();
        givenRight("a1");

        whenLeftOuterJoined();

        thenResultIsEmpty();
    }

    @Test
    void rightEmpty() {
        givenLeft("a1");
        givenRight();

        whenLeftOuterJoined();

        thenResultIs("a1:null");
    }

    private void thenResultIs(String... expectedElements) {
        assertThat(result).containsExactly(expectedElements);
    }

    private void thenResultIsEmpty() {
        assertThat(result).isEmpty();
    }

    private void whenLeftOuterJoined() {
        this.result = toList(Joins.leftOuterJoin(left, s -> s.charAt(0), right, s -> s.charAt(0), Tuple::of));
    }

    private List<String> toList(Iterable<Tuple<String, String>> tuples) {
        return StreamSupport.stream(tuples.spliterator(), false)
                .map(t -> t.element1() + ":" + t.element2())
                .toList();
    }

    private void givenLeft(String... left) {
        this.left = Arrays.asList(left);
    }

    private void givenRight(String... right) {
        this.right = Arrays.asList(right);
    }
}
