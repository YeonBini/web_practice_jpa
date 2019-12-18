package jpabook.jpashop.domain;

import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    MemberRepository memberRepository;

    @After
    public void clear() {
//        orderRepository.deleteAll();
//        memberRepository.deleteAll();
    }

    @Before
    public void setup() {

    }

    @Test
    public void nPlusOneProblemTest() throws Exception {
//        memberRepository.findAll();
    }

}