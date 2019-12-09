package ru.skillbox.socialnetwork.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.skillbox.socialnetwork.api.responses.Error;
import ru.skillbox.socialnetwork.api.responses.ErrorDescription;
import ru.skillbox.socialnetwork.api.responses.Response;
import ru.skillbox.socialnetwork.api.responses.UserAuthorization;
import ru.skillbox.socialnetwork.entities.MessagePermission;
import ru.skillbox.socialnetwork.entities.Person;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private AuthenticationManager authenticationManager;

    private CustomUserDetailsService userDetailsService;

    private JwtConfig jwtConfig;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, CustomUserDetailsService userDetailsService, JwtConfig jwtConfig) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtConfig = jwtConfig;

        setFilterProcessesUrl(jwtConfig.getLoginUrl());
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authToken;
        try {
            Creadentials creds = objectMapper.readValue(request.getInputStream(), Creadentials.class);
            authToken = new UsernamePasswordAuthenticationToken(creds.email, creds.password);
        } catch (Exception e){
            throw new AccessDeniedException("Fuck you");
        }
        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain filterChain, Authentication authentication) throws IOException {
        User user = ((User) authentication.getPrincipal());

        List<String> authorities = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        String token = Jwts.builder()
                .setSubject(user.getUsername())
                .claim("authorities", user.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 864000000))
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret().getBytes())
                .compact();

        Person person = userDetailsService.getThreadLocal();
        userDetailsService.deleteThreadLocal();

        UserAuthorization authorization = new UserAuthorization();
        authorization.setId(person.getId());
        authorization.setFirstName(person.getFirstName());
        authorization.setLastName(person.getLastName());
        authorization.setRegDate(person.getRegDate().getTime());
        if (person.getBirthDate() != null) {
            authorization.setBirthDate(person.getBirthDate().getTime());
        }
        authorization.seteMail(user.getUsername());
        authorization.setPhone(person.getPhone());
        authorization.setPhoto(person.getPhoto());
        authorization.setAbout(person.getAbout());
        authorization.setCity(person.getCity());
        authorization.setCountry(person.getCountry());
        authorization.setMessagesPermission(MessagePermission.ALL);
        authorization.setLastOnlineTime(new Date().getTime());
        authorization.setBlocked(person.getBlocked());
        authorization.setToken(token);

        Response responseContent = new Response(authorization);
        responseContent.setError("");
        responseContent.setTimestamp(new Timestamp(System.currentTimeMillis()).getTime());

        String jsonString = new Gson().toJson(responseContent);

        PrintWriter writer = response.getWriter();
        response.addHeader("Authorization", "Bearer" + token);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");

        writer.print(jsonString);
        writer.flush();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws  IOException {
        Error error = new Error();
        error.setError(Error.ErrorType.INVALID_REQUEST);
        error.setError_description(ErrorDescription.BAD_CREDENTIALS);

        String jsonString = new Gson().toJson(error);

        PrintWriter writer = response.getWriter();
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType("application/json");
        writer.print(jsonString);
        writer.flush();
    }

    private static class Creadentials {
        private String email;
        private String password;

        public String getEmail() {
            return email;
        }

        public void setEmail(String username) {
            this.email = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
