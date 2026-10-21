package edu.course.lab03;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class PriceCalculatorTest {

    private static final double EPS = 1e-9;

    @Test
    void calculatesPriceWithoutDiscount() {
        PriceCalculator calculator = new PriceCalculator(new NoDiscount());
        assertEquals(250.0, calculator.calculate(250.0), EPS);
    }

    @Test
    void calculatesPriceWithPercentDiscount() {
        PriceCalculator calculator = new PriceCalculator(new PercentDiscount(20));
        assertEquals(200.0, calculator.calculate(250.0), EPS);
    }

    @Test
    void policyCanBeReplacedWithoutChangingCalculatorClass() {
        PriceCalculator calculator = new PriceCalculator(new NoDiscount());
        assertEquals(100.0, calculator.calculate(100.0), EPS);

        calculator = new PriceCalculator(new PercentDiscount(20));
        assertEquals(80.0, calculator.calculate(100.0), EPS);
    }

    @Test
    void rejectsNullPolicy() {
        assertThrows(IllegalArgumentException.class, () -> new PriceCalculator(null));
    }
}
