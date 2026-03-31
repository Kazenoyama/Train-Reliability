package com.tr.train_reliability.mapper;

import com.tr.train_reliability.entity.TrainRegularity;
import com.tr.train_reliability.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TgvAxeMapper {

    @Autowired
    private Utility util;

    public TrainRegularity map(Map<String, String> row){
        TrainRegularity tr = new TrainRegularity();

        tr.setTrainType("TGV");
        tr.setDataSetType("AXE");
        tr.setLabel(row.get("Axe"));
        tr.setDate(util.parseDate(row.get("Date")));
        tr.setPunctualityRate(util.parseDouble(row.get("Régularité composite")));

        return tr;
    }
}
