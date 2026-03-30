package com.tr.train_reliability.mapper;

import com.tr.train_reliability.entity.TrainRegularity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Map;

@Component
public class TransilienMapper {

    public TrainRegularity map(Map<String, String> row){
        TrainRegularity tr = new TrainRegularity();

        tr.setTrainType(row.get("Service"));
        tr.setDataSetType("TRANSILIEN");
        tr.setLabel(row.get("Ligne"));
        tr.setDate(parseDate(row.get("Date")));
        tr.setPunctualityRate(parseDouble(row.get("Taux de ponctualité")));

        return tr;
    }

    private LocalDate parseDate(String value){
        return YearMonth.parse(value).atDay(1);
    }

    private Double parseDouble(String value){
        return value == null ? null : Double.parseDouble(value);
    }

}
