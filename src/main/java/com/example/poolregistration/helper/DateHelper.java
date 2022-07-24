package com.example.poolregistration.helper;

import com.example.poolregistration.model.constraints.DateConstraints;
import org.springframework.data.util.Pair;

import java.time.LocalDate;
import java.util.Set;

public class DateHelper {
    private static final Set<Pair<Integer, Integer>> holidays = Set.of(
            Pair.of(1, 1),
            Pair.of(1, 2),
            Pair.of(1, 3),
            Pair.of(1, 4),
            Pair.of(1, 5), //1-5 января
            Pair.of(1, 7), //рождество
            Pair.of(2, 23), //День защитника отечества
            Pair.of(3, 8), //8 марта
            Pair.of(5, 1), //Первомай
            Pair.of(5, 9), //День победы
            Pair.of(6, 12), //День России
            Pair.of(11, 4) //День народного единства
                    //https://www.rus.rusemb.org.uk/russiaholiday/
    );

    private static final DateConstraints normalDayConstraints = new DateConstraints(8, 20, 10);
    private static final DateConstraints holidayConstraints = new DateConstraints(10, 18, 10);

    public static DateConstraints getConstraints(LocalDate date) {
        return holidays.contains(Pair.of(date.getMonthValue(), date.getDayOfMonth()))
                ? holidayConstraints
                : normalDayConstraints;
    }
}
