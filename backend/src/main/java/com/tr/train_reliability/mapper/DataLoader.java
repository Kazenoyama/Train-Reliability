package com.tr.train_reliability.mapper;

import com.tr.train_reliability.entity.TrainRegularity;
import com.tr.train_reliability.repository.TrainRegularityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private CsvReader csvReader;

    @Autowired
    private TrainRegularityRepository trainRepo;

    @Autowired
    private TerMapper terMapper;

    @Autowired
    private TransilienMapper transilienMapper;

    @Override
    public void run(String... args){
        if(trainRepo.count() > 0){
            System.out.println("Data already loaded");
            return;
        }
        System.out.println("Loading data... ");

        List<TrainRegularity> data = new ArrayList<>();

        List<Map<String, String>> terRows = csvReader.read("./src/main/resources/data/regularite-mensuelle-ter.csv");
        terRows.forEach(row -> data.add(terMapper.map(row)));
        List<Map<String, String>> transilienRows = csvReader.read("./src/main/resources/data/ponctualite-mensuelle-transilien.csv");
        transilienRows.forEach(row -> data.add(transilienMapper.map(row)));

        trainRepo.saveAll(data);

        System.out.println("Loaded" + data.size() + " rows");
    }
}
