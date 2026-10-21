package edu.course.lab03;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class NoDiscountTest {

    private static final double EPS = 1e-9;

    @Test
    void returnsSamePrice() {
        NoDiscount policy = new NoDiscount();
        assertEquals(100.0, policy.apply(100.0), EPS);
    }

    @Test
    void returnsZeroForZeroPrice() {
        NoDiscount policy = new NoDiscount();
        assertEquals(0.0, policy.apply(0.0), EPS);
    }

    @Test
    void rejectsNegativePrice() {
        NoDiscount policy = new NoDiscount();
        assertThrows(IllegalArgumentException.class, () -> policy.apply(-1.0));
    }

    @Test
    void rejectsNonFinitePrice() {
        NoDiscount policy = new NoDiscount();
        assertThrows(IllegalArgumentException.class, () -> policy.apply(Double.NaN));
        assertThrows(IllegalArgumentException.class, () -> policy.apply(Double.POSITIVE_INFINITY));
    }
}
