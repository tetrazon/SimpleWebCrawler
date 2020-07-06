import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public final class Utils {
    private static Logger logger = LogManager.getLogger(Utils.class);
    private static Properties props = new Properties();
    static {
        try {
            props.load(Utils.class.getClassLoader().getResourceAsStream("project.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Utils(){}

    public static String initSeedURL(){
        return Optional.ofNullable((props.getProperty("seed"))).orElse("https://en.wikipedia.org/wiki/Elon_Musk");
    }

    public static String[] initTerms(){
        return Optional.ofNullable((props.getProperty("terms")))
                .orElse("Tesla,Musk,Gigafactory,Elon Mask").split(",");
    }

    public static boolean isGoodURL(@NotNull URL url){
        return !url.toString().matches("(.*#.*)|(.*(css|js|gif|jpg|png|mp3|mp4|zip|gz|pdf|ppt)$)");
    }

    public static Set<URL> initUrlsSet(@NotNull URL startURL) {
        if(!isGoodURL(startURL)) {
            logger.error("can't crawl, wrong url");
            throw new IllegalArgumentException("can't crawl, wrong url");
        }
        return Collections.singleton(startURL);
    }

    public static void printAndWriteTopTen(@NotNull List<PageStatistic> pageStatisticList, @NotNull String filename) throws CsvRequiredFieldEmptyException,
                                                                                                                        IOException, CsvDataTypeMismatchException {
        List<PageStatistic> topTen = pageStatisticList.stream().sorted((a, b)-> b.getTotalCount() - a.getTotalCount()).
                limit(10).collect(Collectors.toList());
        System.out.println("\nTop 10:");
        System.out.println(topTen.stream().map(PageStatistic::toString).collect(Collectors.joining("\n")));
        writeAllToCSV(topTen, filename);

    }

    public static void writeAllToCSV(@NotNull List<PageStatistic> pageStatisticList, @NotNull String filename) throws IOException,
                                                                                                    CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        Writer writer = new FileWriter(filename);
        StatefulBeanToCsv<PageStatistic> beanToCsv = new StatefulBeanToCsvBuilder<PageStatistic>(writer).
                                                                    withSeparator(' ').
                                                                    withApplyQuotesToAll(false).
                                                                    build();
        beanToCsv.write(pageStatisticList);
        writer.close();
    }
}
