package com.tr.train_reliability.utility;

import com.tr.train_reliability.entity.TrainRegularity;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class TrainSpecifications {

    public static Specification<TrainRegularity> hasTrainType(String trainType){
        return (root, query, cb) -> trainType == null ? null : cb.equal(cb.lower(root.get("trainType")), trainType.toLowerCase());
    }

    public static Specification<TrainRegularity> hasDataSetType(String dataSetType){
        return (root, query, cb) -> dataSetType == null ? null : cb.equal(cb.lower(root.get("dataSetType")), dataSetType.toLowerCase());
    }

    public static Specification<TrainRegularity> hasLabel(String label){
        return (root, query, cb) -> label == null ? null : cb.like(cb.lower(root.get("label")), label.toLowerCase());
    }

    public static Specification<TrainRegularity> isBetweenDates(LocalDate from, LocalDate to){
        return (root, query, cb) -> {
            if(from == null && to == null) return null;
            if(from != null && to == null) return cb.greaterThanOrEqualTo(root.get("date"), from);
            if(from == null && to != null) return cb.lessThanOrEqualTo(root.get("date"), to);
            else return cb.between(root.get("date"), from, to);
        };
    }
}
