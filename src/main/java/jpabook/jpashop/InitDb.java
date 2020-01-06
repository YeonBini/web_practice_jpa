package jpabook.jpashop;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

/**
 * 총 주문 2개
 * userA
 * * jpa book1
 * * jpa book2
 * userB
 * * spring1 book
 * * spring2 book
 */
@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitDbService initDbService;

    @PostConstruct
    public void init() {
        initDbService.dbInit1();
        initDbService.dbInit2();
    }

    @Component
    @RequiredArgsConstructor
    @Transactional
    static class InitDbService {
        private final EntityManager em;

        public void dbInit1() {
            Member member = getMember("userA", new Address("Seoul", "sample", "sample"));
            em.persist(member);

            Book book1 = getBook("yeonbin", "jpa1 book", 10000,100);
            em.persist(book1);

            Book book2 = getBook("yeonbin", "jpa2 book", 20000, 100);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());

            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        public void dbInit2() {
            Member member = getMember("userB", new Address("Daegu", "sample", "sample"));
            em.persist(member);

            Book book1 = getBook("yeonbin", "spring1 book", 20000,200);
            em.persist(book1);

            Book book2 = getBook("yeonbin", "spring2 book", 30000, 200);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, book1.getPrice(), 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, book2.getPrice(), 2);

            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());

            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        private Book getBook(String author, String name, int price, int quantity) {
            Book book1 = new Book();
            book1.setAuthor(author);
            book1.setName(name);
            book1.setPrice(price);
            book1.setStockQuantity(quantity);
            return book1;
        }

        private Member getMember(String username, Address address) {
            Member member = new Member();
            member.setName(username);
            member.setAddress(address);
            return member;
        }
    }
}
