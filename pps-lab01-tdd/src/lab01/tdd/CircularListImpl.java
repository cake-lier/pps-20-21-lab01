package lab01.tdd;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class CircularListImpl implements CircularList {
    private static final int FIRST_POSITION = 0;

    private final List<Integer> internalList;
    private int currentPosition;

    public CircularListImpl() {
        internalList = new ArrayList<>();
        reset();
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
        return isEmpty() ? Optional.empty() : Optional.of(getElementAtPositionAndIncrement(currentPosition));
    }

    @Override
    public Optional<Integer> previous() {
        if (isEmpty()) {
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
        currentPosition = FIRST_POSITION;
    }

    @Override
    public Optional<Integer> next(final SelectStrategy strategy) {
        return IntStream.concat(IntStream.range(currentPosition, internalList.size()),
                                IntStream.range(FIRST_POSITION, currentPosition))
                        .filter(i -> strategy.apply(internalList.get(i)))
                        .boxed()
                        .findFirst()
                        .map(this::getElementAtPositionAndIncrement);
    }

    private int getElementAtPositionAndIncrement(final int position) {
        final var currentElement = internalList.get(position);
        currentPosition = position + 1;
        if (currentPosition == internalList.size()) {
            reset();
        }
        return currentElement;
    }
}
