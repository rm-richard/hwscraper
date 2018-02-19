package rmrichard.hwscraper.repository;

import java.util.List;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectFilter;
import org.dizitart.no2.objects.ObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import rmrichard.hwscraper.Properties;
import rmrichard.hwscraper.model.Ad;

@Component
public class AdRepository {

    @Autowired
    private Properties properties;

    public int updateAndMarkNew(List<Ad> ads) {
        Nitrite db = openDb();
        List<Ad> savedAds = getAds(db);

        int newAdCount = 0;
        for (Ad ad : ads) {
            if (!savedAds.contains(ad)) {
                ad.setIsNew(true);
                newAdCount += 1;
            }
        }

        replaceSavedAds(db, ads.stream().toArray(Ad[]::new));
        db.commit();
        closeDb(db);
        return newAdCount;
    }

    private List<Ad> getAds(Nitrite db) {
        List<Ad> ads = db.getRepository(Ad.class).find().toList();
        return ads;
    }

    private void replaceSavedAds(Nitrite db, Ad... ads) {
        ObjectRepository<Ad> repository = db.getRepository(Ad.class);
        repository.remove((ObjectFilter) null);
        for (Ad ad : ads) {
            repository.update(ad, true);
        }
    }

    protected Nitrite openDb() {
        return Nitrite.builder()
            .compressed()
            .filePath(properties.getDbFile())
            .openOrCreate();
    }

    protected void closeDb(Nitrite db) {
        db.close();
    }
}
