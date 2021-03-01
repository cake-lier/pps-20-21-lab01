package lab01.tdd;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CircularListImpl implements CircularList {
    private static final int FIRST_POSITION = 0;

    private final List<Integer> internalList;
    private int currentPosition;

    public CircularListImpl() {
        internalList = new ArrayList<>();
        currentPosition = FIRST_POSITION;
    }

    @Override
    public void add(final int element) {
        internalList.add(element);
    }

    @Override
    public int size() {
        return internalList.size();
    }

    @Override
    public boolean isEmpty() {
        return internalList.isEmpty();
    }

    @Override
    public Optional<Integer> next() {
        if (this.isEmpty()) {
            return Optional.empty();
        }
        final var currentElement = internalList.get(currentPosition);
        currentPosition++;
        if (currentPosition == internalList.size()) {
            currentPosition = FIRST_POSITION;
        }
        return Optional.of(currentElement);
    }

    @Override
    public Optional<Integer> previous() {
        if (this.isEmpty()) {
            return Optional.empty();
        }
        currentPosition--;
        if (currentPosition == FIRST_POSITION - 1) {
            currentPosition = internalList.size() - 1;
        }
        return Optional.of(internalList.get(currentPosition));
    }

    @Override
    public void reset() {
        this.currentPosition = FIRST_POSITION;
    }

    @Override
    public Optional<Integer> next(SelectStrategy strategy) {
        return Optional.empty();
    }
}
