package edu.course.lab05;

import java.util.List;

/**
 * Результат загрузки файла: корректные объекты и список ошибок записей.
 * Списки защищены от изменения вызывающим кодом.
 */
public final class LoadResult {

    private final List<Sample> samples;
    private final List<ValidationError> errors;

    public LoadResult(List<Sample> samples, List<ValidationError> errors) {
        if (samples == null || errors == null) {
            throw new IllegalArgumentException("Списки не могут быть null");
        }
        this.samples = List.copyOf(samples);
        this.errors = List.copyOf(errors);
    }

    public List<Sample> getSamples() {
        return samples;
    }

    public List<ValidationError> getErrors() {
        return errors;
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    @Override
    public String toString() {
        return "LoadResult{samples=" + samples.size() + ", errors=" + errors.size() + '}';
    }
}
