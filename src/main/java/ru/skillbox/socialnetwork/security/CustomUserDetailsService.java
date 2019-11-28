package ru.skillbox.socialnetwork.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.entities.Person;
import ru.skillbox.socialnetwork.repositories.PersonRepository;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static ThreadLocal<Person> personThreadLocal = new ThreadLocal<>();

    @Autowired
    private PersonRepository personRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Person person = personRepository.findByEMail(email);
        saveThreadLocal(person);

        if (person != null) {
            List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                    .commaSeparatedStringToAuthorityList("ROLE_USER");
            return new User(person.getEMail(), person.getPassword(), grantedAuthorities);
        }

        throw new UsernameNotFoundException("Username with " + email + " not found");
    }

    public void setAccountOnline(Person person, Boolean onLine) throws  UsernameNotFoundException {
        person.setOnline(onLine);
        changeUser(person);
    }

    public void setAccountLastOnlineTime(Person person) throws  UsernameNotFoundException{
        long timeNow = Calendar.getInstance().getTimeInMillis();
        Timestamp tm = new Timestamp(timeNow);
        person.setLastOnlineTime(tm);
        person.setOnline(false);
        changeUser(person);
    }

    private void changeUser(Person person){
        personRepository.save(person);
    }

    public Person findPerson(String email){
        return personRepository.findByEMail(email);
    }

    private void saveThreadLocal(Person person) {
        personThreadLocal.set(person);
    }

    public Person getThreadLocal() {
        return personThreadLocal.get();
    }

    public void deleteThreadLocal() {
        personThreadLocal.remove();
    }
}
