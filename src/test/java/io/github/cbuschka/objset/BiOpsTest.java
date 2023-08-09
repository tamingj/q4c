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
public class BiOpsTest {
    @Mock
    private Person person1;
    @Mock
    private Person person2;
    @Mock
    private Address address1;
    @Mock
    private Address address2;

    @Test
    void joinTwoEntities() {
        when(person1.getId()).thenReturn(1L);
        when(person2.getId()).thenReturn(2L);
        when(address1.getPersonId()).thenReturn(1L);
        when(address2.getPersonId()).thenReturn(1L);

        List<Tuple<Person, Address>> result = ObjectSet.of(Person.class, List.of(person1, person2))
                .with(Address.class, List.of(address1, address2))
                .select(Person.class)
                .join(Address.class, Person::getId, Address::getPersonId)
                .toList();

        assertThat(result).containsExactly(Tuple.of(person1, address1), Tuple.of(person1, address2));
    }

    @Test
    void leftOuterJoinTwoEntities() {
        when(person1.getId()).thenReturn(1L);
        when(person2.getId()).thenReturn(2L);
        when(address1.getPersonId()).thenReturn(1L);
        when(address2.getPersonId()).thenReturn(1L);

        List<Tuple<Person, Address>> result = ObjectSet.of(Person.class, List.of(person1, person2))
                .with(Address.class, List.of(address1, address2))
                .select(Person.class)
                .leftOuterJoin(Address.class, Person::getId, Address::getPersonId)
                .toList();

        assertThat(result).containsExactly(Tuple.of(person1, address1), Tuple.of(person1, address2),
                Tuple.of(person2, null));
    }

    @Test
    void filteredOfTwoEntities() {
        when(person1.getId()).thenReturn(1L);
        when(person2.getId()).thenReturn(2L);
        when(address1.getPersonId()).thenReturn(1L);
        when(address2.getPersonId()).thenReturn(1L);

        List<Tuple<Person, Address>> result = ObjectSet.of(Person.class, List.of(person1, person2))
                .with(Address.class, List.of(address1, address2))
                .select(Person.class)
                .leftOuterJoin(Address.class, Person::getId, Address::getPersonId)
                .where((i, s) -> s != address2)
                .toList();

        assertThat(result).containsExactly(Tuple.of(person1, address1), Tuple.of(person2, null));
    }
}
