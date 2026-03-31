package com.tr.train_reliability.service;

import com.tr.train_reliability.entity.TrainRegularity;
import com.tr.train_reliability.repository.TrainRegularityRepository;
import com.tr.train_reliability.utility.TrainSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class TrainReliabilityService {
    private final TrainRegularityRepository tr;

    @Autowired
    public TrainReliabilityService(TrainRegularityRepository tr){this.tr = tr;}

    public Page<TrainRegularity> getAllTrain(Pageable pageable){return tr.findAll(pageable);}

    public Page<TrainRegularity> getTrainsfilterBy(String trainType, String dataSetType, String label, LocalDate from, LocalDate to, Pageable pageable){
        return tr.findAll(
                Specification.where(TrainSpecifications.hasTrainType(trainType))
                        .and(TrainSpecifications.hasDataSetType(dataSetType))
                        .and(TrainSpecifications.hasLabel(label))
                        .and(TrainSpecifications.isBetweenDates(from, to)),
                pageable);
    }

}
