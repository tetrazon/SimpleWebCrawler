import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static junit.framework.TestCase.assertTrue;

public class BadCaseTest {

    @Test
    public void testCrawl() throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        String seedURL = "https://google.by";
        String[] terms = Utils.initTerms();
        final SimpleWebCrawler crawler = new SimpleWebCrawler();
        crawler.setTerms(terms);
        crawler.setDepthLimit(500);
        crawler.setPageLimit(6);
        System.out.println("starting search...");
        crawler.crawl(Utils.initUrlsSet(new URL(seedURL)),0);
        Utils.writeAllToCSV(crawler.getPageStatisticList(), "bad_all.csv");
        Utils.printAndWriteTopTen(crawler.getPageStatisticList(),"bad_top10.csv");
        File badAll = new File("bad_all.csv");
        File badTopTen = new File("bad_top10.csv");
        assertTrue(badAll.exists());
        assertTrue(badTopTen.exists());
    }
}
