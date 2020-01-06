package jpabook.jpashop.repository.order.simplequery;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderSimpleQueryRepository {

    private final EntityManager em;

    /**
     * 성능 최적화를 위해 불가피하게 dto를 repository에서 생성해야한다면
     * 기존 엔티티를 조회하는 repository와 별개의 클래스를 생성해준다.
     * 복잡한 join query 사용시 이런식으로 별도로 생성하면 기존 로직과 분리되어 유지 관리가 용이하다.
     * @return
     */
    public List<OrderSimpleQueryDto> findOrderDtos() {
        return em.createQuery(
                "select new jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryDto(o.id, m.name, o.orderDate, o.orderStatus , d.address) " +
                        " from Order o" +
                        " join o.member m" +
                        " join o.delivery d", OrderSimpleQueryDto.class
        ).getResultList();
    }
}
