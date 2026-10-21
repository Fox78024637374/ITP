package edu.course.lab03;

/**
 * Центрирует вектор: вычитает из каждого значения среднее арифметическое.
 */
public class MeanCenteringNormalization implements NormalizationStrategy {

    @Override
    public double[] normalize(double[] values) {
        if (values == null) {
            throw new IllegalArgumentException("Вектор не может быть null");
        }
        if (values.length == 0) {
            return new double[0];
        }

        double sum = 0.0;
        for (double value : values) {
            sum += value;
        }
        double mean = sum / values.length;

        double[] result = new double[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = values[i] - mean;
        }
        return result;
    }
}
