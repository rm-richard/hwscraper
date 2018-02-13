package rmrichard.hwscraper.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.dizitart.no2.Nitrite;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import rmrichard.hwscraper.model.Ad;

@RunWith(MockitoJUnitRunner.class)
public class AdRepositoryTest {

    private static final List<Ad> TEST_ADS = Arrays.asList(new Ad[]{
        new Ad("#1 keyboard", "http://localhost/1", "8 000 Ft", "Budapest"),
        new Ad("#2 mouse", "http://localhost/2", "17 000 Ft", "Budapest"),
        new Ad("#3 headphones", "http://localhost/3", "1 000 Ft", "Szeged"),
    });

    @InjectMocks
    private AdRepository repository;

    @Test
    public void testRepository() throws IOException {
        AdRepository repositorySpy = Mockito.spy(repository);
        Nitrite nitrite = createTempDb();
        doReturn(nitrite).when(repositorySpy).openDb();
        doNothing().when(repositorySpy).closeDb(any());

        int newAdCount = repositorySpy.updateAndMarkNew(TEST_ADS);

        assertEquals("All 3 ads should be newly seen", 3, newAdCount);
        assertTrue("Ad should be marked as new", TEST_ADS.get(0).getIsNew());

        final List<Ad> newAds = Arrays.asList(new Ad[]{
            new Ad("#1 keyboard", "http://localhost/1", "8 000 Ft", "Budapest"),
            new Ad("#2 modified mouse", "http://localhost/2", "8 000 Ft", "Budapest"),
            new Ad("#4 wooden plan", "http://localhost/4", "0 Ft", "Budapest")
        });

        newAdCount = repositorySpy.updateAndMarkNew(newAds);

        assertEquals("Last 2 ads should be newly seen", 2, newAdCount);
        assertFalse("1st item should not be marked as new", newAds.get(0).getIsNew());
        assertTrue("2nd item should be marked as new", newAds.get(1).getIsNew());
        assertTrue("3rd item should be marked as new", newAds.get(2).getIsNew());
        nitrite.close();
    }

    private Nitrite createTempDb() throws IOException {
        File tempFile = File.createTempFile("test", "db");
        tempFile.deleteOnExit();
        return Nitrite.builder().filePath(tempFile).openOrCreate();
    }
}
