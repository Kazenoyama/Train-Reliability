package com.tr.train_reliability.mapper;

import com.tr.train_reliability.entity.TrainRegularity;
import com.tr.train_reliability.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TgvLiaisonMapper {

    @Autowired
    private Utility util;

    public TrainRegularity map(Map<String, String> row){
        TrainRegularity tr = new TrainRegularity();

        tr.setTrainType("TGV");
        tr.setDataSetType(row.get("Service").toUpperCase());
        tr.setLabel(row.get("Gare de départ") + " -/- " + row.get("Gare d'arrivée"));
        tr.setDate(util.parseDate(row.get("Date")));
        tr.setPunctualityRate((1
                - (util.parseDouble( row.get("Nombre de trains en retard à l'arrivée"))
                /(util.parseDouble(row.get("Nombre de circulations prévues")) - util.parseDouble(row.get("Nombre de trains annulés")))
        ))
                *100);
        return tr;
    }
}
