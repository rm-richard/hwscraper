package rmrichard.hwscraper.model;

public class Ad {
    public final String title;
    public final String fullLink;
    public final String price;
    public final String city;

    public Ad(String title, String fullLink, String price, String city) {
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
