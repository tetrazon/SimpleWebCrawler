import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static junit.framework.TestCase.assertTrue;

public class HappyCaseTest {

    @Test
    public void testCrawl() throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        String seedURL = Utils.initSeedURL();
        String[] terms = Utils.initTerms();
        final SimpleWebCrawler crawler = new SimpleWebCrawler();
        crawler.setTerms(terms);
        System.out.println("starting search...");
        crawler.crawl(Utils.initUrlsSet(new URL(seedURL)),0);
        Utils.writeAllToCSV(crawler.getPageStatisticList(), "happy_all.csv");
        Utils.printAndWriteTopTen(crawler.getPageStatisticList(),"happy_top10.csv");
        File happyAll = new File("happy_all.csv");
        File happyTopTen = new File("happy_top10.csv");
        assertTrue(happyAll.exists());
        assertTrue(happyTopTen.exists());
    }
}
