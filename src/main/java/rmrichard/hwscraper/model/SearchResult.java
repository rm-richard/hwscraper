package rmrichard.hwscraper.model;

import java.util.List;

public class SearchResult {
    private String name;
    private List<Ad> results;

    public SearchResult(String name, List<Ad> results) {
        this.name = name;
        this.results = results;
    }

    public String getName() {
        return name;
    }

    public List<Ad> getResults() {
        return results;
    }
}
