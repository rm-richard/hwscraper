package rmrichard.hwscraper.repository;

import java.util.List;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import rmrichard.hwscraper.Properties;
import rmrichard.hwscraper.model.Ad;

@Component
public class AdRepository {

    @Autowired
    private Properties properties;

    public List<Ad> getAds() {
        Nitrite db = openDb();
        List<Ad> ads = db.getRepository(Ad.class).find().toList();
        db.close();
        return ads;
    }

    public void save(Ad... ads) {
        Nitrite db = openDb();
        ObjectRepository<Ad> repository = db.getRepository(Ad.class);

        for (Ad ad : ads) {
            repository.update(ad, true);
        }

        db.close();
    }

    private Nitrite openDb() {
        return Nitrite.builder()
            .compressed()
            .filePath(properties.getDbFile())
            .openOrCreate();
    }
}
