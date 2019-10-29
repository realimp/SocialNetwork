package ru.skillbox.socialnetwork.api.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Error {

    private ErrorType error;
    @JsonProperty("error_description")
    private ErrorDescription error_description;

    public enum ErrorType {
        INVALID_REQUEST,
        UNAUTHORIZED
    }

}


