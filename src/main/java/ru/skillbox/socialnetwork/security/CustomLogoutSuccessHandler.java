package ru.skillbox.socialnetwork.security;

import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import ru.skillbox.socialnetwork.api.responses.Response;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.HashMap;

public class CustomLogoutSuccessHandler extends HttpStatusReturningLogoutSuccessHandler implements LogoutSuccessHandler {

    private final HttpStatus customHttpStatus;
    public CustomLogoutSuccessHandler(HttpStatus httpStatusToReturn) {
        super(httpStatusToReturn);
        this.customHttpStatus = httpStatusToReturn;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HashMap message = new HashMap();
        message.put("message", "ok");
        Response responseContent = new Response();
        responseContent.setError("string");
        responseContent.setTimestamp(new Timestamp(System.currentTimeMillis()).getTime());
        responseContent.setData(message);
        PrintWriter writer = response.getWriter();
        String jsonString = new Gson().toJson(responseContent);
        response.setStatus(this.customHttpStatus.value());
        writer.print(jsonString);
        super.onLogoutSuccess(request,response,authentication);
    }
}
