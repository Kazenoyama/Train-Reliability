package com.tr.train_reliability.controller;

import com.tr.train_reliability.entity.TrainRegularity;
import com.tr.train_reliability.repository.TrainRegularityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/stats")
public class TrainRegularityController {

    @Autowired
    private TrainRegularityRepository trainRepo;

    @GetMapping("/alldata")
    public Page<TrainRegularity> getStats(Pageable pageable){
        return trainRepo.findAll(pageable);
    }
}
