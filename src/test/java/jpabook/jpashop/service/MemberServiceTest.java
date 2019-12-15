package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Member;
import jpabook.jpashop.domain.repository.MemberRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.fail;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EntityManager em;

//    @After
//    public void cleanAll() {
//        memberRepository.deleteAll();
//    }

    @Test
    public void 회원가입() throws Exception {
        // given
        Member member = new Member();
        member.setName("Chung");

        // when
        Long joinId = memberService.join(member);

        // then
        Member one = memberRepository.findOne(joinId);
        em.flush();

        Assert.assertEquals(member, one);
    }

    @Test(expected = IllegalArgumentException.class)
    public void 중복_회원_예외() throws Exception {
        // given
        Member member1 = new Member();
        Member member2 = new Member();

        member1.setName("Chung");
        member2.setName("Chung");

        // when
        memberService.join(member1);
        memberService.join(member2);

        // then
        fail("에러");
    }
}