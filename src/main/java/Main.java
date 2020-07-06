import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.IOException;
import java.net.URL;

public class Main {
    public static void main(String[] args) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        String seedURL = Utils.initSeedURL();
        String[] terms = Utils.initTerms();
        final SimpleWebCrawler crawler = new SimpleWebCrawler();
        crawler.setTerms(terms);
        System.out.println("starting search...");
        crawler.crawl(Utils.initUrlsSet(new URL(seedURL)),0);
        Utils.writeAllToCSV(crawler.getPageStatisticList(), "all.csv");
        Utils.printAndWriteTopTen(crawler.getPageStatisticList(),"top10.csv");
    }
}
