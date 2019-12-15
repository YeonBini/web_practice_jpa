package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Member;
import jpabook.jpashop.domain.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository repository;

    @Test
    @Transactional
    @Rollback(false)
    public void testMember() throws Exception {
        // given
        Member member = new Member();
        member.setName("Member A");

        // when
        Long savedId = repository.save(member);
        Member findedMember = repository.find(savedId);

        // then
        assertThat(findedMember.equals(member));
    }


}