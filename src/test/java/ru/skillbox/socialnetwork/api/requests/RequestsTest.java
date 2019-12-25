package ru.skillbox.socialnetwork.api.requests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

public class RequestsTest {

    @Test
    public void RegisterTest() throws JsonProcessingException, JSONException {
        Register register = new Register();
        register.setEmail("arkady@example.com");
        register.setPasswd1("123456");
        register.setPasswd2("123456");
        register.setFirstName("Аркадий");
        register.setLastName("Паровозов");
        register.setCode("3675");
        JSONAssert.assertEquals("{\"email\":\"arkady@example.com\",\"passwd1\":\"123456\",\"passwd2\":\"123456\",\"firstName\":\"Аркадий\",\"lastName\":\"Паровозов\",\"code\":\"3675\"}",
                new ObjectMapper().writeValueAsString(register), true);
    }

}
