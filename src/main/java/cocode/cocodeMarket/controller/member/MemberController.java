package cocode.cocodeMarket.controller.member;

import cocode.cocodeMarket.dto.response.CustomResponse;
import cocode.cocodeMarket.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Log4j2
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/api/members/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomResponse read(@PathVariable Long id) {
        return CustomResponse.success(memberService.read(id));
    }

    @DeleteMapping("/api/members/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomResponse withdraw(@PathVariable Long id) {
        memberService.delete(id);
        return CustomResponse.success();
    }


}
