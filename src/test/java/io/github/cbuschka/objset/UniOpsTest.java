package io.github.cbuschka.objset;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UniOpsTest {
    @Mock
    private Person person1;
    @Mock
    private Person person2;

    @Test
    void allOfMultipleEntities() {
        List<Person> result = ObjectSet.of(Person.class, List.of(person1, person2))
                .select(Person.class)
                .toList();

        assertThat(result).containsExactly(person1, person2);
    }

    @Test
    void filteredOfMultipleEntity() {
        when(person1.getName()).thenReturn("frank");
        when(person2.getName()).thenReturn("john");
        List<Person> result = ObjectSet.of(Person.class, List.of(person1, person2))
                .select(Person.class)
                .where(i -> i.getName().equals("john"))
                .toList();
        assertThat(result).containsExactly(person2);
    }
}
