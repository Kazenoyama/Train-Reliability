package com.tr.train_reliability.mapper;

import com.tr.train_reliability.entity.TrainRegularity;
import com.tr.train_reliability.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TgvGMapper {

    @Autowired
    private Utility util;

    public TrainRegularity map(Map<String, String> row){
        TrainRegularity tr = new TrainRegularity();

        tr.setTrainType("TGV");
        tr.setDataSetType("GLOBAL");
        tr.setLabel("TGV Global");
        tr.setDate(util.parseDate(row.get("Date")));
        tr.setPunctualityRate(util.parseDouble(row.get("Régularité composite")));

        return tr;
    }
}
