package ru.skillbox.socialnetwork.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.skillbox.socialnetwork.repositories.PersonRepository;

@Component
public class JwtConfig {

    @Autowired
    private PersonRepository personRepository;

    private String token;

    @Value("${security.jwt.secret}")
    private String secret;

    @Value("${security.auth.login}")
    private String loginUrl;

    public String getToken() {
        return token;
    }

    public String getSecret() {
        return secret;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public PersonRepository getPersonRepository() {
        return personRepository;
    }
}
