package org.telegram.mybot.vacancy;

import lombok.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Locale;
import java.util.function.Predicate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Vacancy {

    private String name;
    private LocalDate date;

    public static LocalDate convert(String date) {
        Locale ukrainianLocale = new Locale("uk", "UA");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", ukrainianLocale);
        try {
            return LocalDate.parse(date, formatter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return LocalDate.now();
    }

    @Override
    public String toString() {
        return  name + "\n\n" + date;
    }

    public static Predicate<Vacancy> filterByName() {
        return new Predicate<Vacancy>() {
            @Override
            public boolean test(Vacancy vacancy) {
                String name = vacancy.getName().toLowerCase();
                return !(name.contains("senior") || name.contains("lead"));
            }
        };
    }

    public static Comparator<Vacancy> sortByDate() {
        return new Comparator<Vacancy>() {
            @Override
            public int compare(Vacancy o1, Vacancy o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        };
    }

}
