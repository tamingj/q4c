package io.github.cbuschka.objset;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;

class InnerJoinsTest {
    private List<String> left;
    private List<String> right;
    private List<String> result;

    @Test
    void innerJoinBothEmptyProducesEmptyResult() {
        givenLeft();
        givenRight();

        whenInnerJoined();

        thenResultIsEmpty();
    }

    @Test
    void disjunctSets() {
        givenLeft("a");
        givenRight("b");

        whenInnerJoined();

        thenResultIsEmpty();
    }

    @Test
    void singlePair() {
        givenLeft("a1");
        givenRight("a2");

        whenInnerJoined();

        thenResultIs("a1:a2");
    }

    @Test
    void singlePairWithSingleExtraLeft() {
        givenLeft("a", "b1");
        givenRight("b2");

        whenInnerJoined();

        thenResultIs("b1:b2");
    }

    @Test
    void singlePairWithSingleExtraRight() {
        givenLeft("b1");
        givenRight("a", "b2");

        whenInnerJoined();

        thenResultIs("b1:b2");
    }

    @Test
    void twoPairs() {
        givenLeft("a1", "b1");
        givenRight("a2", "b2");

        whenInnerJoined();

        thenResultIs("a1:a2", "b1:b2");
    }

    @Test
    void twoWithThree() {
        givenLeft("b", "a1", "a2", "c");
        givenRight("a2", "a1", "d", "a3", "e");

        whenInnerJoined();

        thenResultIs("a1:a2", "a1:a1", "a1:a3", "a2:a2", "a2:a1", "a2:a3");
    }

    private void thenResultIs(String... expectedElements) {
        assertThat(result).containsExactly(expectedElements);
    }

    private void thenResultIsEmpty() {
        assertThat(result).isEmpty();
    }

    private void whenInnerJoined() {
        this.result = toList(Joins.innerJoin(left, s -> s.charAt(0), right, s -> s.charAt(0), Tuple::of));
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
