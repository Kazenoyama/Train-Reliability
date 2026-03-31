package com.tr.train_reliability.mapper;

import com.tr.train_reliability.entity.TrainRegularity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Map;

@Component
public class TerMapper {

    public TrainRegularity map(Map<String, String> row){
        TrainRegularity tr = new TrainRegularity();

        tr.setTrainType("TER");
        tr.setDataSetType("REGION");
        tr.setLabel(row.get("Région"));
        tr.setDate(parseDate(row.get("Date")));
        tr.setPunctualityRate(parseDouble(row.get("Taux de régularité")));

        return tr;
    }

    private LocalDate parseDate(String value){
        return YearMonth.parse(value).atDay(1);
    }

    private Double parseDouble(String value){
        return value == null ? null : Double.parseDouble(value);
    }
}
