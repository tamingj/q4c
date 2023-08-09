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
public class TriOpsTest {
    @Mock
    private Person person1;
    @Mock
    private Person person2;
    @Mock
    private Address address1;
    @Mock
    private Address address2;
    @Mock
    private Order order1;
    @Mock
    private Item item1;

    @Test
    void joinThreeEntities() {
        when(person1.getId()).thenReturn(1L);
        when(person2.getId()).thenReturn(2L);
        when(address1.getPersonId()).thenReturn(1L);
        when(address2.getPersonId()).thenReturn(1L);
        when(order1.getPersonId()).thenReturn(1L);

        List<Triple<Person, Address, Order>> result = ObjectSet.of(Person.class, List.of(person1, person2))
                .with(Address.class, List.of(address1, address2))
                .with(Order.class, List.of(order1))
                .select(Person.class)
                .join(Address.class, Person::getId, Address::getPersonId)
                .join(Order.class, (i, s) -> i.getId(), Order::getPersonId)
                .toList();


        assertThat(result).containsExactly(Triple.of(person1, address1, order1),
                Triple.of(person1, address2, order1));
    }

    @Test
    void filteredOfThreeEntities() {
        when(person1.getId()).thenReturn(1L);
        when(person2.getId()).thenReturn(2L);
        when(address1.getPersonId()).thenReturn(1L);
        when(address2.getPersonId()).thenReturn(1L);
        when(order1.getPersonId()).thenReturn(1L);

        List<Triple<Person, Address, Order>> result = ObjectSet.of(Person.class, List.of(person1, person2))
                .with(Address.class, List.of(address1, address2))
                .with(Order.class, List.of(order1))
                .select(Person.class)
                .join(Address.class, Person::getId, Address::getPersonId)
                .join(Order.class, (i, s) -> i.getId(), Order::getPersonId)
                .where((i, s, ss) -> s != address2)
                .toList();

        assertThat(result).containsExactly(Triple.of(person1, address1, order1));
    }
}
