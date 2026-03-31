package com.tr.train_reliability.mapper;

import com.tr.train_reliability.entity.TrainRegularity;
import com.tr.train_reliability.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class TransilienMapper {

    @Autowired
    private Utility util;

    public TrainRegularity map(Map<String, String> row){
        TrainRegularity tr = new TrainRegularity();

        tr.setTrainType(row.get("Service").toUpperCase());
        tr.setDataSetType("LINE");
        tr.setLabel(row.get("Nom de la ligne"));
        tr.setDate(util.parseDate(row.get("Date")));
        tr.setPunctualityRate(util.parseDouble(row.get("Taux de ponctualité")));

        return tr;
    }

}
