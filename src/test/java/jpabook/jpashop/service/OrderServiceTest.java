package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.domain.item.*;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.status.OrderStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private EntityManager em;

    @Test
    public void 상품주문() throws Exception {
        // given
        Member member = createMember("Chung", new Address("Seoul", "gosanjaro", "04750"));

        Item item = createItem(10000, 10);

        // when
        Long orderId = orderService.order(member.getId(), item.getId(), 5);
        em.flush();

        // then
        Order findOrder = orderRepository.findOne(orderId);
        assertEquals(findOrder.getOrderItemList().get(0).getItem(), item);
        assertEquals(findOrder.getMember().getName(), member.getName());
        assertEquals("주문 총 금액은 : ", findOrder.getTotalPrice(), 10000 * 5);
        assertEquals("재고 수량이 줄어아 한다." , item.getStockQuantity(), 5);

    }

    @Test
    public void 주문취소() throws Exception {
        // given
        Member member = createMember("Chung", new Address("Seoul", "gosanjaro", "04750"));
        Item item = createItem(10000, 10);
        Long orderId = orderService.order(member.getId(), item.getId(), 5);

        // when
        orderService.cancelOrder(orderId);
        em.flush();

        // then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals("재고 수량 확인 ", item.getStockQuantity(), 10);
        assertEquals("주문 상태 확인 ", getOrder.getOrderStatus(), OrderStatus.CANCEL);
    }

    @Test(expected = NotEnoughStockException.class)
    public void 재고부족() throws Exception {
        // given
        Member member = createMember("Chung", new Address("Seoul", "gosanjaro", "04750"));
        Item item = createItem(10000, 10);

        // when

        Long orderId = orderService.order(member.getId(), item.getId(), 15);
        // then
        fail("should be fail for lack of stock");
    }

    private Item createItem(int price, int quantity) {
        Item item = new Book();
        item.setPrice(price);
        item.addStock(quantity);
        em.persist(item);
        return item;
    }

    private Member createMember(String name, Address address) {
        Member member = new Member();
        member.setName(name);
        member.setAddress(address);
        em.persist(member);

        return member;
    }


}