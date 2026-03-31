package com.tr.train_reliability.repository;

import com.tr.train_reliability.entity.TrainRegularity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainRegularityRepository extends JpaRepository<TrainRegularity, Long>, JpaSpecificationExecutor<TrainRegularity> {}
