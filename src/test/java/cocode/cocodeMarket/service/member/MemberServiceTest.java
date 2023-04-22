package cocode.cocodeMarket.service.member;

import cocode.cocodeMarket.dto.member.MemberDto;
import cocode.cocodeMarket.entity.member.Member;
import cocode.cocodeMarket.exception.MemberNotFoundException;
import cocode.cocodeMarket.repository.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @InjectMocks
    MemberService memberService;

    @Mock
    MemberRepository memberRepository;

    @Test
    void readTest() {
        // given
        Member member = createMemberForTest();
        BDDMockito.given(memberRepository.findById(ArgumentMatchers.anyLong())).willReturn(Optional.of(member));
        // when
        MemberDto result = memberService.read(1L);
        // then
        Assertions.assertThat(result.getEmail()).isEqualTo(member.getEmail());
    }

    @Test
    void readExceptionByMemberNotFoundTest() {
        // given
        BDDMockito.given(memberRepository.findById(ArgumentMatchers.anyLong())).willReturn(Optional.ofNullable(null));
        // when, then
        Assertions.assertThatThrownBy(() -> memberService.read(1L)).isInstanceOf(MemberNotFoundException.class);
    }

    @Test
    void deleteTest() {
        // given
        BDDMockito.given(memberRepository.findById(ArgumentMatchers.anyLong())).willReturn(Optional.of(createMemberForTest()));

        // when
        memberService.delete(1L);
        // then
        BDDMockito.verify(memberRepository).deleteById(ArgumentMatchers.anyLong());
    }

    @Test
    void deleteExceptionByMemberNotFoundTest() {
        // given
        BDDMockito.given(memberRepository.findById(ArgumentMatchers.anyLong())).willReturn(Optional.ofNullable(null));
        // when, then
        Assertions.assertThatThrownBy(() -> memberService.delete(1L)).isInstanceOf(MemberNotFoundException.class);
    }

    private Member createMemberForTest() {
        return new Member("test@gmail.com","cocoJ^^12345","username","nickname", List.of());
    }
}
