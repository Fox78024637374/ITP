package edu.course.lab02;

public record SampleId(String value) {
    public SampleId {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("SampleId не может быть null или пустым");
        }
        value = value.trim();   // в компактном конструкторе можно переприсвоить
    }
}