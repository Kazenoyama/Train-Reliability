package com.tr.train_reliability;

import com.tr.train_reliability.entity.TrainRegularity;
import com.tr.train_reliability.repository.TrainRegularityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class testData implements CommandLineRunner {

    @Autowired
    private TrainRegularityRepository trainRepo;

    @Override
    public void run(String... args) throws Exception {
        if(trainRepo.count()==0){
            TrainRegularity tr = new TrainRegularity();

            tr.setDate(LocalDate.now());
            tr.setTrainType("TER");
            tr.setDataSetType("REGION");
            tr.setLabel("Test REGION Train");
            tr.setPunctualityRate(79.0);

            trainRepo.save(tr);
        }
    }
}
