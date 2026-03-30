package com.tr.train_reliability.controller;

import com.tr.train_reliability.entity.TrainRegularity;
import com.tr.train_reliability.repository.TrainRegularityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/data")
public class TrainRegularityController {

    @Autowired
    private TrainRegularityRepository trainRepo;

    @GetMapping
    public List<TrainRegularity> getAll() {return trainRepo.findAll(); }
}
