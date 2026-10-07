package edu.course.lab02;

public class DataSample {
    private final String id;
    private final String label;
    private SampleStatus status;
    private final double[] features;

    public DataSample(String id, String label, SampleStatus status, double[] features) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("id не может быть null или пустым");
        }
        if (label == null || label.isBlank()) {
            throw new IllegalArgumentException("label не может быть null или пустым");
        }
        if (status == null) {
            throw new IllegalArgumentException("status не может быть null");
        }
        if (features == null) {
            throw new IllegalArgumentException("features не могут быть null");
        }
        if (features.length == 0) {
            throw new IllegalArgumentException("features не могут быть пустым массивом");
        }
        
        

        this.id = id;
        this.label = label;
        this.status = status;
        this.features = features.clone(); 
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public SampleStatus getStatus() {
        return status;
    }

    public double[] getFeatures() {
        return features.clone(); // чтобы нельзя было изменить внутренний массив
    }

    public void changeStatus(SampleStatus newStatus) {
        if (newStatus == null) {
            throw new IllegalArgumentException("Новый статус не может быть null");
        }
        this.status = newStatus;
    }

    public boolean isReady() {
        return status == SampleStatus.READY;
    }

    public double meanFeatures() {
        double sum = 0.0;
        for (double feature : features) {
            sum += feature;
        }
        return sum / features.length;
    }


    public DataSample normalized(double min, double max) {
        if (!Double.isFinite(min) || !Double.isFinite(max) || max <= min) {
            throw new IllegalArgumentException("min и max должны быть конечными, max > min");
        }

        double[] normalized = new double[features.length];
        for (int i = 0; i < features.length; i++) {
            normalized[i] = (features[i] - min) / (max - min);
        }

        return new DataSample(id, label, status, normalized);
    }


}