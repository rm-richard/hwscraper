package rmrichard.hwscraper.model;

import java.util.List;

// TODO: split into SearchTask and SearchResults
public class Search {
    private String name;
    private String url;
    private List<Ad> results;

    public Search() {
    }

    public Search(String name, String url, List<Ad> results) {
        this.name = name;
        this.url = url;
        this.results = results;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Ad> getResults() {
        return results;
    }

    public void setResults(List<Ad> results) {
        this.results = results;
    }
}
