package com.tr.train_reliability.mapper;

import com.tr.train_reliability.entity.TrainRegularity;
import com.tr.train_reliability.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class InterciteMapper {

    @Autowired
    private Utility util;

    public TrainRegularity map(Map<String, String> row){
        TrainRegularity tr = new TrainRegularity();

        tr.setTrainType("INTERCITES");
        tr.setDataSetType("LIAISON");
        tr.setLabel(row.get("Départ") + " -/- " + row.get("Arrivée"));
        tr.setDate(util.parseDate(row.get("Date")));
        tr.setPunctualityRate(util.parseDouble(row.get("Taux de régularité")));

        return tr;
    }

}
