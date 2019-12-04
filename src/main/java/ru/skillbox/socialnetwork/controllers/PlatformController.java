package ru.skillbox.socialnetwork.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.socialnetwork.api.responses.CityResponse;
import ru.skillbox.socialnetwork.api.responses.CountryResponse;
import ru.skillbox.socialnetwork.api.responses.LanguageResponse;
import ru.skillbox.socialnetwork.api.responses.ResponseList;
import ru.skillbox.socialnetwork.services.PlatformService;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/platform")
public class PlatformController {

    @Autowired
    private PlatformService platformService;
    private final Integer itemPerPageDefault = 20;
    private final Integer offsetDefault = 0;
    private final String searchStringDefault = "";

    @GetMapping("/languages")
    public ResponseList<List<LanguageResponse>> searchLanguages(
                            @RequestParam(value = "language", required = false) String searchString,
                            @RequestParam(value = "offset", required = false) Integer offset,
                            @RequestParam(value = "itemPerPage", required = false) Integer itemPerPage) {
        if (offset == null) {
            offset = offsetDefault;
        }
        if (searchString == null) {
            searchString = searchStringDefault;
        }
        if (itemPerPage == null) {
            itemPerPage = itemPerPageDefault;
        }
        List<LanguageResponse> languages = platformService.getLanguages(searchString,offset,itemPerPage);
        ResponseList<List<LanguageResponse>> responseList = new ResponseList<>(languages);
        responseList.setOffset(offset);
        responseList.setPerPage(itemPerPage);
        responseList.setTimestamp(System.currentTimeMillis());
        responseList.setTotal(languages.size());
        return responseList;
    }

    @GetMapping("/countries")
    public ResponseList<List<CountryResponse>> searchCountries(
                            @RequestParam(value = "country", required = false) String searchString,
                            @RequestParam(value = "offset", required = false) Integer offset,
                            @RequestParam(value = "itemPerPage", required = false) Integer itemPerPage) {
        if (offset == null) {
            offset = offsetDefault;
        }
        if (searchString == null) {
            searchString = searchStringDefault;
        }
        if (itemPerPage == null) {
            itemPerPage = itemPerPageDefault;
        }
        List<CountryResponse> countries = platformService.getCountries(searchString,offset,itemPerPage);
        ResponseList<List<CountryResponse>> responseList = new ResponseList<>(countries);
        responseList.setOffset(offset);
        responseList.setPerPage(itemPerPage);
        responseList.setTimestamp(System.currentTimeMillis());
        responseList.setTotal(countries.size());
        return responseList;
    }

    @GetMapping("/cities")
    public ResponseList<List<CityResponse>> searchCities(
                            @RequestParam(value = "countryId") Integer countryId,
                            @RequestParam(value = "city", required = false) String searchString,
                            @RequestParam(value = "offset", required = false) Integer offset,
                            @RequestParam(value = "itemPerPage", required = false) Integer itemPerPage) {
        if (countryId == null) {
            return new ResponseList<>("Error! Country id is null!", new ArrayList<>());
        }
        if (offset == null) {
            offset = offsetDefault;
        }
        if (searchString == null) {
            searchString = searchStringDefault;
        }
        if (itemPerPage == null) {
            itemPerPage = itemPerPageDefault;
        }
        List<CityResponse> cities = platformService.getCities(countryId, searchString, offset, itemPerPage);
        ResponseList<List<CityResponse>> responseList = new ResponseList<>(cities);
        responseList.setOffset(offset);
        responseList.setPerPage(itemPerPage);
        responseList.setTimestamp(System.currentTimeMillis());
        responseList.setTotal(cities.size());
        return responseList;
    }

}
