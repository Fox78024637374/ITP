package edu.course.lab03;

/**
 * Дополнительная стратегия: z-score нормализация
 * {@code (x - mean) / standardDeviation}.
 * Используется генеральное стандартное отклонение (деление на количество значений).
 */
public final class ZScoreNormalization implements NormalizationStrategy {

    @Override
    public double[] normalize(double[] values) {
        requireValidVector(values);

        double sum = 0.0;
        for (double value : values) {
            sum += value;
        }
        double mean = sum / values.length;

        double squaredDifferences = 0.0;
        for (double value : values) {
            double difference = value - mean;
            squaredDifferences += difference * difference;
        }
        double deviation = Math.sqrt(squaredDifferences / values.length);

        double[] normalized = new double[values.length];
        if (deviation == 0.0) {
            // Все значения одинаковы: стандартное отклонение нулевое.
            return normalized;
        }

        for (int i = 0; i < values.length; i++) {
            normalized[i] = (values[i] - mean) / deviation;
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
