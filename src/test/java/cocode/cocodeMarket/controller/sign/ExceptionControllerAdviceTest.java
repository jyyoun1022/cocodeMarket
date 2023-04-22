package cocode.cocodeMarket.controller.sign;

import cocode.cocodeMarket.controller.advice.GlobalExceptionAdvice;
import cocode.cocodeMarket.controller.exception.ExceptionController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class ExceptionControllerAdviceTest {

    @InjectMocks
    ExceptionController exceptionController;
    MockMvc mockMvc;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(exceptionController)
                .setControllerAdvice(new GlobalExceptionAdvice()).build();
    }

    @Test
    void entryPointTest() throws Exception {
        // given, when, then
        mockMvc.perform(
                MockMvcRequestBuilders.get("/exception/entry-point"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(401));
    }
    @Test
    void accessDeniedTest() throws Exception {
        // given, when, then
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/exception/access-denied"))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(403));
    }
}
