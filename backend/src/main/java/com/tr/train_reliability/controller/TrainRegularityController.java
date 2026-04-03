package com.tr.train_reliability.controller;

import com.tr.train_reliability.entity.TrainRegularity;
import com.tr.train_reliability.service.TrainReliabilityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/stats")
public class TrainRegularityController {
    private final TrainReliabilityService trs;

    @Autowired
    public TrainRegularityController(TrainReliabilityService trs){this.trs = trs;}

    @GetMapping("/alldata")
    public ResponseEntity<Page<TrainRegularity>> getAllData(Pageable pageable){
        return ResponseEntity.ok(trs.getAllTrain(pageable));
    }

    @GetMapping("/filterby")
    public ResponseEntity<Page<TrainRegularity>> filterBy(
            Pageable pageable,
            @RequestParam(required = false) String trainType,
            @RequestParam(required = false) String dataSetType,
            @RequestParam(required = false) String label,
            @RequestParam(required = false) LocalDate from,
            @RequestParam(required = false) LocalDate to
    ){
        return ResponseEntity.ok(trs.getTrainsfilterBy(trainType, dataSetType, label, from, to,pageable ));
    }

    @GetMapping("/ranking")
    public ResponseEntity<Page<TrainRegularity>> rankingBest(
            Pageable pageable,
            @RequestParam(required = false) String trainType,
            @RequestParam(required = false) String dataSetType,
            @RequestParam(required = false) String label,
            @RequestParam(required = false) LocalDate from,
            @RequestParam(required = false) LocalDate to
    ){
        return ResponseEntity.ok(trs.rankingbyBest(trainType, dataSetType, label, from, to, pageable));
    }

    @GetMapping("/chartData")
    public ResponseEntity<Page<TrainRegularity>> chartData(
            Pageable pageable,
            @RequestParam String label,
            @RequestParam LocalDate from,
            @RequestParam LocalDate to
            ){

        return ResponseEntity.ok(trs.chartDataSorted(label, from, to, pageable));
    }

    @GetMapping("/unique/{category}")
    public ResponseEntity<List<String>> getUniqueValue(@PathVariable String category){
        switch (category.toLowerCase()){
            case "label":
                return ResponseEntity.ok(trs.uniqueLabel());
            case "dataset":
                return ResponseEntity.ok(trs.uniqueDataSet());
            case "traintype":
                return ResponseEntity.ok(trs.uniqueTrainType());
            default:
                return ResponseEntity.badRequest().build();
        }
    }

}
