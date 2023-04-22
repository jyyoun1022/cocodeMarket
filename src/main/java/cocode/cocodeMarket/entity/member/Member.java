package cocode.cocodeMarket.entity.member;

import cocode.cocodeMarket.entity.common.BaseEntity;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, length = 30, unique = true) // 2
    private String email;

    private String password; // 3

    @Column(nullable = false, length = 20)
    private String username;

    @Column(nullable = false, unique = true, length = 20) // 4
    private String nickname;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true) // 5
    private Set<MemberRole> roles;

    @Builder
    public Member(String email, String password, String username, String nickname, List<Role> roles) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.nickname = nickname;
        this.roles = roles.stream().map(role -> new MemberRole(this, role)).collect(Collectors.toSet());
    }


    protected Member(){
        this.email = null;
        this.password = null;
        this.username = null;
        this.nickname = null;
        this.roles = Set.of();
    }

    public void updateNickname(String nickname) { // 6
        this.nickname = nickname;
    }
}
