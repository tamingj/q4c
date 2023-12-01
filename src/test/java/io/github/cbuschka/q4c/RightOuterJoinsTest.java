package io.github.cbuschka.q4c;

import io.github.cbuschka.q4c.impl.JoinIterator;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;

class RightOuterJoinsTest {
    private List<String> left;
    private List<String> right;
    private List<String> result;

    @Test
    void bothEmptyProducesEmptyResult() {
        givenLeft();
        givenRight();

        whenRightOuterJoined();

        thenResultIsEmpty();
    }

    @Test
    void disjunctSets() {
        givenLeft("a");
        givenRight("b");

        whenRightOuterJoined();

        thenResultIs("null:b");
    }

    @Test
    void twoPairs() {
        givenLeft("a1", "b1");
        givenRight("a2", "b2");

        whenRightOuterJoined();

        thenResultIs("a1:a2", "b1:b2");
    }

    @Test
    void twoWithThree() {
        givenLeft("a1", "a2", "b", "c");
        givenRight("a2", "a1", "d", "a3", "e");

        whenRightOuterJoined();

        thenResultIs("a1:a2", "a2:a2", "a1:a1", "a2:a1", "null:d", "a1:a3", "a2:a3", "null:e");
    }

    @Test
    void leftEmpty() {
        givenLeft();
        givenRight("a1");

        whenRightOuterJoined();

        thenResultIs("null:a1");
    }

    @Test
    void rightEmpty() {
        givenLeft("a1");
        givenRight();

        whenRightOuterJoined();

        thenResultIsEmpty();
    }

    private void thenResultIs(String... expectedElements) {
        assertThat(result).containsExactly(expectedElements);
    }

    private void thenResultIsEmpty() {
        assertThat(result).isEmpty();
    }

    private void whenRightOuterJoined() {
        this.result = toList(JoinIterator.forRightOuterJoin(left, s -> s.charAt(0), right, s1 -> s1.charAt(0), Pair::of));
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
