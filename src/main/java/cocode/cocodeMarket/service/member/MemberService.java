package cocode.cocodeMarket.service.member;


import cocode.cocodeMarket.dto.member.MemberDto;
import cocode.cocodeMarket.entity.member.Member;
import cocode.cocodeMarket.exception.MemberNotFoundException;
import cocode.cocodeMarket.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberDto read(Long id) {
        return MemberDto.toDto(memberRepository.findById(id).orElseThrow(MemberNotFoundException::new));
    }

    @Transactional
    public void delete(Long id){
        Optional<Member> findMember = memberRepository.findById(id);
        if (findMember.isPresent()) {
            memberRepository.deleteById(id);
        } else {
            throw new MemberNotFoundException();
        }
    }
}
