package edu.course.lab04;

/**
 * Неизменяемый ключ наблюдения: имя набора данных и номер наблюдения.
 * Оба поля участвуют и в {@link #equals(Object)}, и в {@link #hashCode()}.
 */
public final class SampleId {

    private final String datasetName;
    private final int observationNumber;

    public SampleId(String datasetName, int observationNumber) {
        if (datasetName == null || datasetName.isBlank()) {
            throw new IllegalArgumentException("Имя набора не может быть null или пустым");
        }
        if (observationNumber < 0) {
            throw new IllegalArgumentException("Номер наблюдения не может быть отрицательным");
        }
        this.datasetName = datasetName.trim();
        this.observationNumber = observationNumber;
    }

    public String getDatasetName() {
        return datasetName;
    }

    public int getObservationNumber() {
        return observationNumber;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SampleId)) {
            return false;
        }
        SampleId sampleId = (SampleId) other;
        return observationNumber == sampleId.observationNumber
            && datasetName.equals(sampleId.datasetName);
    }

    @Override
    public int hashCode() {
        int result = datasetName.hashCode();
        result = 31 * result + observationNumber;
        return result;
    }

    @Override
    public String toString() {
        return "SampleId{dataset='" + datasetName + "', observation=" + observationNumber + "}";
    }
}
