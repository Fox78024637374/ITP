package edu.course.lab03;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class PercentDiscountTest {

    private static final double EPS = 1e-9;

    @Test
    void appliesTwentyPercentDiscount() {
        PercentDiscount policy = new PercentDiscount(20);
        assertEquals(80.0, policy.apply(100.0), EPS);
    }

    @Test
    void zeroPercentKeepsPrice() {
        PercentDiscount policy = new PercentDiscount(0);
        assertEquals(100.0, policy.apply(100.0), EPS);
    }

    @Test
    void hundredPercentMakesPriceZero() {
        PercentDiscount policy = new PercentDiscount(100);
        assertEquals(0.0, policy.apply(100.0), EPS);
    }

    @Test
    void rejectsPercentOutsideRange() {
        assertThrows(IllegalArgumentException.class, () -> new PercentDiscount(-1));
        assertThrows(IllegalArgumentException.class, () -> new PercentDiscount(101));
    }

    @Test
    void rejectsNegativePrice() {
        PercentDiscount policy = new PercentDiscount(20);
        assertThrows(IllegalArgumentException.class, () -> policy.apply(-0.01));
    }
}
