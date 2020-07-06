import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class UtilsTest {

    @Test
    public void initSeedURL() {
        assertTrue(Utils.initSeedURL().equals("https://en.wikipedia.org/wiki/Elon_Musk"));
    }

    @Test
    public void initTerms() {
        String [] terms = new String[]{"Tesla","Musk","Gigafactory","Elon Mask"};
        String [] termsToTest = Utils.initTerms();
        for (int i = 0; i < terms.length; i++) {
            assertTrue(terms[i].equals(termsToTest[i]));
        }

    }

    @Test
    public void isGoodURL() throws MalformedURLException {
        URL goodUrl = new URL("https://en.wikipedia.org/wiki/Elon_Musk");
        URL badUrl = new URL("https://en.wikipedia.org/wiki/Elon_Musk#ololo");
        assertTrue(Utils.isGoodURL(goodUrl));
        assertFalse(Utils.isGoodURL(badUrl));
    }

    @Test
    public void initUrlsSet() throws MalformedURLException {
        URL testURL = new URL("https://en.wikipedia.org/wiki/Elon_Musk");
        assertTrue(Utils.initUrlsSet(testURL).contains(testURL));
    }

    @Test
    public void writeAllToCSV() throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        List<PageStatistic> pageStatisticList = new LinkedList<>();
        PageStatistic pageStatistic = new PageStatistic();
        pageStatistic.setLink("https://en.wikipedia.org/wiki/Elon_Musk");
        Map<String, Integer> termsFrequency = new LinkedHashMap<>();
        termsFrequency.put("Musk", 1000);
        termsFrequency.put("Tesla", 20);
        pageStatistic.setTermsFrequency(termsFrequency);
        pageStatistic.calculateTotalCount();
        pageStatisticList.add(pageStatistic);
        Utils.writeAllToCSV(pageStatisticList, "test.csv");
        File f = new File("test.csv");
        assertTrue(f.exists());
    }
}