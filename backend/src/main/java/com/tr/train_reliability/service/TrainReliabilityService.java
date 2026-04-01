package com.tr.train_reliability.service;

import com.tr.train_reliability.entity.TrainRegularity;
import com.tr.train_reliability.repository.TrainRegularityRepository;
import com.tr.train_reliability.utility.TrainSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class TrainReliabilityService {
    private final TrainRegularityRepository tr;

    @Autowired
    public TrainReliabilityService(TrainRegularityRepository tr){this.tr = tr;}

//    @Cacheable("trains")
    public Page<TrainRegularity> getAllTrain(Pageable pageable){return tr.findAll(pageable);}

//    @Cacheable(value="trainFiltered", key = "{#trainType, #dataSetType, #label, #from, #to, #pageable.pageNumber, #pageable.pageSize}")
    public Page<TrainRegularity> getTrainsfilterBy(String trainType, String dataSetType, String label, LocalDate from, LocalDate to, Pageable pageable){
        return tr.findAll(
                Specification.where(TrainSpecifications.hasTrainType(trainType))
                        .and(TrainSpecifications.hasDataSetType(dataSetType))
                        .and(TrainSpecifications.hasLabel(label))
                        .and(TrainSpecifications.isBetweenDates(from, to)),
                pageable);
    }

//    @Cacheable(value="trainRankingFiltered", key = "{#trainType, #dataSetType, #label, #from, #pageable.pageNumber, #pageable.pageSize}")
    public Page<TrainRegularity> rankingbyBest(String trainType, String dataSetType, String label, LocalDate from, Pageable pageable){
        Sort sort = Sort.by(Sort.Order.desc("punctualityRate").nullsLast());

        LocalDate dateToUse = from;
        if(dateToUse == null) dateToUse = tr.findMaxDate();

        Pageable sortByPunctuality = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                sort
        );

        return tr.findAll(
                Specification.where(TrainSpecifications.hasTrainType(trainType))
                        .and(TrainSpecifications.hasDataSetType(dataSetType))
                        .and(TrainSpecifications.hasLabel(label))
                        .and(TrainSpecifications.isBetweenDates(dateToUse, dateToUse)),
                sortByPunctuality
        );
    }

}
