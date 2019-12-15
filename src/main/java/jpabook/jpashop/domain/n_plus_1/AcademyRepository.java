package jpabook.jpashop.domain.n_plus_1;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AcademyRepository extends JpaRepository<Academy, Long> {

    @Query("select a from Academy a join fetch a.subjects")
    List<Academy> findAllJoinFetch();



}
