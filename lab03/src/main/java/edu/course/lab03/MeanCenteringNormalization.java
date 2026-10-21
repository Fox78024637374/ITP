package edu.course.lab03;

/**
 * Центрирует вектор: вычитает из каждого значения среднее арифметическое.
 * Среднее значение результата равно нулю.
 */
public final class MeanCenteringNormalization implements NormalizationStrategy {

    @Override
    public double[] normalize(double[] values) {
        requireValidVector(values);

        double sum = 0.0;
        for (double value : values) {
            sum += value;
        }
        double mean = sum / values.length;

        double[] normalized = new double[values.length];
        for (int i = 0; i < values.length; i++) {
            normalized[i] = values[i] - mean;
        }
        return normalized;
    }

    private static void requireValidVector(double[] values) {
        if (values == null) {
            throw new IllegalArgumentException("Вектор не может быть null");
        }
        if (values.length == 0) {
            throw new IllegalArgumentException("Вектор не может быть пустым");
        }
        for (double value : values) {
            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException("Все значения вектора должны быть конечными");
            }
        }
    }
}
