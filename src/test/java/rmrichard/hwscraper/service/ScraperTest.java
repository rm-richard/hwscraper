package rmrichard.hwscraper.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import rmrichard.hwscraper.Properties;
import rmrichard.hwscraper.model.Ad;

@RunWith(MockitoJUnitRunner.class)
public class ScraperTest {

    public static final String SAMPLE_PAGE = "/page_sample.html";

    @InjectMocks
    private Scraper scraper;

    @Mock
    private Properties properties;

    @Test
    public void testScraper() throws Exception {
        // GIVEN
        Scraper scraperSpy = Mockito.spy(scraper);
        String html = IOUtils.toString(getClass().getResourceAsStream(SAMPLE_PAGE));

        doReturn(Jsoup.parse(html)).when(scraperSpy).openPage(any());

        // WHEN
        List<Ad> ads = scraperSpy.scrapePage(SAMPLE_PAGE);

        // THEN
        verify(scraperSpy, times(1)).openPage(SAMPLE_PAGE);
        assertEquals(5, ads.size());
        assertEquals("localhost:/apro/uj_bontatlan_bose_quietcomfort_25_qc25_fekete", ads.get(0).getFullLink());
    }
}
