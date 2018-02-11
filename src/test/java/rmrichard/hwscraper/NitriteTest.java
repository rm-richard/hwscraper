package rmrichard.hwscraper;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.Cursor;
import org.dizitart.no2.objects.ObjectRepository;
import org.junit.Test;
import rmrichard.hwscraper.model.Ad;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class NitriteTest {

    private static final Ad TEST_AD = new Ad("Test Ad", "http://localhost/", "10 Ft", "Budapest");

    @Test
    public void testSaveAndRead() throws IOException {
        File tempFile = File.createTempFile("test", "db");
        tempFile.deleteOnExit();

        Nitrite db = Nitrite.builder()
            .compressed()
            .filePath(tempFile)
            .openOrCreate();
        ObjectRepository<Ad> repository = db.getRepository(Ad.class);

        Cursor<Ad> cursor = repository.find();
        assertEquals("Test repository is not empty", 0, cursor.size());

        repository.insert(TEST_AD);
        db.commit();

        Ad readback = repository.find().firstOrDefault();
        assertEquals("Test should contain newly saved element", TEST_AD, readback);

        db.close();
    }
}
