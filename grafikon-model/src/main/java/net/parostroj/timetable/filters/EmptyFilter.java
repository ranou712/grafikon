package net.parostroj.timetable.filters;

/**
 * Empty filter.
 *
 * @author jub
 */
public class EmptyFilter<T> implements ExtractionFilter<T, T> {

    public EmptyFilter() {
    }

    @Override
    public boolean is(T item) {
        return true;
    }

    @Override
    public T get(T item) {
        return item;
    }
}
