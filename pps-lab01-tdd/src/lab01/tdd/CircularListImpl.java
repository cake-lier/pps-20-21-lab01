package lab01.tdd;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CircularListImpl implements CircularList {
    private final List<Integer> internalList;
    private int currentPosition;

    public CircularListImpl() {
        internalList = new ArrayList<>();
        currentPosition = -1;
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
        currentPosition++;
        if (currentPosition == internalList.size()) {
            currentPosition = 0;
        }
        return Optional.of(internalList.get(currentPosition));
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
