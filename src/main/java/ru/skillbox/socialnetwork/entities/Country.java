package ru.skillbox.socialnetwork.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "country")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    @OneToMany(mappedBy="country")
    private List<City> cities;

    public Country() {
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

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Country)) return false;
        Country country = (Country) o;
        return Objects.equals(getId(), country.getId()) &&
                Objects.equals(getTitle(), country.getTitle()) &&
                Objects.equals(getCities(), country.getCities());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getCities());
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", cities=" + cities +
                '}';
    }

}
