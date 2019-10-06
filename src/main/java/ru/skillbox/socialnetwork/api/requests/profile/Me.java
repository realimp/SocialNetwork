package ru.skillbox.socialnetwork.api.requests.profile;

import ru.skillbox.socialnetwork.api.City;
import ru.skillbox.socialnetwork.api.Country;

import java.util.Date;

public class Me {

  private String firstName;
  private String lastName;
  private Date birthDate;
  private String phone;
  private String photo;
  private String about;
  private City city;
  private Country country;
  private String messagesPermission;

  public Me() {
  }

  public Me(String firstName, String lastName, Date birthDate, String phone,
            String photo, String about, City city, Country country, String messagesPermission) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.birthDate = birthDate;
    this.phone = phone;
    this.photo = photo;
    this.about = about;
    this.city = city;
    this.country = country;
    this.messagesPermission = messagesPermission;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Date getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(Date birthDate) {
    this.birthDate = birthDate;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getPhoto() {
    return photo;
  }

  public void setPhoto(String photo) {
    this.photo = photo;
  }

  public String getAbout() {
    return about;
  }

  public void setAbout(String about) {
    this.about = about;
  }

  public City getCity() {
    return city;
  }

  public void setCity(City city) {
    this.city = city;
  }

  public Country getCountry() {
    return country;
  }

  public void setCountry(Country country) {
    this.country = country;
  }

  public String getMessagesPermission() {
    return messagesPermission;
  }

  public void setMessagesPermission(String messagesPermission) {
    this.messagesPermission = messagesPermission;
  }
}
