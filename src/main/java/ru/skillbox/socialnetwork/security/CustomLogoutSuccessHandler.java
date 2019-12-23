package ru.skillbox.socialnetwork.security;

import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import ru.skillbox.socialnetwork.api.responses.MessageResponse;
import ru.skillbox.socialnetwork.api.responses.Response;
import ru.skillbox.socialnetwork.entities.Person;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class CustomLogoutSuccessHandler extends HttpStatusReturningLogoutSuccessHandler
        implements LogoutSuccessHandler {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtConfig jwtConfig;

    public CustomLogoutSuccessHandler(HttpStatus httpStatusToReturn) {
        super(httpStatusToReturn);
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        String token = request.getHeader("Authorization");
        byte[] secret = jwtConfig.getSecret().getBytes();
        Jws<Claims> parsedToken = Jwts.parser().setSigningKey(secret)
                .parseClaimsJws(token.replace("Bearer ", "").replace("Bearer", ""));
        String logoutPersonEmail = parsedToken.getBody().getSubject();
        MessageResponse responseContent = new MessageResponse("ok");
        Response<MessageResponse> successResponse = new Response<>(responseContent);
        
        String jsonString = new Gson().toJson(successResponse);

        PrintWriter writer = response.getWriter();
        
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");

        writer.print(jsonString);
        writer.flush();
        
        Person person = userDetailsService.findPerson(logoutPersonEmail);
        userDetailsService.setAccountLastOnlineTime(person);
        super.onLogoutSuccess(request, response, authentication);
    }
}
