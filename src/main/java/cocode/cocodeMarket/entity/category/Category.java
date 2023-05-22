package cocode.cocodeMarket.entity.category;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {
    /**
     * 카테고리 엔티티는 자신의 id 값과 , 카테고리명 ,부모 카테고리를 가짐
     * 만약 부모 카테고리가 없다면, 해당 카테고리는 루트 코테고리가 되고, parent 는 null을 가지게 됨.
     * 1. 어떤 부모 카테고리가 삭제된다면, 해당 카테고리의 모든 하위 카테고리는 연달아서 제거 됨
     * @ManyToOne 관계로 자식 카테고리를 참조하여 cascade 타입을 REMOVE로 설정하거나,
     * @OnDelete의 action 설정을 cascade로 설정하면, 해당 카테고리의 하위 카테고리를 연달하서 제거가 가능하다.
     *
     * 예를 들어 "스마트폰" 카테고리의 하위 카테고리가 안드로이드,아이폰 일떄 스마트폰 카테고리를 제거 되면 하위 카테고리를 모두 제거
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(length = 30,nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @OnDelete(action = OnDeleteAction.CASCADE) //1.
    private Category parent;

    public Category(String name, Category parent) {
        this.name = name;
        this.parent = parent;
    }
}
