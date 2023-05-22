package cocode.cocodeMarket.repository.category;

import cocode.cocodeMarket.entity.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    /**
     * 부모의 아이디로 오름차순 하되, null을 우선으로, 그 다음으로 자신의 아이디를 오름차순으로 정렬하여 조회
     * id     p_id          id    p_id
     * 1      NULL          1       null
     * 2        1           8       null
     * 3        1           2       1
     * 4        2           3       1
     * 4        2     ->    4       2
     * 5        2           5       2
     * 6        4           6       3
     * 7        3           7       4
     * 8      NULL
     */
    @Query("select c from Category c left join c.parent p order by p.id asc nulls first,c.id asc")
    List<Category> findAllOrderByParentIdAscNullsFirstCategoryIdAsc();

}
