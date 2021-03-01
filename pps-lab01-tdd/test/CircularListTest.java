import lab01.tdd.CircularList;
import lab01.tdd.CircularListImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

/**
 * The test suite for testing the CircularList implementation
 */
public class CircularListTest {
    private static final int FIRST_ELEMENT = 0;
    private static final int SECOND_ELEMENT = 1;

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

    private void createListOfTwoElements() {
        list.add(FIRST_ELEMENT);
        list.add(SECOND_ELEMENT);
    }
}
