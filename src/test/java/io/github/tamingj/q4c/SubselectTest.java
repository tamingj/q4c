package io.github.tamingj.q4c;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static io.github.tamingj.q4c.Queries.selectFrom;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class SubselectTest {

    private UniSelect<Integer> oddNumbersSelect;
    private List<Integer> result;

    @Test
    void subselectOfFilteredSelect() {
        givenIsAQueryOfOddOnesFromNumbersFrom0To10();

        whenNumbersGt5QueriedFromGivenQuery();

        thenOddNumbersGt5AreReturned();
    }

    private void thenOddNumbersGt5AreReturned() {
        assertThat(result).containsExactly(7, 9);
    }

    private void whenNumbersGt5QueriedFromGivenQuery() {
        result = selectFrom(oddNumbersSelect).where((n) -> n > 5).toList();
    }

    private void givenIsAQueryOfOddOnesFromNumbersFrom0To10() {
        var allNumbers = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        oddNumbersSelect = selectFrom(allNumbers).where((n) -> n % 2 != 0);
    }

}
