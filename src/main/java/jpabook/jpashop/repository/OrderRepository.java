package jpabook.jpashop.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

import static jpabook.jpashop.domain.QMember.member;
import static jpabook.jpashop.domain.QOrder.order;

@Repository
@RequiredArgsConstructor
public class OrderRepository  {
    Logger logger = LoggerFactory.getLogger(OrderRepository.class);

    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

//    public List<Order> findAllByCriteria(OrderSearch orderSearch) {
    // 동적 쿼리 문제 해결 필요
//        return em.createQuery("select o from Order o join o.member m " +
//                "where o.orderStatus = :status " +
//                "and m.name like :name", Order.class)
//                .setParameter("status", orderSearch.getOrderStatus())
//                .setParameter("name", orderSearch.getMemberName())
//                .setMaxResults(1000)
//                .getResultList();
//    }

    public List<Order> findAllByQueryDsl(OrderSearch orderSearch) {
//        BooleanBuilder booleanBuilder = new BooleanBuilder();
//
//        if(orderSearch.getOrderStatus() != null) {
//            booleanBuilder.and(order.orderStatus.eq(orderSearch.getOrderStatus()));
//        }
//
//        if(orderSearch.getMemberName() != null) {
//            booleanBuilder.and(member.name.like(orderSearch.getMemberName()));
//        }

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);
        JPAQuery<Order> orderJPAQuery = jpaQueryFactory.selectFrom(order)
                .innerJoin(order.member, member);

        if(orderSearch.getOrderStatus() != null) {
            orderJPAQuery = orderJPAQuery.where(order.orderStatus.eq(orderSearch.getOrderStatus()));
        }

        if(orderSearch.getMemberName() != null) {
            orderJPAQuery = orderJPAQuery.where(member.name.like(orderSearch.getMemberName()));
        }
        return orderJPAQuery.fetch();
    }

    public List<Order> findAllByCriteria(OrderSearch orderSearch) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> o = cq.from(Order.class);
        Join<Order, Member> m = o.join("member", JoinType.INNER);

        List<Predicate> criteria = new ArrayList<>();

        if(orderSearch.getOrderStatus() != null) {
            Predicate status = cb.equal(o.get("orderStatus"), orderSearch.getOrderStatus());
            logger.info("order status : " + status);
            criteria.add(status);
        }

        if(StringUtils.hasText(orderSearch.getMemberName())) {
            Predicate name = cb.like(m.get("name"), "%"+orderSearch.getMemberName()+"%");
            criteria.add(name);
        }

        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000);

        return query.getResultList();

    }

    public List<Order> findAllWithMemberAndAddress() {
        return em.createQuery(
                "select o from Order o " +
                        "join fetch o.member m " +
                        "join fetch o.delivery d", Order.class
        ).getResultList();
    }
}
