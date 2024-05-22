package io.github.tamingj.q4c;

import java.util.stream.Collectors;

import io.github.tamingj.q4c.impl.JoinIterator;
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

    @Test
    void twoWithThreeFiltered() {
        givenLeft("b", "a1", "a2", "c");
        givenRight("a2", "a1", "d", "a3", "e");

        whenInnerJoinedAndFilteredForEvenSum();

        thenResultIs("a1:a1", "a1:a3", "a2:a2");
    }

    private void whenInnerJoinedAndFilteredForEvenSum() {
        this.result = toList(JoinIterator.forInnerJoin(left, s -> s.charAt(0), right, s1 -> s1.charAt(0), InnerJoinsTest::hasEvenSum, Pair::of));
    }

    private static boolean hasEvenSum(String a, String b) {
        return (Integer.parseInt(a.substring(1, 2)) + Integer.parseInt(b.substring(1, 2))) % 2 == 0;
    }

    private void thenResultIs(String... expectedElements) {
        assertThat(result).containsExactly(expectedElements);
    }

    private void thenResultIsEmpty() {
        assertThat(result).isEmpty();
    }

    private void whenInnerJoined() {
        this.result = toList(JoinIterator.forInnerJoin(left, s -> s.charAt(0), right, s1 -> s1.charAt(0), BiPredicates.matchAll(), Pair::of));
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
