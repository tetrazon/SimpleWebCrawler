
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class SimpleWebCrawler {
    private static Logger logger = LogManager.getLogger(SimpleWebCrawler.class);
    private final Set<URL> urlSet;
    private int pageLimit;
    private int depthLimit;
    private String[] terms;
    private List<PageStatistic> pageStatisticList = new ArrayList<>();
    private String processedURL;

    public SimpleWebCrawler(){
        urlSet = new HashSet<>();
        depthLimit = 8;
        pageLimit = 1000;

    }

    public int getDepthLimit() {
        return depthLimit;
    }

    public void setDepthLimit(int depthLimit) {
        this.depthLimit = depthLimit;
    }

    public int getPageLimit() {
        return pageLimit;
    }

    public void setPageLimit(int limit) {
        this.pageLimit = limit;
    }

    public String[] getTerms() {
        return terms;
    }

    public void setTerms(String[] terms) {
        this.terms = terms;
    }

    public List<PageStatistic> getPageStatisticList() {
        return pageStatisticList;
    }


    public void crawl(Set<URL> urls, int depth) {
        if (depth> depthLimit)
            return;
        urls.removeAll(this.urlSet);
        if (!urls.isEmpty()) {
            HashSet<URL> urlsToProcess = new HashSet<>();
            try {
                this.urlSet.addAll(urls);
                for(URL url: urls) {
                    if (--pageLimit == 0)
                        return;
                    if (Utils.isGoodURL(url)) {
                        String stringURL = url.toString();
                        processedURL = stringURL;
                        Document document = Jsoup.connect(stringURL).get();
                        String text = document.select("body").text();
                        PageStatistic pageStatistic = new PageStatistic();
                        pageStatistic.setLink(stringURL);
                        pageStatistic.countTerms(text, terms);
                        pageStatistic.calculateTotalCount();
                        System.out.println(pageStatistic);
                        pageStatisticList.add(pageStatistic);
                        Elements linksOnPage = document.select("a[href]");
                        for (Element element : linksOnPage) {
                            String urlText = element.attr("abs:href");
                            URL discoveredURL = new URL(urlText);
                            urlsToProcess.add(discoveredURL);
                        }
                    }
                }
            } catch (IOException e) {
                logger.error("failed to connect: " + processedURL);
                logger.error(Arrays.deepToString(e.getStackTrace()));
            }
            crawl(urlsToProcess, depth++);
        }
    }


}
