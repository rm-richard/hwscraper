package rmrichard.hwscraper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import rmrichard.hwscraper.repository.AdRepository;
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

    @Autowired
    private AdRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            List<SearchResult> searchResults = runSearchTasks();
            int newAdCount = repository.updateAndMarkNew(
                searchResults.stream()
                    .map(p -> p.getResults())
                    .flatMap(List::stream)
                    .collect(Collectors.toList())
            );

            if (newAdCount > 0 || properties.getAlwaysSendMail()) {
                logger.info("{} new ads seen, sending results mail...", newAdCount);
                emailService.sendEmail(searchResults);
                logger.info("Mail sent");
            } else {
                logger.info("No new ads, skipping mail sending");
            }
        };
    }

    private List<SearchResult> runSearchTasks() throws InterruptedException {
        List<SearchResult> searchResults = new ArrayList<>();

        for (SearchTask searchTask : properties.getSearchTasks()) {
            List<Ad> ads = scraper.scrapePage(searchTask.getUrl());
            logger.info("{} results for \"{}\"", ads.size(), searchTask.getName());
            if (ads.size() > 0) {
                searchResults.add(new SearchResult(searchTask.getName(), ads));
            }
            Thread.sleep(properties.getSearchDelay());
        }
        return searchResults;
    }
}
