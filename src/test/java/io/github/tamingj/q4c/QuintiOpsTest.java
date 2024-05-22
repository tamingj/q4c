package io.github.tamingj.q4c;

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
public class QuintiOpsTest {

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
    @Mock
    private Voucher voucher;

    private List<Quintuple<Person, Address, Order, Item, Voucher>> result;

    @Test
    void joinFiveEntities() {
        givenPerson1WithTwoAddressesAndOrderWithItemAndVoucher();
        givenPerson2WithoutAddressesAndOrderWithItemAndVoucher();

        whenQueried((persons, addresses, orders, items, vouchers) -> Queries.selectFrom(persons)
                .join(addresses).on(Person::getId, Address::getPersonId)
                .join(orders).on((person, address) -> person.getId(), Order::getPersonId)
                .join(items).on((person, address, order) -> order1.getItemId(), Item::getId)
                .join(vouchers).on((person, address, order, item) -> person.getId(), Voucher::getPersonId)
                .toList());

        assertThat(result).containsExactly(Quintuple.of(person1, address1, order1, item1, voucher),
                Quintuple.of(person1, address2, order1, item1, voucher));
    }

    private void whenQueried(QuintiFunction<Iterable<Person>, Iterable<Address>, Iterable<Order>, Iterable<Item>, Iterable<Voucher>, List<Quintuple<Person, Address, Order, Item, Voucher>>> func) {
        List<Person> persons = List.of(person1, person2);
        List<Address> addresses = List.of(address1, address2);
        List<Order> orders = List.of(order1);
        List<Item> items = List.of(item1);
        List<Voucher> vouchers = List.of(voucher);
        this.result = func.apply(persons, addresses, orders, items, vouchers);
    }

    private void givenPerson2WithoutAddressesAndOrderWithItemAndVoucher() {
        when(person2.getId()).thenReturn(2L);
    }

    private void givenPerson1WithTwoAddressesAndOrderWithItemAndVoucher() {
        when(person1.getId()).thenReturn(1L);
        when(address1.getPersonId()).thenReturn(1L);
        when(address2.getPersonId()).thenReturn(1L);
        when(order1.getPersonId()).thenReturn(1L);
        when(order1.getItemId()).thenReturn(22L);
        when(item1.getId()).thenReturn(22L);
        when(voucher.getPersonId()).thenReturn(1L);
    }

    @Test
    void filteredOfFiveEntities() {
        givenPerson1WithTwoAddressesAndOrderWithItemAndVoucher();
        givenPerson2WithoutAddressesAndOrderWithItemAndVoucher();

        whenQueried((persons, addresses, orders, items, vouchers) -> Queries.selectFrom(persons)
                .join(addresses).on(Person::getId, Address::getPersonId)
                .join(orders).on((person, address) -> person.getId(), Order::getPersonId)
                .join(items).on((person, address, order) -> order1.getItemId(), Item::getId)
                .join(vouchers).on((person, address, order, item) -> person.getId(), Voucher::getPersonId)
                .where((person, address, order, item, voucher) -> address != address2)
                .toList());

        assertThat(result).containsExactly(Quintuple.of(person1, address1, order1, item1, voucher));
    }
}
