package io.github.cbuschka.q4c;

import io.github.cbuschka.q4c.impl.JoinIterator;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;

class FullOuterJoinsTest {
    private List<String> left;
    private List<String> right;
    private List<String> result;

    @Test
    void bothEmptyProducesEmptyResult() {
        givenLeft();
        givenRight();

        whenFullOuterJoined();

        thenResultIsEmpty();
    }

    @Test
    void disjunctSets() {
        givenLeft("a0", "a1");
        givenRight("b0", "b1");

        whenFullOuterJoined();

        thenResultIs("a0:null", "a1:null", "null:b0", "null:b1");
    }

    @Test
    void twoPairs() {
        givenLeft("a1", "b1");
        givenRight("a2", "b2");

        whenFullOuterJoined();

        thenResultIs("a1:a2", "b1:b2");
    }

    @Test
    void twoWithThree() {
        givenLeft("a1", "a2", "b", "c");
        givenRight("a2", "a1", "d", "a3", "e");

        whenFullOuterJoined();

        thenResultIs("a1:a2", "a1:a1", "a1:a3", "a2:a2", "a2:a1", "a2:a3", "b:null", "c:null", "null:d", "null:e");
    }

    @Test
    void leftEmpty() {
        givenLeft();
        givenRight("a1");

        whenFullOuterJoined();

        thenResultIs("null:a1");
    }

    @Test
    void rightEmpty() {
        givenLeft("a1");
        givenRight();

        whenFullOuterJoined();

        thenResultIs("a1:null");
    }

    private void thenResultIs(String... expectedElements) {
        assertThat(result).containsExactly(expectedElements);
    }

    private void thenResultIsEmpty() {
        assertThat(result).isEmpty();
    }

    private void whenFullOuterJoined() {
        this.result = toList(JoinIterator.forFullOuterJoin(left, s -> s.charAt(0), right, s1 -> s1.charAt(0), Pair::of));
    }

    private List<String> toList(Iterable<Pair<String, String>> tuples) {
        return StreamSupport.stream(tuples.spliterator(), false)
                .map(t -> t.element1() + ":" + t.element2())
                .collect(Collectors.toList());
    }

    private void givenLeft(String... left) {
        this.left = Arrays.asList(left);
    }

    private void givenRight(String... right) {
        this.right = Arrays.asList(right);
    }
}
