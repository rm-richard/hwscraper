package rmrichard.hwscraper.model;

import java.beans.Transient;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.dizitart.no2.objects.Id;

public class Ad {
    private final String title;
    @Id
    private final String fullLink;
    private final String price;
    private final String city;
    private Boolean isNew = false;

    @JsonCreator
    public Ad(@JsonProperty("title") String title,
              @JsonProperty("fullLink") String fullLink,
              @JsonProperty("price") String price,
              @JsonProperty("city") String city) {
        this.title = title;
        this.fullLink = fullLink;
        this.price = price;
        this.city = city;
    }

    public String getTitle() {
        return title;
    }

    public String getFullLink() {
        return fullLink;
    }

    public String getPrice() {
        return price;
    }

    public String getCity() {
        return city;
    }

    @Transient
    public Boolean getIsNew() {
        return isNew;
    }

    public void setIsNew(Boolean aNew) {
        isNew = aNew;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Ad ad = (Ad) o;

        return new EqualsBuilder()
            .append(title, ad.title)
            .append(fullLink, ad.fullLink)
            .append(price, ad.price)
            .append(city, ad.city)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(title)
            .append(fullLink)
            .append(price)
            .append(city)
            .toHashCode();
    }

    @Override
    public String toString() {
        return "Ad{" +
            "title='" + title + '\'' +
            ", fullLink='" + fullLink + '\'' +
            ", price='" + price + '\'' +
            ", city='" + city + '\'' +
            '}';
    }
}
