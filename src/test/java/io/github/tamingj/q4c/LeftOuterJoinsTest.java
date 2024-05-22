package io.github.tamingj.q4c;

import java.util.stream.Collectors;

import io.github.tamingj.q4c.impl.JoinIterator;
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
    void twoWithThreeAndFiltered() {
        givenLeft("a1", "a2", "b", "c");
        givenRight("a2", "a1", "d", "a3", "e");

        whenLeftOuterJoinedAndFilteredForEvenSum();

        thenResultIs("a1:a1", "a1:a3", "a2:a2", "b:null", "c:null");
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
        this.result = toList(JoinIterator.forLeftOuterJoin(left, s -> s.charAt(0), right, s1 -> s1.charAt(0), BiPredicates.matchAll(), Pair::of));
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


    private void whenLeftOuterJoinedAndFilteredForEvenSum() {
        this.result = toList(JoinIterator.forLeftOuterJoin(left, s -> s.charAt(0), right, s1 -> s1.charAt(0), LeftOuterJoinsTest::hasEvenSum, Pair::of));
    }

    private static boolean hasEvenSum(String a, String b) {
        return a == null || b == null || (a.length() >= 2 && b.length() >= 2 && (Integer.parseInt(a.substring(1, 2)) + Integer.parseInt(b.substring(1, 2))) % 2 == 0);
    }

}
