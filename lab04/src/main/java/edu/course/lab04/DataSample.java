package edu.course.lab04;

/**
 * Минимальная модель наблюдения, используемая как значение в реестре.
 * Массив признаков защищен копированием при создании и при чтении.
 */
public final class DataSample {

    private final String label;
    private final double[] features;

    public DataSample(String label, double[] features) {
        if (label == null || label.isBlank()) {
            throw new IllegalArgumentException("Метка не может быть null или пустой");
        }
        if (features == null) {
            throw new IllegalArgumentException("Признаки не могут быть null");
        }
        if (features.length == 0) {
            throw new IllegalArgumentException("Признаки не могут быть пустым массивом");
        }
        this.label = label.trim();
        this.features = features.clone();
    }

    public String getLabel() {
        return label;
    }

    public double[] getFeatures() {
        return features.clone();
    }

    @Override
    public String toString() {
        return "DataSample{label='" + label + "', features.length=" + features.length + "}";
    }
}
