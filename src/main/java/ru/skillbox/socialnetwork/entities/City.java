package ru.skillbox.socialnetwork.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "city")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    @ManyToOne
    @JoinColumn(name="country_id", nullable=false)
    private Country country;

    public City() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof City)) return false;
        City city = (City) o;
        return Objects.equals(getId(), city.getId()) &&
                Objects.equals(getTitle(), city.getTitle()) &&
                Objects.equals(getCountry(), city.getCountry());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getCountry());
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", country=" + country +
                '}';
    }

}
