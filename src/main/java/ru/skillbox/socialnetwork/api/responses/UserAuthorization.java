package ru.skillbox.socialnetwork.api.responses;

import java.util.Objects;

public class UserAuthorization extends PersonResponse{

    private Token token;

    public UserAuthorization() {}

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAuthorization)) return false;
        UserAuthorization that = (UserAuthorization) o;
        return Objects.equals(getToken(), that.getToken());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getToken());
    }

    @Override
    public String toString() {
        return "UserAuthorization{" +
                "token='" + token + '\'' +
                '}';
    }

}
