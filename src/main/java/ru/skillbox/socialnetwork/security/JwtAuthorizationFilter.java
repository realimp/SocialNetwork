package ru.skillbox.socialnetwork.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.skillbox.socialnetwork.entities.Person;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final JwtConfig jwtConfig;
    private CustomUserDetailsService localUserDetailsService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, CustomUserDetailsService userDetailsService, JwtConfig jwtConfig) {
        super(authenticationManager);
        this.jwtConfig = jwtConfig;
        this.localUserDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        if (authentication == null) {
            chain.doFilter(request, response);
            return;
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        Person person = localUserDetailsService.findPerson(SecurityContextHolder.getContext().getAuthentication().getName());
        //localUserDetailsService.setAccountLastOnlineTime(person);
        localUserDetailsService.setAccountOnline(person,true);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {

        String token = request.getHeader("Authorization");

        if (!StringUtils.isEmpty(token) && (token.startsWith("Bearer") || token.startsWith("ey"))) {
            byte[] secret = jwtConfig.getSecret().getBytes();

            Jws<Claims> parsedToken = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token.replace("Bearer ", "")
                            .replace("Bearer", ""));

            String subject = parsedToken
                    .getBody()
                    .getSubject();

            List<SimpleGrantedAuthority> authorities = ((List<?>) parsedToken.getBody()
                    .get("authorities")).stream()
                    .map(authority -> new SimpleGrantedAuthority((String) authority))
                    .collect(Collectors.toList());

            if (!StringUtils.isEmpty(subject)) {
                return new UsernamePasswordAuthenticationToken(subject, null, authorities);
            }
        }
        return null;
    }


}
