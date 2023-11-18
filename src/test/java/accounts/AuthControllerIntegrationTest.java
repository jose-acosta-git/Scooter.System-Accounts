package accounts;

import com.fasterxml.jackson.databind.ObjectMapper;

import accounts.dtos.RegisterDto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(properties = "spring.datasource.url=jdbc:h2:mem:testdb")
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void register_requestIsValid_returnsToken() throws Exception {

        RegisterDto registerDto = new RegisterDto("jose", "jose@gmail.com", "273737", "12345", "ADMIN");

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerDto)));

        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(result -> {
                    String responseContent = result.getResponse().getContentAsString();
                    assertTrue(responseContent.contains("token"), "Response should contain a token");
                });
    }

    @Test
    void register_emailIsDuplicated_returnsBadRequest() throws Exception {

        RegisterDto registerDto1 = new RegisterDto("jose", "jose@gmail.com", "273737", "12345", "ADMIN");

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(registerDto1)))
            .andExpect(MockMvcResultMatchers.status().isOk());

        // Second registration with the same email
        RegisterDto registerDto2 = new RegisterDto("anotherUser", "jose@gmail.com", "987654", "54321", "USER");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerDto2)))
                .andReturn();

        int statusCode = result.getResponse().getStatus();
        assertEquals(HttpStatus.BAD_REQUEST.value(), statusCode);
    }

    @Test
    void register_roleIsInvalid_returnsBadRequest() throws Exception {
        RegisterDto registerDto = new RegisterDto("userName", "jose@gmail.com", "987654", "54321", "user");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerDto)))
                .andReturn();

        int statusCode = result.getResponse().getStatus();
        assertEquals(HttpStatus.BAD_REQUEST.value(), statusCode);
    }
}
