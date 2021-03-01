package lab01.tdd;

public class EqualsStrategy implements SelectStrategy {
    private final int value;

    public EqualsStrategy(final int value) {
        this.value = value;
    }

    @Override
    public boolean apply(final int element) {
        return element == value;
    }
}
