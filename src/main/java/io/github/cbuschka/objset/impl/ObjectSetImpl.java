package io.github.cbuschka.objset.impl;

import io.github.cbuschka.objset.FilterableUniSelect;
import io.github.cbuschka.objset.ObjectSet;
import lombok.NoArgsConstructor;

import java.util.*;

@NoArgsConstructor
public class ObjectSetImpl implements ObjectSet {
    private final Map<Class<?>, List<?>> elementsByType = new HashMap<>();

    public <Element> ObjectSetImpl with(Class<Element> type, Iterable<Element> elements) {
        addAll(type, elements);
        return this;
    }

    public <Element> void addAll(Class<Element> type, Iterable<Element> elements) {
        elements.forEach((e) -> addElement(type, e));
    }

    private <Element> void addElement(Class<Element> type, Element element) {
        @SuppressWarnings("unchecked")
        List<Element> elements = (List<Element>) elementsByType.computeIfAbsent(type, k -> new ArrayList<Element>());
        elements.add(element);
    }

    public <Element1> FilterableUniSelect<Element1> select(Class<Element1> type) {
        return new UniSelectImpl<>(this, getElementsFor(type));
    }

    <Element1> List<Element1> getElementsFor(Class<Element1> type) {
        @SuppressWarnings("unchecked")
        List<Element1> elements = (List<Element1>) elementsByType.get(type);
        if (elements == null) {
            throw new NoSuchElementException("No elements for %s.".formatted(type.getName()));
        }
        return elements;
    }
}
