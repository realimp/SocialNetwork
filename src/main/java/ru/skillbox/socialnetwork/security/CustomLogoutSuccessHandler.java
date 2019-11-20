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
import ru.skillbox.socialnetwork.api.responses.Response;
import ru.skillbox.socialnetwork.entities.Person;
import ru.skillbox.socialnetwork.services.AccountService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.HashMap;

public class CustomLogoutSuccessHandler extends HttpStatusReturningLogoutSuccessHandler implements LogoutSuccessHandler {

    private final HttpStatus customHttpStatus;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private JwtConfig jwtConfig;

    public CustomLogoutSuccessHandler(HttpStatus httpStatusToReturn) {
        super(httpStatusToReturn);
        this.customHttpStatus = httpStatusToReturn;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String token = request.getHeader("Authorization");
        byte[] secret = jwtConfig.getSecret().getBytes();
        Jws<Claims> parsedToken = Jwts.parser().setSigningKey(secret).parseClaimsJws(token.replace("Bearer ", "").replace("Bearer", ""));
        String logoutPersonEmail = parsedToken.getBody().getSubject();
        Response responseContent = new Response();
        responseContent.setError("string");
        responseContent.setTimestamp(new Timestamp(System.currentTimeMillis()).getTime());
        HashMap message = new HashMap();
        message.put("message", "ok");
        responseContent.setData(message);
        PrintWriter writer = response.getWriter();
        String jsonString = new Gson().toJson(responseContent);
        response.setStatus(this.customHttpStatus.value());
        writer.print(jsonString);
        Person person = userDetailsService.findPerson(logoutPersonEmail);
        userDetailsService.setAccountLastOnlineTime(person);
        super.onLogoutSuccess(request,response,authentication);
    }
}
