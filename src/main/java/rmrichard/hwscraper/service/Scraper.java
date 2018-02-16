package rmrichard.hwscraper.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import rmrichard.hwscraper.Properties;
import rmrichard.hwscraper.model.Ad;

@Component
public class Scraper {

    private static final String BASE_URL = "https://hardverapro.hu/";

    @Autowired
    private Properties properties;

    public List<Ad> scrapePage(String url) {
        List<Ad> adResults = new ArrayList<>();
        Document document = openPage(url);
        Elements rows = document.select(".global_list_gray, .global_list_def");

        for (Element row : rows) {
            adResults.add(parseRow(row));
        }

        return adResults;
    }

    protected Document openPage(String url) {
        try {
            return Jsoup.connect(url).userAgent(properties.getUserAgent()).get();
        } catch (IOException ex) {
            throw new RuntimeException("Failed to connect to site", ex);
        }
    }

    private Ad parseRow(Element row) {
        Element titleContainer = row.selectFirst(".glvc3>h5>a");
        String url = BASE_URL + titleContainer.attr("href");
        String title = titleContainer.text();

        Element priceContainer = row.selectFirst(".right>.global_list_pdc");
        String price = priceContainer.selectFirst("span.price").text();

        Element mainCityContainter = priceContainer.selectFirst("span.city");
        Element alternativeCityContainer = row.selectFirst(".right>.bubi.city_bubi");
        String city = mainCityContainter != null
            ? mainCityContainter.text()
            : alternativeCityContainer != null
            ? alternativeCityContainer.text()
            : "";

        return new Ad(title, url, price, city);
    }
}
