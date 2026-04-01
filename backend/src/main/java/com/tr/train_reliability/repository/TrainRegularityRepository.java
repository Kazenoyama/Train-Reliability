package com.tr.train_reliability.repository;

import com.tr.train_reliability.entity.TrainRegularity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface TrainRegularityRepository extends JpaRepository<TrainRegularity, Long>, JpaSpecificationExecutor<TrainRegularity> {

    @Query("SELECT MAX(t.date) FROM TrainRegularity t")
    LocalDate findMaxDate();

}
