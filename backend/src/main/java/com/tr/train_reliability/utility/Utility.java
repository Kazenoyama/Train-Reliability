package com.tr.train_reliability.utility;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.YearMonth;

@Component
public class Utility {

    public LocalDate parseDate(String value){return YearMonth.parse(value).atDay(1);}

    public Double parseDouble(String value){return value == null ? null : Double.parseDouble(value);}

}
