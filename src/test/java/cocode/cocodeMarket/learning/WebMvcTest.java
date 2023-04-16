package cocode.cocodeMarket.learning;

import cocode.cocodeMarket.controller.response.CustomResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class WebMvcTest {

    @InjectMocks TestController testController;
    MockMvc mockMvc; // 1

    @Controller // 2
    public static class TestController {
        @GetMapping("/test/null_value_ignore")
        public CustomResponse ignoreTest() {
            return CustomResponse.success();
        }
    }

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(testController).build(); // 3
    }

    @Test
    void ignoreNullValueInJsonResponseTest() throws Exception {
        mockMvc.perform( // 4
                MockMvcRequestBuilders.get("/test/null_value_ignore"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").doesNotExist()
                );
    }
}
