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

    public ErrorDescription getError_description() {
        return error_description;
    }

    public void setError_description(ErrorDescription error_description) {
        this.error_description = error_description;
    }

    public void setError(ErrorType error) {
        this.error = error;
    }
}


