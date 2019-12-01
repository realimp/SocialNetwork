package ru.skillbox.socialnetwork.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.api.responses.CityResponse;
import ru.skillbox.socialnetwork.api.responses.CountryResponse;
import ru.skillbox.socialnetwork.api.responses.LanguageResponse;
import ru.skillbox.socialnetwork.entities.City;
import ru.skillbox.socialnetwork.entities.Country;
import ru.skillbox.socialnetwork.entities.Language;
import ru.skillbox.socialnetwork.repositories.CityRepository;
import ru.skillbox.socialnetwork.repositories.CountryRepository;
import ru.skillbox.socialnetwork.repositories.LanguageRepository;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlatformService {

    @Autowired
    private LanguageRepository languageRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private CountryRepository countryRepository;

    public List<LanguageResponse> getLanguages(String searchString, Integer offset, Integer itemPerPage) {
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(offset, itemPerPage, sort);
        Page<Language> pages = languageRepository.findByTitle(searchString, pageable);
        List<LanguageResponse> languages = new ArrayList<>();
        pages.forEach(language -> {
            languages.add(new LanguageResponse(language.getId(),language.getTitle()));
        });
        return languages;
    }

    public List<CountryResponse> getCountries(String searchString, Integer offset, Integer itemPerPage) {
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(offset, itemPerPage, sort);
        Page<Country> pages = countryRepository.findByTitle(searchString,pageable);
        List<CountryResponse> countries = new ArrayList<>();
        pages.forEach(country -> {
            countries.add(new CountryResponse(country.getId(),country.getTitle()));
        });
        return countries;
    }

    public List<CityResponse> getCities(Integer countryId, String searchString, Integer offset, Integer itemPerPage) {
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(offset, itemPerPage, sort);
        Page<City> pages = cityRepository.findByCountryIdAndTitle(countryId,searchString,pageable);
        List<CityResponse> cities = new ArrayList<>();
        pages.forEach(city -> {
            cities.add(new CityResponse(city.getId(),city.getTitle()));
        });
        return cities;
    }

}
