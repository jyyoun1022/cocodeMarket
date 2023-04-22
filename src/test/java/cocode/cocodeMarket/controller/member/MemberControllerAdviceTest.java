package cocode.cocodeMarket.controller.member;

import cocode.cocodeMarket.controller.advice.GlobalExceptionAdvice;
import cocode.cocodeMarket.exception.MemberNotFoundException;
import cocode.cocodeMarket.service.member.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class MemberControllerAdviceTest {

    @InjectMocks
    MemberController memberController;
    @Mock
    MemberService memberService;
    MockMvc mockMvc;

    @BeforeEach
    void init(){
        mockMvc = MockMvcBuilders.standaloneSetup(memberController).setControllerAdvice(new GlobalExceptionAdvice()).build();
    }

    @Test
    void readMemberNotFoundExceptionTest() throws Exception {
        // given
        Long id = 1L;
        BDDMockito.given(memberService.read(ArgumentMatchers.anyLong())).willThrow(MemberNotFoundException.class);
        // when, then
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/members/{id}",id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404));
    }
    @Test
    void deleteMemberNotFoundExceptionTest() throws Exception {
        // given
        BDDMockito.doThrow(MemberNotFoundException.class).when(memberService).delete(ArgumentMatchers.anyLong());
        Long id = 1L;
        // when, then
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/members/{id}",id))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404));
    }
}
