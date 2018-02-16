package rmrichard.hwscraper;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import rmrichard.hwscraper.model.SearchTask;

@Component
@ConfigurationProperties
public class Properties {

    private static final String DEFAULT_DB_FILE = "ads.db";
    private static final String DEFAULT_USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36"
        + " (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36";
    private static final Long DEFAULT_SEARCH_DELAY = 2000L;
    private static final Boolean DEFAULT_ALWAYS_SEND_MAIL = false;

    private String recipient;
    private String subject;
    private String userAgent;
    private Long searchDelay;
    private List<SearchTask> searchTasks;
    private String dbFile;
    private Boolean alwaysSendMail;

    @PostConstruct
    public void setup() {
        validateMandatoryParams();
        initializeDefaults();
    }

    private void validateMandatoryParams() {
        if (recipient == null || subject == null) {
            throw new IllegalArgumentException("Property \"recipient\" or \"subject\" not configured");
        }
        if (searchTasks == null || searchTasks.isEmpty()) {
            throw new IllegalArgumentException("No searchTasks configured");
        }
    }

    private void initializeDefaults() {
        dbFile = dbFile != null ? dbFile : DEFAULT_DB_FILE;
        searchDelay = searchDelay != null ? searchDelay : DEFAULT_SEARCH_DELAY;
        userAgent = userAgent != null ? userAgent : DEFAULT_USER_AGENT;
        alwaysSendMail = alwaysSendMail != null ? alwaysSendMail : DEFAULT_ALWAYS_SEND_MAIL;
    }

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

    public List<SearchTask> getSearchTasks() {
        return searchTasks;
    }

    public void setSearchTasks(List<SearchTask> searchTasks) {
        this.searchTasks = searchTasks;
    }

    public String getDbFile() {
        return dbFile;
    }

    public void setDbFile(String dbFile) {
        this.dbFile = dbFile;
    }

    public Boolean getAlwaysSendMail() {
        return alwaysSendMail;
    }

    public void setAlwaysSendMail(Boolean alwaysSendMail) {
        this.alwaysSendMail = alwaysSendMail;
    }
}
