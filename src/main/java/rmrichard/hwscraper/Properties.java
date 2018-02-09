package rmrichard.hwscraper;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import rmrichard.hwscraper.model.Search;

@Component
@ConfigurationProperties
public class Properties {

    private String recipient;
    private String subject;
    private String baseUrl;
    private String userAgent;
    private Long searchDelay;
    private List<Search> searches;

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public Long getSearchDelay() {
        return searchDelay;
    }

    public void setSearchDelay(Long searchDelay) {
        this.searchDelay = searchDelay;
    }

    public List<Search> getSearches() {
        return searches;
    }

    public void setSearches(List<Search> searches) {
        this.searches = searches;
    }

}
