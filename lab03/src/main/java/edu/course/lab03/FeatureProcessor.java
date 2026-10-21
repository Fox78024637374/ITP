package edu.course.lab03;

/**
 * Обрабатывает один вектор признаков с помощью переданной стратегии нормализации.
 * Класс не создает стратегию внутри себя и не знает ее конкретный тип.
 */
public final class FeatureProcessor {

    private final NormalizationStrategy normalizationStrategy;

    public FeatureProcessor(NormalizationStrategy normalizationStrategy) {
        if (normalizationStrategy == null) {
            throw new IllegalArgumentException("Стратегия нормализации не может быть null");
        }
        this.normalizationStrategy = normalizationStrategy;
    }

    public double[] process(double[] values) {
        return normalizationStrategy.normalize(values);
    }
}
