package br.com.texo.utils;

import br.com.texo.domain.Producer;
import br.com.texo.domain.Studio;
import br.com.texo.dto.MovieDTO;
import liquibase.util.csv.CSVReader;
import liquibase.util.csv.opencsv.bean.ColumnPositionMappingStrategy;
import liquibase.util.csv.opencsv.bean.CsvToBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;

import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public abstract class Util {

    public static final SimpleDateFormat format = new SimpleDateFormat("yyyy");
    private static Calendar calendar;

    public static Date yearToDate(String year) {
        if(StringUtils.isBlank(year) || year.length() > 4){
            return null;
        }
        calendar = Calendar.getInstance();
        calendar.set(Integer.parseInt(year), 0, 1);
        return calendar.getTime();
    }

    public static String booleanToString(Boolean winner) {
        return winner.equals(Boolean.TRUE) ? "yes" : "no";
    }

    public static Boolean stringToBoolean(String winner) {
        if(StringUtils.isBlank(winner)) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public static <T> List<T> mapToCSV(Resource resource) throws IOException {
        ColumnPositionMappingStrategy strat = new ColumnPositionMappingStrategy();
        strat.setType(MovieDTO.class);
        String[] columns = new String[]{"year", "title", "studios", "producers", "winner"};
        strat.setColumnMapping(columns);
        CsvToBean csv = new CsvToBean();
        CSVReader csvReader = new CSVReader(new FileReader(resource.getFile()), ';');
        return csv.parse(strat, csvReader);

    }

    public static Set<Producer> getProducers(String producers) {
        Set<Producer> set = new HashSet<>();
        for (String name: producers.split(" and |,")) {
            set.add(new Producer(StringUtils.trim(name)));
        }
        return set;
    }

    public static Set<Studio> getStudios(String studios) {
        Set<Studio> set = new HashSet<>();
        for (String name: StringUtils.split(studios, ",")) {
            set.add(new Studio(name));
        }
        return set;
    }
}
