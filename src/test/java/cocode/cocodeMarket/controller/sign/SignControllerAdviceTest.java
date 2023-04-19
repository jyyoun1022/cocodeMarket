package cocode.cocodeMarket.controller.sign;

import cocode.cocodeMarket.controller.advice.GlobalExceptionAdvice;
import cocode.cocodeMarket.dto.sign.SignInRequest;
import cocode.cocodeMarket.dto.sign.SignUpRequest;
import cocode.cocodeMarket.exception.LoginFailureException;
import cocode.cocodeMarket.exception.MemberEmailAlreadyExistsException;
import cocode.cocodeMarket.exception.RoleNotFoundException;
import cocode.cocodeMarket.service.sign.SignService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
public class SignControllerAdviceTest {

    @InjectMocks
    SignController signController;
    @Mock
    SignService signService;
    MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void init(){
        mockMvc = MockMvcBuilders.standaloneSetup(signController).setControllerAdvice(new GlobalExceptionAdvice()).build();
    }

    @Test
    void signInLoginFailureExceptionTest() throws Exception {
        // given
        SignInRequest req = new SignInRequest("test@gmail.com", "123456a!");
        BDDMockito.given(signService.signIn(ArgumentMatchers.any())).willThrow(LoginFailureException.class);
        // when, then
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void signInMethodArgumentNotValidExceptionTest() throws Exception {
        // given
        SignInRequest req = new SignInRequest("test", "123456!^j");
        // when, then
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/sign-in")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(req)))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError());
    }

    @Test
    void signUpMemberEmailAlreadyExistsExceptionTest() throws Exception{
        // given
        SignUpRequest req = new SignUpRequest("test@gmail.com", "1234456J!aa", "tester", "tester");
        doThrow(MemberEmailAlreadyExistsException.class).when(signService).signUp(ArgumentMatchers.any());
        // when, then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }
    @Test
    void signUpRoleNotFoundExceptionTest() throws Exception{
        // given
        SignUpRequest req = new SignUpRequest("email@gmail.com", "cocode123!J!", "tester", "tester");
        Mockito.doThrow(RoleNotFoundException.class).when(signService).signUp(any());
        // when, then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void signUpMethodArgumentNotValidationExceptionTest() throws Exception {
        // given
        SignUpRequest req = new SignUpRequest("", "", "", "");
        // when, then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
