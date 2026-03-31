package com.tr.train_reliability.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class TrainRegularity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private String trainType;
    private String dataSetType;
    private String label;

    private Double punctualityRate;

    public TrainRegularity() {
    }

    public TrainRegularity(Long id, LocalDate date, String trainType, String dataSetType, String label, Double punctualityRate) {
        this.id = id;
        this.date = date;
        this.trainType = trainType;
        this.dataSetType = dataSetType;
        this.label = label;
        this.punctualityRate = punctualityRate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTrainType() {
        return trainType;
    }

    public void setTrainType(String trainType) {
        this.trainType = trainType;
    }

    public String getDataSetType() {
        return dataSetType;
    }

    public void setDataSetType(String dataSetType) {
        this.dataSetType = dataSetType;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Double getPunctualityRate() {
        return punctualityRate;
    }

    public void setPunctualityRate(Double punctualityRate) {
        this.punctualityRate = punctualityRate;
    }
}


