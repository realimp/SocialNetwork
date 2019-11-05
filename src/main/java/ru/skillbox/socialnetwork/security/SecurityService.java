package ru.skillbox.socialnetwork.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.entities.Person;
import ru.skillbox.socialnetwork.repositories.PersonRepository;

@Service
public class SecurityService {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private PersonRepository personResponse;

    private UserDetails currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated()) {
            return null;
        }
        String emailAddress= (String) authentication.getPrincipal();
        return customUserDetailsService.loadUserByUsername(emailAddress);
    }

    public Integer currentUserId() {
        UserDetails currentUser = currentUser();
        if (currentUser == null) return null;
        Person person = personResponse.findByEMail(currentUser.getUsername());
        if (person == null) return null;
        return person.getId();
    }
}
