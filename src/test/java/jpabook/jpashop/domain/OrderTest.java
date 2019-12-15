package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Member;
import jpabook.jpashop.domain.item.Order;
import jpabook.jpashop.domain.repository.MemberRepository;
import jpabook.jpashop.domain.repository.OrderRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    MemberRepository memberRepository;

    @After
    public void clear() {
        orderRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Before
    public void setup() {
        Member member = new Member();
        Arrays.asList(1, 2, 3, 4, 5).stream()
                .forEach(i -> {
                    Order order = new Order();
                    order.setMember(member);
                    orderRepository.save(order);
                });
        memberRepository.save(member);
    }

    @Test
    public void nPlusOneProblemTest() throws Exception {
//        memberRepository.findAll();
    }

}