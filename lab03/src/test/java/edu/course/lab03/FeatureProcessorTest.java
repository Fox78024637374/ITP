package edu.course.lab03;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class FeatureProcessorTest {

    private static final double EPS = 1e-9;

    @Test
    void processesVectorWithMinMaxStrategy() {
        FeatureProcessor processor = new FeatureProcessor(new MinMaxNormalization());
        double[] result = processor.process(new double[]{10.0, 20.0, 30.0});
        assertArrayEquals(new double[]{0.0, 0.5, 1.0}, result, EPS);
    }

    @Test
    void processesVectorWithMeanCenteringStrategy() {
        FeatureProcessor processor = new FeatureProcessor(new MeanCenteringNormalization());
        double[] result = processor.process(new double[]{1.0, 2.0, 3.0});
        assertArrayEquals(new double[]{-1.0, 0.0, 1.0}, result, EPS);
    }

    @Test
    void strategyCanBeReplacedWithoutChangingProcessorClass() {
        double[] input = {1.0, 2.0, 3.0};

        FeatureProcessor processor = new FeatureProcessor(new MinMaxNormalization());
        assertArrayEquals(new double[]{0.0, 0.5, 1.0}, processor.process(input), EPS);

        processor = new FeatureProcessor(new MeanCenteringNormalization());
        assertArrayEquals(new double[]{-1.0, 0.0, 1.0}, processor.process(input), EPS);
    }

    @Test
    void acceptsAnyImplementationOfNormalizationStrategy() {
        FeatureProcessor processor = new FeatureProcessor(values -> values);
        assertArrayEquals(new double[]{4.0, 5.0}, processor.process(new double[]{4.0, 5.0}), EPS);
    }

    @Test
    void doesNotModifyInputArray() {
        FeatureProcessor processor = new FeatureProcessor(new MinMaxNormalization());
        double[] input = {10.0, 20.0, 30.0};
        double[] original = input.clone();

        processor.process(input);

        assertArrayEquals(original, input, EPS);
    }

    @Test
    void rejectsNullStrategy() {
        assertThrows(IllegalArgumentException.class, () -> new FeatureProcessor(null));
    }
}
