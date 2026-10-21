package edu.course.lab03;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class MinMaxNormalizationTest {

    private static final double EPS = 1e-9;

    @Test
    void scalesValuesToUnitRange() {
        MinMaxNormalization strategy = new MinMaxNormalization();
        double[] result = strategy.normalize(new double[]{10.0, 20.0, 30.0});
        assertArrayEquals(new double[]{0.0, 0.5, 1.0}, result, EPS);
    }

    @Test
    void handlesNegativeValues() {
        MinMaxNormalization strategy = new MinMaxNormalization();
        double[] result = strategy.normalize(new double[]{-10.0, 0.0, 10.0});
        assertArrayEquals(new double[]{0.0, 0.5, 1.0}, result, EPS);
    }

    @Test
    void constantVectorBecomesZeros() {
        MinMaxNormalization strategy = new MinMaxNormalization();
        double[] result = strategy.normalize(new double[]{7.0, 7.0, 7.0});
        assertArrayEquals(new double[]{0.0, 0.0, 0.0}, result, EPS);
    }

    @Test
    void singleValueBecomesZero() {
        MinMaxNormalization strategy = new MinMaxNormalization();
        assertArrayEquals(new double[]{0.0}, strategy.normalize(new double[]{5.0}), EPS);
    }

    @Test
    void doesNotModifyInputArray() {
        MinMaxNormalization strategy = new MinMaxNormalization();
        double[] input = {10.0, 20.0, 30.0};
        double[] original = input.clone();

        strategy.normalize(input);

        assertArrayEquals(original, input, EPS);
    }

    @Test
    void handlesEmptyArray() {
        MinMaxNormalization strategy = new MinMaxNormalization();
        assertEquals(0, strategy.normalize(new double[0]).length);
    }

    @Test
    void rejectsNullVector() {
        MinMaxNormalization strategy = new MinMaxNormalization();
        assertThrows(IllegalArgumentException.class, () -> strategy.normalize(null));
    }
}
