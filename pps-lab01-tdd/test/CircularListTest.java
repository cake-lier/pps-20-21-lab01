import lab01.tdd.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
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
    public static final int RANGE_START_EVEN_MULTIPLES = -1;
    public static final int RANGE_END_EVEN_MULTIPLES = 5;
    public static final int CHOSEN_DIVISOR = 3;
    public static final int RANGE_START_DIVISOR_MULTIPLES = -2;
    public static final int RANGE_END_DIVISOR_MULTIPLES = 7;

    public CircularList list;

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

    private void createListOfTwoElements() {
        list.add(FIRST_ELEMENT);
        list.add(SECOND_ELEMENT);
    }

    @Test
    void testGetNextElement() {
        this.createListOfTwoElements();
        list.next();
        Assertions.assertEquals(Optional.of(SECOND_ELEMENT), list.next());
    }

    @Test
    void testNextWrapsAfterLastElement() {
        this.createListOfTwoElements();
        list.next();
        list.next();
        Assertions.assertEquals(Optional.of(FIRST_ELEMENT), list.next());
    }

    @Test
    void testGetPreviousElement() {
        this.createListOfTwoElements();
        list.next();
        Assertions.assertEquals(Optional.of(FIRST_ELEMENT), list.previous());
    }

    @Test
    void testPreviousWrapsBeforeFirstElement() {
        this.createListOfTwoElements();
        Assertions.assertEquals(Optional.of(SECOND_ELEMENT), list.previous());
    }

    @Test
    void testResetPositionAfterNext() {
        this.createListOfTwoElements();
        list.next();
        list.reset();
        Assertions.assertEquals(Optional.of(FIRST_ELEMENT), list.next());
    }

    @Test
    void testResetPositionAfterPrevious() {
        this.createListOfTwoElements();
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

    private List<Integer> generateSatisfyingElements(final int endRange, final int divisor) {
        return IntStream.iterate(FIRST_SATISFYING_ELEMENT, i -> (i + divisor) % ((endRange / divisor + 1) * divisor))
                        .limit(NUMBER_OF_SATISFYING_ELEMENTS)
                        .boxed()
                        .collect(Collectors.toList());
    }

    @Test
    void testNextWithEvenStrategy() {
        IntStream.range(RANGE_START_EVEN_MULTIPLES, RANGE_END_EVEN_MULTIPLES).forEach(list::add);
        Assertions.assertEquals(generateSatisfyingElements(RANGE_END_EVEN_MULTIPLES, 2),
                                getSatisfyingElementsFromList(new EvenStrategy()));
    }

    @Test
    void testNextWithMultipleOfStrategy() {
        IntStream.range(RANGE_START_DIVISOR_MULTIPLES, RANGE_END_DIVISOR_MULTIPLES).forEach(list::add);
        Assertions.assertEquals(generateSatisfyingElements(RANGE_END_DIVISOR_MULTIPLES, CHOSEN_DIVISOR),
                                getSatisfyingElementsFromList(new MultipleOfStrategy(CHOSEN_DIVISOR)));
    }

    @Test
    void testNextWithEqualsStrategy() {
        IntStream.iterate(1, i -> 1 - i).limit(6).forEach(list::add);
        Assertions.assertEquals(IntStream.generate(() -> 0).limit(5).boxed().collect(Collectors.toList()),
                                getSatisfyingElementsFromList(new EqualsStrategy(0)));
    }
}
