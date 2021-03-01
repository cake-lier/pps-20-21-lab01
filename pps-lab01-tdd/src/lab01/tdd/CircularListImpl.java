package lab01.tdd;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CircularListImpl implements CircularList {
    private final List<Integer> internalList;
    private int currentPosition;

    public CircularListImpl() {
        this.internalList = new ArrayList<>();
        this.currentPosition = -1;
    }

    @Override
    public void add(final int element) {
        this.internalList.add(element);
    }

    @Override
    public int size() {
        return this.internalList.size();
    }

    @Override
    public boolean isEmpty() {
        return this.internalList.isEmpty();
    }

    @Override
    public Optional<Integer> next() {
        this.currentPosition++;
        return Optional.of(this.internalList.get(this.currentPosition));
    }

    @Override
    public Optional<Integer> previous() {
        return Optional.empty();
    }

    @Override
    public void reset() {

    }

    @Override
    public Optional<Integer> next(SelectStrategy strategy) {
        return Optional.empty();
    }
}
