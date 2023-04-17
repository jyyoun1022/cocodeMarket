package cocode.cocodeMarket.service.sign;

import cocode.cocodeMarket.controller.sign.SignController;
import cocode.cocodeMarket.dto.sign.SignInRequest;
import cocode.cocodeMarket.dto.sign.SignInResponse;
import cocode.cocodeMarket.dto.sign.SignUpRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
public class SignControllerTest {
    @InjectMocks
    SignController signController;
    @Mock
    SignService signService;
    MockMvc mockMvc;
    ObjectMapper om = new ObjectMapper(); // 1


    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(signController).build();
    }

    @Test
    void signUpTest() throws Exception {
        // given
        SignUpRequest req = new SignUpRequest("test@gmail.com", "123456@!", "tester", "testNick");
        // when, then
        mockMvc.perform(
                post("/api/sign-up")
                        .content(om.writeValueAsString(req)) // 2
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(signService).signUp(req);
    }

    @Test
    void signInTest() throws Exception {
        // given
        SignInRequest req = new SignInRequest("test@gmail.com", "123456@!");
        given(signService.signIn(req)).willReturn(new SignInResponse("access","refresh"));
        // when, then
        mockMvc.perform(
                post("/api/sign-in")
                        .content(om.writeValueAsString(req))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.data.accessToken").value("access")) // 3
                .andExpect(jsonPath("$.result.data.refreshToken").value("refresh"));

        verify(signService).signIn(req);
    }
    @Test
    void ignoreNulLValueJsonResponseTest() throws Exception { // 4
        // given
        SignUpRequest req = new SignUpRequest("test@gmail.com", "12345@1", "tester", "testNick");
        // when, then
        mockMvc.perform(
                post("/api/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.result").doesNotExist());
    }

}
