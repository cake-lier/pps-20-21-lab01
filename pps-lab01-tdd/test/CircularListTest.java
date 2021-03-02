import lab01.tdd.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.function.IntUnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * The test suite for testing the CircularList implementation
 */
public class CircularListTest {
    private static final int FIRST_ELEMENT = 0;
    private static final int SECOND_ELEMENT = 1;
    private static final int NUMBER_OF_SATISFYING_ELEMENTS = 5;
    public static final int FIRST_SATISFYING_ELEMENT = 0;
    public static final int START_RANGE_EVEN_MULTIPLES = -1;
    public static final int END_RANGE_EVEN_MULTIPLES = 5;
    public static final int CHOSEN_DIVISOR = 3;
    public static final int START_RANGE_DIVISOR_MULTIPLES = -2;
    public static final int END_RANGE_DIVISOR_MULTIPLES = 7;
    public static final int START_SEQUENCE_EQUALS = 1;
    public static final int SIZE_SEQUENCE_EQUALS = 7;
    public static final int EQUALS_CHOSEN_VALUE = 0;

    private final SelectStrategyFactory selectStrategyFactory = new SelectStrategyFactoryImpl();
    private CircularList list;

    @BeforeEach
    void setUp() {
        list = new CircularListImpl();
    }

    @Test
    void testAddElement() {
        list.add(FIRST_ELEMENT);
        Assertions.assertFalse(list.isEmpty());
        Assertions.assertEquals(1, list.size());
        Assertions.assertEquals(Optional.of(FIRST_ELEMENT), list.next());
    }

    @Test
    void testListIsInitiallyEmpty() {
        Assertions.assertEquals(0, list.size());
        Assertions.assertTrue(list.isEmpty());
        Assertions.assertEquals(Optional.empty(), list.next());
        Assertions.assertEquals(Optional.empty(), list.previous());
    }

    private void addAllToList(final IntStream elementsToAdd) {
        elementsToAdd.forEach(list::add);
    }

    @Test
    void testGetNextElement() {
        addAllToList(IntStream.of(FIRST_ELEMENT, SECOND_ELEMENT));
        list.next();
        Assertions.assertEquals(Optional.of(SECOND_ELEMENT), list.next());
    }

    @Test
    void testNextWrapsAfterLastElement() {
        addAllToList(IntStream.of(FIRST_ELEMENT, SECOND_ELEMENT));
        list.next();
        list.next();
        Assertions.assertEquals(Optional.of(FIRST_ELEMENT), list.next());
    }

    @Test
    void testGetPreviousElement() {
        addAllToList(IntStream.of(FIRST_ELEMENT, SECOND_ELEMENT));
        list.next();
        Assertions.assertEquals(Optional.of(FIRST_ELEMENT), list.previous());
    }

    @Test
    void testPreviousWrapsBeforeFirstElement() {
        addAllToList(IntStream.of(FIRST_ELEMENT, SECOND_ELEMENT));
        Assertions.assertEquals(Optional.of(SECOND_ELEMENT), list.previous());
    }

    @Test
    void testResetPositionAfterNext() {
        addAllToList(IntStream.of(FIRST_ELEMENT, SECOND_ELEMENT));
        list.next();
        list.reset();
        Assertions.assertEquals(Optional.of(FIRST_ELEMENT), list.next());
    }

    @Test
    void testResetPositionAfterPrevious() {
        addAllToList(IntStream.of(FIRST_ELEMENT, SECOND_ELEMENT));
        list.previous();
        list.reset();
        Assertions.assertEquals(Optional.of(SECOND_ELEMENT), list.previous());
    }

    private List<Integer> getSatisfyingElementsFromList(final SelectStrategy strategy) {
        return IntStream.range(0, NUMBER_OF_SATISFYING_ELEMENTS)
                        .mapToObj(i -> list.next(strategy))
                        .map(Optional::get)
                        .collect(Collectors.toList());
    }

    private List<Integer> generateSatisfyingElements(final IntUnaryOperator generator) {
        return IntStream.iterate(FIRST_SATISFYING_ELEMENT, generator)
                        .limit(NUMBER_OF_SATISFYING_ELEMENTS)
                        .boxed()
                        .collect(Collectors.toList());
    }

    private List<Integer> generateRangeOfSatisfyingElements(final int endRange, final int divisor) {
        return generateSatisfyingElements(i -> (i + divisor) % ((endRange / divisor + 1) * divisor));
    }

    @Test
    void testNextWithEvenStrategy() {
        addAllToList(IntStream.range(START_RANGE_EVEN_MULTIPLES, END_RANGE_EVEN_MULTIPLES));
        Assertions.assertEquals(generateRangeOfSatisfyingElements(END_RANGE_EVEN_MULTIPLES, 2),
                                getSatisfyingElementsFromList(selectStrategyFactory.createEvenStrategy()));
    }

    @Test
    void testNextWithMultipleOfStrategy() {
        addAllToList(IntStream.range(START_RANGE_DIVISOR_MULTIPLES, END_RANGE_DIVISOR_MULTIPLES));
        Assertions.assertEquals(generateRangeOfSatisfyingElements(END_RANGE_DIVISOR_MULTIPLES, CHOSEN_DIVISOR),
                                getSatisfyingElementsFromList(selectStrategyFactory.createMultipleOfStrategy(CHOSEN_DIVISOR)));
    }

    @Test
    void testNextWithEqualsStrategy() {
        addAllToList(IntStream.iterate(START_SEQUENCE_EQUALS, i -> 1 - i).limit(SIZE_SEQUENCE_EQUALS));
        Assertions.assertEquals(generateSatisfyingElements(i -> FIRST_SATISFYING_ELEMENT),
                                getSatisfyingElementsFromList(selectStrategyFactory.createEqualsStrategy(EQUALS_CHOSEN_VALUE)));
    }
}
