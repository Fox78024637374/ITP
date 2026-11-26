package edu.course.lab05;

/**
 * Профильная модель: набор данных для обучения модели распознавания.
 * Класс неизменяемый, предметные ограничения проверяются в конструкторе
 * до того, как объект попадет в результат загрузки.
 */
public final class Sample {

    private final String id;
    private final String label;
    private final String source;
    private final int featureCount;
    private final SampleStatus status;

    public Sample(String id, String label, String source, int featureCount, SampleStatus status) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Идентификатор не может быть null или пустым");
        }
        if (label == null || label.isBlank()) {
            throw new IllegalArgumentException("Метка не может быть null или пустой");
        }
        if (source == null || source.isBlank()) {
            throw new IllegalArgumentException("Источник не может быть null или пустым");
        }
        if (featureCount <= 0) {
            throw new IllegalArgumentException("Количество признаков должно быть положительным");
        }
        if (status == null) {
            throw new IllegalArgumentException("Статус не может быть null");
        }
        this.id = id.trim();
        this.label = label.trim();
        this.source = source.trim();
        this.featureCount = featureCount;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public String getSource() {
        return source;
    }

    public int getFeatureCount() {
        return featureCount;
    }

    public SampleStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Sample{id='" + id + "', label='" + label + "', source='" + source
                + "', featureCount=" + featureCount + ", status=" + status + '}';
    }
}
