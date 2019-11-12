package ru.skillbox.socialnetwork.api.responses;

import javax.swing.*;

public enum ErrorDescription {

    UNAUTHORIZED("Unauthorized"),
    NO_AUTH_CODE("An authorization code must be supplied"),
    MISMATCH_URI("Redirect URI mismatch"),
    INVALID_CODE("Invalid authorization code: CODE"),
    BAD_CREDENTIALS("Bad credentials"),
    USER_NOT_FOUND("User not found");

    private String description;

    ErrorDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
