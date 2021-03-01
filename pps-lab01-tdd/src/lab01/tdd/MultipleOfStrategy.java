package lab01.tdd;

public class MultipleOfStrategy implements SelectStrategy {
    private final int divisor;

    public MultipleOfStrategy(final int divisor) {
        this.divisor = divisor;
    }

    @Override
    public boolean apply(final int element) {
        return element % divisor == 0;
    }
}
