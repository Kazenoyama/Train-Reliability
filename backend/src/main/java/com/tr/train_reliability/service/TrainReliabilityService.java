package com.tr.train_reliability.service;

import com.tr.train_reliability.entity.TrainRegularity;
import com.tr.train_reliability.repository.TrainRegularityRepository;
import com.tr.train_reliability.utility.TrainSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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

    public Page<TrainRegularity> rankingbyBest(String trainType, String dataSetType, String label, LocalDate from,LocalDate to, Pageable pageable){
        Sort sort = Sort.by(Sort.Order.desc("punctualityRate").nullsLast());

        from = from == null ? tr.findMinDate() : from;
//        to = to == null ? tr.findMaxDate() : to;

        Pageable sortByPunctuality = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                sort
        );

        return tr.findAll(
                Specification.where(TrainSpecifications.hasTrainType(trainType))
                        .and(TrainSpecifications.hasDataSetType(dataSetType))
                        .and(TrainSpecifications.hasLabel(label))
                        .and(TrainSpecifications.isBetweenDates(from, from)),
                sortByPunctuality
        );
    }

    public Page<TrainRegularity> chartDataSorted(String label, LocalDate from, LocalDate to, Pageable pageable){
        Sort sort = Sort.by(Sort.Order.asc("date"));

        from = from == null ? tr.findMinDate() : from;
        to = to == null ? tr.findMaxDate() : to;

        Pageable sortByDate = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                sort
        );

        return tr.findAll(
                Specification.where(TrainSpecifications.hasExactLabel(label))
                        .and(TrainSpecifications.isBetweenDates(from, to)), sortByDate
        );
    }

    public List<String> uniqueLabel(){
        return tr.findAll().stream()
                .map(TrainRegularity::getLabel)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<String> uniqueDataSet(){
        return tr.findAll().stream()
                .map(TrainRegularity::getDataSetType)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<String> uniqueTrainType(){
        return tr.findAll().stream()
                .map(TrainRegularity::getTrainType)
                .distinct()
                .collect(Collectors.toList());
    }

}
