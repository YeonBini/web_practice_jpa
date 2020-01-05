package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor // final 있는 필드만 생성자로 주입해준다.
//@AllArgsConstructor : 모든 필드값을 생성자로 주입.
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     *  회원 가입
     */
    @Transactional
    public Long join(Member member) {
        validateDuplicatedMember(member);
        return memberRepository.save(member);
    }

    private void validateDuplicatedMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()) {
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 회원 전체 조회
     * @return List
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 회원 단건 조회
     * @param memberId
     * @return Member
     */
    public Member findById(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    public List<Member> findByName(String name) {
        return memberRepository.findByName(name);
    }

    @Transactional
    public void update(Long id, String name, Address address, List<Order> orders) {
        Member member = memberRepository.findOne(id);
        member.setName(name);
        member.setAddress(address);
        member.setOrders(orders);
    }
}
