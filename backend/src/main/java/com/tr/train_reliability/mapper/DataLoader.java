package com.tr.train_reliability.mapper;

import com.tr.train_reliability.entity.TrainRegularity;
import com.tr.train_reliability.repository.TrainRegularityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.InputStream;
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

    @Autowired
    private TgvGMapper tgvGMapper;

    @Autowired
    private TgvAxeMapper tgvAxeMapper;

    @Autowired
    private TgvLiaisonMapper tgvLiaisonMapper;

    @Autowired
    private InterciteMapper interciteMapper;

    @Override
    public void run(String... args){

        if(trainRepo.count() > 0){
            System.out.println("Data already loaded");
            return;
        }

        System.out.println("Loading data... ");

        try {
            List<TrainRegularity> data = new ArrayList<>();

            InputStream terStream = getClass()
                    .getResourceAsStream("/data/regularite-mensuelle-ter.csv");

            InputStream transilienStream = getClass()
                    .getResourceAsStream("/data/ponctualite-mensuelle-transilien.csv");

            InputStream tgvGStream = getClass()
                    .getResourceAsStream("/data/reglarite-mensuelle-tgv-nationale.csv");

            InputStream tgvAxeStream = getClass()
                    .getResourceAsStream("/data/regularite-mensuelle-tgv-axes.csv");

            InputStream tgvLiaisonStream = getClass()
                    .getResourceAsStream("/data/regularite-mensuelle-tgv-aqst.csv");

            InputStream interciteStream = getClass()
                    .getResourceAsStream("/data/regularite-mensuelle-intercites.csv");

            if (terStream == null || transilienStream == null || tgvGStream == null
                    || tgvAxeStream == null || tgvLiaisonStream == null || interciteStream == null) {
                throw new RuntimeException("CSV files not found in resources!");
            }

            List<Map<String, String>> terRows = csvReader.read(terStream);
            terRows.forEach(row -> data.add(terMapper.map(row)));

            List<Map<String, String>> transilienRows = csvReader.read(transilienStream);
            transilienRows.forEach(row -> data.add(transilienMapper.map(row)));

            List<Map<String, String>> tgvGRows = csvReader.read(tgvGStream);
            tgvGRows.forEach(row -> data.add(tgvGMapper.map(row)));

            List<Map<String, String>> tgvAxeRows = csvReader.read(tgvAxeStream);
            tgvAxeRows.forEach(row -> data.add(tgvAxeMapper.map(row)));

            List<Map<String, String>> tgvLiaisonRows = csvReader.read(tgvLiaisonStream);
            tgvLiaisonRows.forEach(row -> data.add(tgvLiaisonMapper.map(row)));

            List<Map<String, String>> interciteRows = csvReader.read(interciteStream);
            interciteRows.forEach(row -> data.add(interciteMapper.map(row)));

            trainRepo.saveAll(data);

            System.out.println("Loaded " + data.size() + " rows");

        } catch (Exception e) {
            System.err.println("Error loading data");
            e.printStackTrace();
        }
    }
}
