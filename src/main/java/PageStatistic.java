import org.apache.commons.lang3.StringUtils;
import java.util.*;
import java.util.stream.Collectors;

public class PageStatistic {
    private String link;
    private Map<String, Integer> termsFrequency = new LinkedHashMap<>();
    private int totalCount;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Map<String, Integer> getTermsFrequency() {
        return termsFrequency;
    }

    public void setTermsFrequency(Map<String, Integer> termsFrequency) {
        this.termsFrequency = termsFrequency;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public void calculateTotalCount(){
        totalCount = termsFrequency.entrySet().stream().map(a->a.getValue()).reduce(0,(a, b)->a+b);
    }

    public void countTerms(String text, String[] terms){
        for (int i = 0; i < terms.length; i++) {
            termsFrequency.put(terms[i],StringUtils.countMatches(text, terms[i]));
        }

    }

    @Override
    public String toString() {
        String counts = termsFrequency.values().stream().map(a->a.toString()).collect(Collectors.joining(" ", " ", ""));
        return  link + counts + " " + totalCount;
    }

}
