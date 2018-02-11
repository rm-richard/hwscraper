package rmrichard.hwscraper;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import rmrichard.hwscraper.model.Ad;
import rmrichard.hwscraper.model.SearchResult;
import rmrichard.hwscraper.model.SearchTask;
import rmrichard.hwscraper.service.EmailService;
import rmrichard.hwscraper.service.Scraper;

@SpringBootApplication
public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    @Autowired
    private Properties properties;

    @Autowired
    private Scraper scraper;

    @Autowired
    private EmailService emailService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            List<SearchResult> searchResults = new ArrayList<>();

            List<SearchTask> searchTasks = properties.getSearchTasks();
            for (SearchTask searchTask : searchTasks) {
                logger.info("Executing search for \"{}\"...", searchTask.getName());
                List<Ad> ads = scraper.scrapePage(searchTask.getUrl());
                logger.info("{} results found", ads.size());
                if (ads.size() > 0) {
                    searchResults.add(new SearchResult(searchTask.getName(), ads));
                }
                logger.info("Waiting {} millisecs after search...", properties.getSearchDelay());
                waitFor(properties.getSearchDelay());
            }

            logger.info("Search over, sending results mail...");
            emailService.sendEmail(searchResults);
            logger.info("Mail sent.");
        };
    }

    private void waitFor(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            // dont care
        }
    }
}
