package lab01.tdd;

public class SelectStrategyFactoryImpl implements SelectStrategyFactory {
    private final SelectStrategy evenStrategy = new MultipleOfStrategy(2);

    @Override
    public SelectStrategy createEvenStrategy() {
        return evenStrategy;
    }

    @Override
    public SelectStrategy createMultipleOfStrategy(int divisor) {
        return new MultipleOfStrategy(divisor);
    }

    @Override
    public SelectStrategy createEqualsStrategy(int value) {
        return e -> e == value;
    }

    private static class MultipleOfStrategy implements SelectStrategy {
        private final int divisor;

        private MultipleOfStrategy(final int divisor) {
            this.divisor = divisor;
        }

        @Override
        public boolean apply(final int element) {
            return element % divisor == 0;
        }
    }
}
