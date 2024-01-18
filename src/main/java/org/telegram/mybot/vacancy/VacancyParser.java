package org.telegram.mybot.vacancy;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class VacancyParser {
    private static final String URL = "https://jobs.dou.ua/vacancies/?category=";
    private static final List<String> categories = List.of(
            ".NET",
            "Java",
            "C++",
            "Python"
    );

    public List<String> getCategories() {
        return new ArrayList<>(categories);
    }

    public List<Vacancy> getVacancyHTML(String category) {

        try {
           return Jsoup.connect(URL + category)
                   .get()
                   .select(".l-vacancy")
                   .stream()
                   .map(VacancyParser::convert)
                   .filter(Vacancy.filterByName())
                   .sorted(Vacancy.sortByDate())
                   .limit(10)
                   .toList();
        } catch (Exception e) {
            return List.of(new Vacancy("Error !", LocalDate.now()));
        }
    }

    private static Vacancy convert(Element element) {
        return Vacancy.builder()
                .name(element.selectFirst(".vt").outerHtml().replaceAll("&nbsp;", " "))
                .date(Vacancy.convert(element.selectFirst(".date").text()))
                .build();
    }

}
