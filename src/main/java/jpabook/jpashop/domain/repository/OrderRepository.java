package jpabook.jpashop.domain.repository;

import jpabook.jpashop.domain.item.Order;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class OrderRepository  {

    @PersistenceContext
    private EntityManager em;

    public Long save(Order order) {
        em.persist(order);
        return order.getId();
    }

    public Order find(Long id) {
        return em.find(Order.class, id);
    }

    public List<Order> findAll() {
        return em.createQuery("select o from Order o", Order.class).getResultList();
    }

    public void deleteAll() {
        List<Order> orders = findAll();
        orders.stream()
                .forEach(order -> {
                    em.remove(order);
                });
    }
}
